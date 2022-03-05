// 사용자가 요청을 보낼 때마다, 로그인 세션을 새로 갱신하여 연장한다.
const cookieMiddle = (req, res, next) => {
  if (req.cookies["connect.sid"]) {
    res.cookie("connect.sid", req.cookies["connect.sid"], {
      httpOnly: true,
      secure: true,
      maxAge: 2 * 60 * 60 * 1000,
    });
  }
  next();
};

module.exports = cookieMiddle;
