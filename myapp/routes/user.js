const express = require("express");
const router = express.Router();
const ctrl = require("./ctrl/home.js");

/* GET home page. */
router.get("/login", ctrl.output.login);
router.post("/login", ctrl.process.login);
router.post("/register", ctrl.process.register);
router.post("/logout", ctrl.process.logout);

module.exports = router;
