const UserStorage = require("./UserStorage");
const LoginCookie = require("./LoginCookie");

class User {
  constructor(data, res = null) {
    this.body = data;
    this.res = res;
  }

  async login() {
    try {
      // 해당하는 id가 존재하는지 체크
      const userInfo = await UserStorage.getInfo(this.body.body.id);

      // 사용자 salt를 통해 똑같이 암호화를 하여 해쉬 값 비교를 한다. 이를 가지고 패스워드 체크
      const hashPsw = await UserStorage.hashPsw(
        this.body.body.password,
        userInfo.user_salt
      );
      if (hashPsw.hashPsw != userInfo.user_password) {
        return { result: false, msg: "비밀번호가 다릅니다." };
      }

      // 'auto-login' == 'on' 이면 로그인 쿠키 발행
      if (this.body.body["auto-login"] == "true") {
        const cookieData = await LoginCookie.setCookie(
          this.body.body.id,
          this.res
        );
        await LoginCookie.saveToDB(cookieData);
      }

      // 로그인 세션 발행
      this.body.session.authenticate = true;
      this.body.session.userName = userInfo.user_name;
      this.body.session.userId = userInfo.user_id;

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
      // 세션과 로그인 쿠키 삭제
      this.body.session.destroy(() => {
        this.body.session;
      });
      this.res.clearCookie("login_cookie");
      resolve("good");
    });
  }

  async register() {
    const data = this.body;
    try {
      /*
      1. 아이디 존재여부 판단
      2. 비밀번호 일치여부 판단
      3. 해쉬를 통해 salt와 암호화된 비밀번호 저장
      */
      const info = await UserStorage.getInfo(data.id);
      return { result: false, msg: "아이디가 이미 존재합니다." };
    } catch (error) {
      if (data.password != data["confirm-password"])
        return { result: false, msg: "비밀번호가 다릅니다." };

      const hashPsw = await UserStorage.hashPsw(data.password);
      data["hashPsw"] = hashPsw.hashPsw;
      data["salt"] = hashPsw.salt;

      const setInfo = await UserStorage.setInfo(data);
      if (setInfo) return { result: true, msg: data.name };
    }
  }
}

module.exports = User;
