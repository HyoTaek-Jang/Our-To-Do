const session = require("express-session");
const MySQLStore = require("express-mysql-session")(session);

const dbStore = new MySQLStore({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PSWORD,
  database: process.env.DB_DATABASE,
  port: 3306,
});

module.exports = dbStore;
