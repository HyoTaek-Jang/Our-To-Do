const express = require("express");
const router = express.Router();

router.use("/", (req, res, next) => {
  res.cookie("connect.sid", req.cookies["connect.sid"], {
    httpOnly: true,
    secure: false,
    maxAge: 2 * 60 * 60 * 1000,
  });
  next();
});

module.exports = router;
