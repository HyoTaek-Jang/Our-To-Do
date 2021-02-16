const express = require("express");
const router = express.Router();
const ctrl = require("./ctrl/home.js");

/* GET home page. */
router.get("/", ctrl.output.main);

router.post("/todo", ctrl.process.addTodo);
router.get("/todo", ctrl.output.getTodo);
router.delete("/todo", ctrl.process.delTodo);

router.post("/bookmark", ctrl.process.addBookmark);
router.get("/bookmark", ctrl.output.getBookmark);
router.delete("/bookmark", ctrl.process.delBookmark);

router.post("/weather", ctrl.process.getAPIKEY);

module.exports = router;
