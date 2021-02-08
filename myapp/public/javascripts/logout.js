$("#logout").click(() => {
  $.ajax({
    url: "/user/logout",
    type: "POST",
    success: (result) => {
      alert("logout");
      location.href = "/";
    },
    error: (error) => {
      alert(error);
    },
  });
});
