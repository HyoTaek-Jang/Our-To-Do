const User = require("../../model/User");

module.exports = {
  output: {
    index: (req, res) => {
      if (req.session.authenticate) res.redirect("/main");
      else if (req.cookies.login_cookie) {
        // 로그인쿠키 있나 확인하고 있으면 db체크후 사용자면 로그인 처리
        console.log(req.cookies.login_cookie);
        res.render("index");
      } else res.render("index");
    },
    main: (req, res) => {
      if (!req.session.authenticate) res.redirect("/");
      else res.render("main", { username: req.session.userName });
    },
    login: (req, res) => {
      if (req.session.authenticate) res.redirect("/main");
      else res.render("login");
    },
  },
  process: {
    login: async (req, res) => {
      const user = new User(req, res);
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
      // 디비 login_cookie삭제
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
