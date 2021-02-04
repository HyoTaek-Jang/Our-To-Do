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
      const data = await user.login();
      res.json(data);
    },
  },
};
