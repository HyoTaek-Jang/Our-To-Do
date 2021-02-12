const User = require("../../model/User");
const LoginCookie = require("../../model/LoginCookie");
const Todo = require("../../model/Todo");
const AutoLogin = require("../../model/AutoLogin");

module.exports = {
  output: {
    index: async (req, res) => {
      if (req.session.authenticate) res.redirect("/main");
      else if (req.cookies.login_cookie) {
        // 로그인쿠키 있나 확인하고 있으면 db체크후 사용자면 로그인 처리

        try {
          const checkCookie = await LoginCookie.checkCookie(
            req.cookies.login_cookie
          );
          await LoginCookie.deleteToDB(checkCookie.cookie_user_id);
          if (checkCookie.result) {
            // 세션받기 그리고 db삭제하고 다시 넣기, 쿠키도 다시
            const cookieData = await LoginCookie.setCookie(
              checkCookie.cookie_user_id,
              res
            );
            await LoginCookie.saveToDB(cookieData);

            await LoginCookie.getSession(req, checkCookie.cookie_user_id);
            res.redirect("/main");
          } else {
            // db 삭제 그리고 res 홈
            res.render("index");
          }
        } catch (error) {
          res.render("index");
        }
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
      const user = new User(req, res);
      await user.logout();

      res.json({ result: true });
    },
    register: async (req, res) => {
      const user = new User(req.body);
      const registerResult = await user.register();
      res.json(registerResult);
    },
    addTodo: async (req, res) => {
      const todo = new Todo(req.session.userId);
      await todo.setTodo(req.body["todo-input"]);
      res.json(req.body);
    },
  },
};
