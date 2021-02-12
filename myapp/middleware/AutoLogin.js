const LoginCookie = require("../model/LoginCookie");

const autoLogin = async (req, res, next) => {
  if (!req.cookies.login_cookie) return next();
  // 로그인쿠키 있나 확인하고 있으면 db체크후 사용자면 로그인 처리

  try {
    const cookie = JSON.parse(req.cookies.login_cookie);
    const checkCookie = await LoginCookie.checkCookie(cookie);
    await LoginCookie.deleteToDB(checkCookie.cookie_user_id);
    if (checkCookie.result) {
      // 세션받기 그리고 db삭제하고 다시 넣기, 쿠키도 다시
      const cookieData = await LoginCookie.setCookie(
        checkCookie.cookie_user_id,
        res
      );
      await LoginCookie.saveToDB(cookieData);

      await LoginCookie.getSession(req, checkCookie.cookie_user_id);
      req.session.save();
      next();
    }
  } catch (error) {
    console.error(error);
    next();
  }
};

module.exports = autoLogin;
