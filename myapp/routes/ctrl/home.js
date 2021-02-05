const User = require("../../model/User");

module.exports = {
  output: {
    index: (req, res) => {
      res.render("index");
    },
    main: (req, res) => {
      res.render("main");
    },
    login: (req, res) => {
      res.render("login");
    },
  },
  process: {
    login: async (req, res) => {
      const user = new User(req.body);
      // 로그인 결과 받기
      const loginResult = await user.login();

      if (loginResult.result == true) {
        //로그인 성공
        res.json(loginResult);
      } else {
        // 실패
        res.json(loginResult);
      }
    },
    register: async (req, res) => {
      const user = new User(req.body);
      const registerResult = await user.register();
      res.json(registerResult);
    },
    todo: (req, res) => {
      res.json(req.body);
    },
  },
};
