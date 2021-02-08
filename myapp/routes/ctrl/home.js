const User = require("../../model/User");

module.exports = {
  output: {
    index: (req, res) => {
      if (req.session.authenticate)
        res.render("main", { username: req.session.userName });
      else res.render("index");
    },
    main: (req, res) => {
      if (!req.session.authenticate) res.render("index");
      else res.render("main", { username: req.session.userName });
    },
    login: (req, res) => {
      if (req.session.authenticate)
        res.render("main", { username: req.session.userName });
      else res.render("login");
    },
  },
  process: {
    login: async (req, res) => {
      const user = new User(req);
      // 로그인 결과 받기
      const loginResult = await user.login();

      if (loginResult.result == true) {
        //로그인 성공
        req.session.save(() => res.json(loginResult));
      } else {
        // 실패
        res.json(loginResult);
      }
    },
    logout: async (req, res) => {
      const user = new User(req);
      await user.logout();

      res.json({ result: true });
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
