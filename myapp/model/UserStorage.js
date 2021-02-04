const db = require("../config/db");

class UserStorage {
  static getInfo(id) {
    return new Promise((resolve) => {
      db.query("SELECT * FROM user WHERE user_id = ?", [id], (err, data) => {
        // 아이디 존재하지 않으면
        if (err) return reject(`${err}`);
        // 아이디 존재하면
        return resolve(data[0]);
      });
    });
  }
}

module.exports = UserStorage;
