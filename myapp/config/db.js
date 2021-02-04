const mysql = require("mysql");

const db = mysql.createConnection({
  host: "ourtodo.ck2ibekxygnd.ap-northeast-2.rds.amazonaws.com",
  user: "admin",
  password: "wkdWkddl1218",
  database: "ourtodo",
});

db.connect();

module.exports = db;

// PORT=3000

// DB_HOST="ourtodo.ck2ibekxygnd.ap-northeast-2.rds.amazonaws.com"
// DB_USER="admin"
// DB_PSWORD="wkdWkddl1218"
// DB_DATABASE="ourtodo"
