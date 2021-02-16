const UserStorage = require("./UserStorage");
const LoginCookie = require("./LoginCookie");

class User {
  constructor(data, res = null) {
    this.body = data;
    this.res = res;
  }

  async login() {
    try {
      const userInfo = await UserStorage.getInfo(this.body.body.id);
      // 아이디 비번 비교처리

      const hashPsw = await UserStorage.hashPsw(
        this.body.body.password,
        userInfo.user_salt
      );
      if (hashPsw.hashPsw != userInfo.user_password) {
        return { result: false, msg: "비밀번호가 다릅니다." };
      }

      // 'auto-login' == 'on' 이면 자동로그인되게
      if (this.body.body["auto-login"] == "true") {
        // 로그인 쿠키 발행
        const cookieData = await LoginCookie.setCookie(
          this.body.body.id,
          this.res
        );
        await LoginCookie.saveToDB(cookieData);
      }

      this.body.session.authenticate = true;
      this.body.session.userName = userInfo.user_name;
      this.body.session.userId = userInfo.user_id;

      // 정상처리 객체
      return {
        result: true,
        msg: userInfo.user_name,
      };
    } catch (error) {
      console.log(error);
      return { result: false, msg: "아이디가 존재하지 않습니다." };
    }
  }

  logout() {
    return new Promise((resolve, reject) => {
      // 세션도 없애고 로그인 쿠키도 없애야함.
      this.body.session.destroy(() => {
        this.body.session;
      });
      this.res.clearCookie("login_cookie");
      resolve("good");
    });
  }

  async register() {
    // 일단 아이디가 존재하는지 체크
    // 비번 같나 체크
    // 통과하면 디비에 셋
    const data = this.body;
    console.log(data);
    try {
      const info = await UserStorage.getInfo(data.id);
      return { result: false, msg: "아이디가 이미 존재합니다." };
    } catch (error) {
      if (data.password != data["confirm-password"])
        return { result: false, msg: "비밀번호가 다릅니다." };

      const hashPsw = await UserStorage.hashPsw(data.password);
      data["hashPsw"] = hashPsw.hashPsw;
      data["salt"] = hashPsw.salt;

      const setInfo = await UserStorage.setInfo(data);
      // 삽입 성공시 반환
      if (setInfo) return { result: true, msg: data.id };
    }
  }
}

module.exports = User;
