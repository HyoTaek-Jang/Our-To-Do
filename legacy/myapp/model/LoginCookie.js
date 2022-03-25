const db = require("../config/db");
const crypto = require("crypto");

class LoginCookie {
  constructor(loginCookie) {
    this.cookie = loginCookie;
  }

  static setCookie(user_id, res) {
    return new Promise((resolve, reject) => {
      db.query(
        "SELECT user_salt FROM user WHERE user_id = ?",
        [user_id],
        (err, data) => {
          crypto.pbkdf2(
            user_id,
            Math.random().toString(36).substr(2, 11),
            12841,
            64,
            "sha512",
            (err, key) => {
              const dataSet = {
                cookie_identify: data[0].user_salt,
                cookie_user_id: user_id,
                cookie_token: key.toString("base64"),
              };
              res.cookie("login_cookie", JSON.stringify(dataSet), {
                httpOnly: true,
                maxAge: 30 * 24 * 60 * 60 * 1000,
                secure: true,
              });
              resolve(dataSet);
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
          if (err) console.error(err);
          resolve("good");
        }
      );
    });
  }

  static checkCookie(cookieData) {
    return new Promise((resolve, reject) => {
      db.query(
        "SELECT cookie_identify, cookie_user_id, cookie_token FROM login_cookie WHERE cookie_user_id = ?",
        [cookieData.cookie_user_id],
        (err, data) => {
          if (data.length) {
            if (
              data[0].cookie_identify == cookieData.cookie_identify &&
              data[0].cookie_token == cookieData.cookie_token
            )
              resolve({ result: true, cookie_user_id: data[0].cookie_user_id });
            else
              resolve({
                result: false,
                cookie_user_id: data[0].cookie_user_id,
              });
          }
          reject("checkCookie");
        }
      );
    });
  }

  static getSession(req, user_id) {
    return new Promise((resolve) => {
      db.query(
        "SELECT * FROM user WHERE user_id = ?",
        [user_id],
        (err, data) => {
          req.session.authenticate = true;
          req.session.userName = data[0].user_name;
          req.session.userId = data[0].user_id;

          resolve("good");
        }
      );
    });
  }

  static deleteToDB(user_id) {
    return new Promise((resolve) => {
      db.query(
        "DELETE FROM login_cookie WHERE cookie_user_id = ?",
        [user_id],
        (err) => {
          if (err) console.error(err);

          resolve("good");
        }
      );
    });
  }
}

module.exports = LoginCookie;
