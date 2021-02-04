const express = require('express');
const router = express.Router();
const ctrl = require("./ctrl/home.js")

/* GET home page. */
router.get('/', ctrl.output.index);


module.exports = router;
