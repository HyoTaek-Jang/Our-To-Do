const LoginCookie = require("../model/LoginCookie");

const autoLogin = async (req, res, next) => {
  // 로그인 쿠키가 없다면 return
  if (!req.cookies.login_cookie) return next();

  /*
  1. db에 최근 자동 로그인 쿠키와 사용자가 들고 있는 쿠키가 같지 않다면 도난 당한것.
  2. db 최근 쿠키와 사용자 쿠키가 같다면 db에 로그인 쿠키를 지우고
  3. 새롭게 로그인 쿠키를 발급받아 쿠키 유효기간을 연장, db에 최근 쿠키로 갈아끼운다.
  4. 로그인 세션을 부여하여 로그인 작업 완료.
  */
  try {
    const cookie = JSON.parse(req.cookies.login_cookie);
    const checkCookie = await LoginCookie.checkCookie(cookie);
    await LoginCookie.deleteToDB(checkCookie.cookie_user_id);
    if (checkCookie.result) {
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
