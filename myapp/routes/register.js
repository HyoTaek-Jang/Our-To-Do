const express = require("express");
const router = express.Router();
const ctrl = require("./ctrl/home.js");

/* GET home page. */
router.post("/", ctrl.process.register);

module.exports = router;
