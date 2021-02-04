const express = require('express');
const router = express.Router();
const ctrl = require("./ctrl/home.js")

/* GET home page. */
router.get('/', ctrl.output.login);
// router.post('/', ctrl.proccess.login);
// router.post('/register', ctrl.proccess.login);


module.exports = router;
