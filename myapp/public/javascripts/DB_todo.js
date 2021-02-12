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
      $("#todo-input").val("");
      $("#todo-input").focus();
    },
  });
});
