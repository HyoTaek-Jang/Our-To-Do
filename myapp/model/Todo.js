const db = require("../config/db");

class Todo {
  constructor(data) {
    this.id = data;
    console.log(this.id);
  }

  setTodo(content) {
    return new Promise((resolve) => {
      db.query(
        "INSERT INTO todo (todo_content, user_user_id) VALUES(?,?)",
        [content, this.id],
        (err) => {
          if (err) console.err(err);
          resolve();
        }
      );
    });
  }
}

module.exports = Todo;
