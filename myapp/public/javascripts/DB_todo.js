$(".todoForm").submit((e) => {
  e.preventDefault();
  if (!$("#todo-input").val()) {
    alert("인풋비었음");
    return false;
  }
  $.ajax({
    url: "/main/todo",
    type: "POST",
    data: { "todo-input": $("#todo-input").val() },
    success: (result) => {
      //db로드해서 창 띄우기
      // 디비에서 제이슨으로 주루루룩~! 받고 그걸 화면에 띄우쟝!
      $("#todo-input").val("");
      $("#todo-input").focus();
    },
  });
});
