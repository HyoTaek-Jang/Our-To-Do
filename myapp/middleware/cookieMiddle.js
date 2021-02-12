const cookieMiddle = (req, res, next) => {
  if (req.cookies["connect.sid"]) {
    res.cookie("connect.sid", req.cookies["connect.sid"], {
      httpOnly: true,
      secure: false,
      maxAge: 2 * 60 * 60 * 1000,
    });
  }
  next();
};

module.exports = cookieMiddle;
