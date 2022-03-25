const crypto = require("crypto");
const db = require("../config/db");

class UserStorage {
  static getInfo(id) {
    return new Promise((resolve, reject) => {
      db.query("SELECT * FROM user WHERE user_id = ?", [id], (err, data) => {
        if (data[0] == undefined) {
          return reject(`아이디가 존재하지 않습니다.`);
        }
        return resolve(data[0]);
      });
    });
  }

  static setInfo(data) {
    return new Promise((resolve, reject) => {
      console.log(data);
      db.query(
        "INSERT INTO user (user_id,user_password,user_name, user_salt) VALUES(?,?,?,?)",
        [data.id, data.hashPsw, data.name, data.salt],
        (err) => {
          if (err) return resolve(`${err}`);
          return resolve(true);
        }
      );
    });
  }

  static hashPsw(psw, salt = "0") {
    return new Promise((resolve, reject) => {
      const hashPsw = crypto.randomBytes(64, (err, buf) => {
        if (salt == "0") {
          salt = buf.toString("base64");
        }
        crypto.pbkdf2(psw, salt, 32367, 64, "sha512", (err, key) => {
          resolve({
            salt: salt,
            hashPsw: key.toString("base64"),
          });
        });
      });
    });
  }
}

module.exports = UserStorage;
