const express = require("express");
const router = express.Router();
const ctrl = require("./ctrl/home.js");

/* GET home page. */
router.get("/", ctrl.output.main);
router.post("/todo", ctrl.process.addTodo);

module.exports = router;
