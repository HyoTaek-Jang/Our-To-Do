const express = require("express");
const router = express.Router();
const ctrl = require("./ctrl/home.js");

/* GET home page. */
router.get("/", ctrl.output.index);
router.get("/intro", ctrl.output.intro);

module.exports = router;
