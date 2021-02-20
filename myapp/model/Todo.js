const db = require("../config/db");

class Todo {
  constructor(data) {
    this.id = data;
  }

  setTodo(content) {
    return new Promise((resolve) => {
      db.query(
        "INSERT INTO todo (todo_content, user_user_id) VALUES(?,?)",
        [content, this.id],
        (err) => {
          if (err) console.error(err);
          resolve();
        }
      );
    });
  }

  getTodo() {
    return new Promise((resolve) => {
      db.query(
        "SELECT todo_id, todo_content FROM todo WHERE user_user_id = ? ORDER BY todo_idx ",
        [this.id],
        (err, data) => {
          if (err) console.error(err);

          resolve(data);
        }
      );
    });
  }

  delTodo() {
    return new Promise((resolve) => {
      db.query("DELETE FROM todo WHERE todo_id = ? ", [this.id], (err) => {
        if (err) console.error(err);
        resolve();
      });
    });
  }

  patchTodoIdx() {
    this.id.forEach((item, index) => {
      db.query(
        "UPDATE todo SET todo_idx = ? WHERE todo_id = ?",
        [index + 1, item],
        (err) => {
          if (err) console.error(err);
        }
      );
    });
  }
}

module.exports = Todo;
