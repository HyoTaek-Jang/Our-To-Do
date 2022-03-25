$(".message a").click(function () {
  $(".form-box").animate({ height: "toggle", opacity: "toggle" }, "slow");
});

$("#login-form").submit((e) => {
  e.preventDefault();
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
        location.href = "/main";
      } else {
        alert("Error : " + result.msg);
      }
    },
    error: (err) => {
      alert("로그인 통신에 오류가 발생했습니다.");
    },
  });
});

$("#register-form").submit((e) => {
  e.preventDefault();
  if (
    $("#register-name").val() &&
    $("#register-id").val() &&
    $("#register-password").val() &&
    $("#register-confirm-password").val()
  ) {
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
        if (result.result == true) {
          alert(result.msg + "님 환영합니다.");
          location.href = "/user/login";
        } else alert("Error : " + result.msg);
      },
      error: (err) => {
        alert("회원가입 통신에 오류가 발생했습니다.");
      },
    });
  } else alert("빈칸이 존재합니다.");
});
