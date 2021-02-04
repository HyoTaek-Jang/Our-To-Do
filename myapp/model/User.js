const UserStorage = require("./UserStorage");

class User {
  constructor(data) {
    this.body = data;
  }

  async login() {
    try {
      const userInfo = await UserStorage.getInfo(this.body.id);
      // 아이디 비번 비교처리

      // 'auto-login' == 'on' 이면 자동로그인되게

      // 정상처리 객체
      return { result: true, msg: userInfo.user_name };
    } catch (error) {
      console.log(error);
      return { result: false, msg: `${error}` };
    }
  }
}

module.exports = User;
