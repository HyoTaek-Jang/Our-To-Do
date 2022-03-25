$("#logout").click(() => {
  $.ajax({
    url: "/user/logout",
    type: "POST",
    success: (result) => {
      location.href = "/";
    },
    error: (error) => {
      alert(error);
    },
  });
});
