const express = require("express");
const router = express.Router();
const ctrl = require("./ctrl/home.js");

/* GET home page. */
router.get("/", ctrl.output.login);
router.post("/", ctrl.process.login);
// router.post("/register", ctrl.process.register);

module.exports = router;
