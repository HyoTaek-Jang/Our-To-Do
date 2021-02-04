$(".message a").click(function () {
  $(".form-box").animate({ height: "toggle", opacity: "toggle" }, "slow");
});

$("#login-button").click(() => {
  $.ajax({
    url: "/login",
    type: "POST",
    dataType: "json",
    data: {
      id: $("#login-id").val(),
      password: $("#login-password").val(),
      "auto-login": $("#auto-login").val(),
    },
    success: (result) => {
      console.log(result);
      alert(result.msg);
    },
    error: (err) => {
      alert("로그인 통신 오류");
    },
  });
});
