const db = require("../config/db");

class Bookmark {
  constructor(id) {
    this.id = id;
  }

  setBookmark(url, title) {
    return new Promise((resolve, reject) => {
      db.query(
        "INSERT INTO bookmark (bookmark_title, bookmark_link, user_user_id) VALUES(?,?,?)",
        [title, url, this.id],
        (err) => {
          if (err) console.log(err);
          db.query("SELECT LAST_INSERT_ID()", (err, data) => {
            if (err) console.log(err);
            resolve(data[0]);
          });
        }
      );
    });
  }

  getBookmark() {
    return new Promise((resolve, reject) => {
      db.query(
        "SELECT bookmark_id, bookmark_title, bookmark_link FROM bookmark WHERE user_user_id = ? ORDER BY bookmark_idx",
        [this.id],
        (err, data) => {
          if (err) console.log(err);

          resolve(data);
        }
      );
    });
  }

  delBookmark() {
    return new Promise((resolve) => {
      db.query(
        "DELETE FROM bookmark WHERE bookmark_id = ? ",
        [this.id],
        (err) => {
          if (err) console.error(err);
          resolve();
        }
      );
    });
  }

  patchBookmarkIdx() {
    this.id.forEach((item, index) => {
      db.query(
        "UPDATE bookmark SET bookmark_idx = ? WHERE bookmark_id = ?",
        [index + 1, item],
        (err) => {
          if (err) console.error(err);
        }
      );
    });
  }
}

module.exports = Bookmark;
