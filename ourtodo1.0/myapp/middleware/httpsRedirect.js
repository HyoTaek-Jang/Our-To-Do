const httpsRedirect = (req, res, next) => {
  if (!req.secure) {
    res.redirect("https://" + "ourtodo.site" + req.url);
  } else {
    next();
  }
};

module.exports = httpsRedirect;
