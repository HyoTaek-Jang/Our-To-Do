const db = require("../config/db");

class UserStorage {
  static getInfo(id) {
    return new Promise((resolve, reject) => {
      db.query("SELECT * FROM user WHERE user_id = ?", [id], (err, data) => {
        // 아이디 존재하지 않으면
        if (data[0] == undefined) {
          return reject(`아이디가 존재하지 않습니다.`);
        } // 아이디 존재하면
        return resolve(data[0]);
      });
    });
  }

  static setInfo(data) {
    return new Promise((resolve, reject) => {
      db.query(
        "INSERT INTO user (user_id,user_password,user_name) VALUES(?,?,?)",
        [data.id, data.password, data.name],
        (err) => {
          if (err) return resolve(`${err}`);
          return resolve(true);
        }
      );
    });
  }
}

module.exports = UserStorage;
