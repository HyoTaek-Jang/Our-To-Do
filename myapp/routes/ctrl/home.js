const User = require("../../model/User");
const Todo = require("../../model/Todo");
const Bookmark = require("../../model/Bookmark");

module.exports = {
  output: {
    index: async (req, res) => {
      if (req.session.authenticate) res.redirect("/main");
      else res.render("index");
    },
    intro: async (req, res) => {
      res.render("index");
    },
    main: (req, res) => {
      if (!req.session.authenticate) res.redirect("/");
      else res.render("main", { username: req.session.userName });
    },
    login: (req, res) => {
      if (req.session.authenticate) res.redirect("/main");
      else res.render("login");
    },
    getTodo: async (req, res) => {
      const todo = new Todo(req.session.userId);
      const data = await todo.getTodo();
      res.json(data);
    },
    getBookmark: async (req, res) => {
      const bookmark = new Bookmark(req.session.userId);
      const data = await bookmark.getBookmark();
      res.json(data);
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
    delTodo: async (req, res) => {
      const todo = new Todo(req.body.del_id);
      await todo.delTodo();
      res.json("good");
    },
    addBookmark: async (req, res) => {
      const bookmark = new Bookmark(req.session.userId);
      await bookmark.setBookmark(
        req.body["bookmark-url"],
        req.body["bookmark-title"]
      );
      res.json("good");
    },
    delBookmark: async (req, res) => {
      const bookmark = new Bookmark(req.body.del_id);
      await bookmark.delBookmark();
      res.json("good");
    },
  },
};
