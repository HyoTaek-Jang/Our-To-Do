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
        alert(result.msg + "님 로그인 돼쓰여");
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
        console.log(result);
        if (result.result == true) {
          alert(result.msg + "님 회원가입 ㅊㅋ");
          location.href = "/user/login";
        } else alert("회원가입 실패!! " + result.msg);
      },
      error: (err) => {
        alert("회원가입 통신 오류");
      },
    });
  } else alert("빈칸이 있으면 안되지여~");
});
