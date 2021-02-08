$(".message a").click(function () {
  $(".form-box").animate({ height: "toggle", opacity: "toggle" }, "slow");
});

$("#login-button").click(() => {
  $.ajax({
    url: "/user/login",
    type: "POST",
    dataType: "json",
    data: {
      id: $("#login-id").val(),
      password: $("#login-password").val(),
      "auto-login": $("#auto-login").is(":checked"),
    },
    success: (result) => {
      if (result.result) {
        alert(result.msg);
        location.href = "/main";
      } else {
        alert(result.msg);
      }
    },
    error: (err) => {
      alert("로그인 통신 오류");
    },
  });
});

$("#register-button").click(() => {
  $.ajax({
    url: "/user/register",
    type: "POST",
    dataType: "json",
    data: {
      name: $("#register-name").val(),
      id: $("#register-id").val(),
      password: $("#register-password").val(),
      "confirm-password": $("#register-confirm-password").val(),
    },
    success: (result) => {
      console.log(result);
      alert(result.msg);
    },
    error: (err) => {
      alert("회원가입 통신 오류");
    },
  });
});
