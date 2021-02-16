const express = require("express");
const router = express.Router();
const ctrl = require("./ctrl/home.js");

/* GET home page. */
router.get("/", ctrl.output.main);
router.post("/todo", ctrl.process.addTodo);
router.get("/todo", ctrl.output.getTodo);
router.delete("/todo", ctrl.process.delTodo);

module.exports = router;
