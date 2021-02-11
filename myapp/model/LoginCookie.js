const db = require("../config/db");
const crypto = require("crypto");

class LoginCookie {
  static setCookie(req, res) {
    return new Promise((resolve, reject) => {
      db.query(
        "SELECT user_salt FROM user WHERE user_id = ?",
        [req.body.id],
        (err, data) => {
          crypto.pbkdf2(
            req.body.id,
            Math.random().toString(36).substr(2, 11),
            12841,
            64,
            "sha512",
            (err, key) => {
              res.cookie(
                "login_cookie",
                `{
                    cookie_identify: ${data[0].user_salt},
                    cookie_user_id: ${req.body.id},
                    cookie_token: ${key.toString("base64")},
                  }`,
                {
                  httpOnly: true,
                  maxAge: 30 * 24 * 60 * 60 * 1000,
                }
              );
              resolve({
                cookie_identify: data[0].user_salt,
                cookie_user_id: req.body.id,
                cookie_token: key.toString("base64"),
              });
            }
          );
        }
      );
    });
  }

  static saveToDB(data) {
    return new Promise((resolve, reject) => {
      db.query(
        "INSERT INTO login_cookie (cookie_identify, cookie_user_id, cookie_token) VALUES(?,?,?)",
        [data.cookie_identify, data.cookie_user_id, data.cookie_token],
        (err) => {
          if (err) console.err(err);
          resolve("good");
        }
      );
    });
  }
}

module.exports = LoginCookie;
