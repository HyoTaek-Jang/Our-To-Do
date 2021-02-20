const parentBox = document.querySelector(".toDo-CB");

$(".todoForm").submit((e) => {
  e.preventDefault();
  if (!$("#todo-input").val()) {
    alert("빈칸이 존재합니다.");
    return false;
  }
  $.ajax({
    url: "/main/todo",
    type: "POST",
    data: { "todo-input": $("#todo-input").val() },
    success: () => {
      makeBox($("#todo-input").val());
      $("#todo-input").val("");
      $("#todo-input").focus();
    },
  });
});

function loadTodo() {
  return new Promise((resolve) => {
    $.ajax({
      url: "/main/todo",
      type: "GET",
      dataType: "json",
      success: (data) => {
        resolve(data);
      },
      error: (err) => {
        console.log(err);
      },
    });
  });
}

async function displayTodo() {
  const data = await loadTodo();

  for (i = 0; i < data.length; i++) {
    makeBox(data[i].todo_content, data[i].todo_id);
  }
}

function makeBox(text, id = 0) {
  const CONTENTBOX_TAG = document.createElement("li");
  const CONTENT_TAG = document.createElement("div");
  const P_TAG = document.createElement("P");
  const DELETE_TAG = document.createElement("div");
  const IDX_TAG = document.createElement("span");

  parentBox.appendChild(CONTENTBOX_TAG);
  CONTENTBOX_TAG.appendChild(CONTENT_TAG);
  CONTENTBOX_TAG.appendChild(IDX_TAG);
  CONTENT_TAG.appendChild(P_TAG);
  CONTENTBOX_TAG.appendChild(DELETE_TAG);

  const newId = id;

  CONTENTBOX_TAG.classList.add("l_contentBox-contents", "flex");
  CONTENTBOX_TAG.id = newId;
  CONTENT_TAG.classList.add("toDo-content");
  DELETE_TAG.classList.add("toDo-del");

  DELETE_TAG.addEventListener("click", handleDel);

  P_TAG.innerHTML = text;
}

function handleDel(event) {
  const target = event.target;
  const removeT = target.parentNode;
  parentBox.removeChild(removeT);

  $.ajax({
    url: "/main/todo",
    type: "DELETE",
    data: { del_id: `${removeT.id}` },
    success: (result) => {
      console.log("delete : ", result);
    },
  });
}

function cleanTodo() {
  while (parentBox.hasChildNodes()) {
    parentBox.removeChild(parentBox.firstChild);
  }
}

function init() {
  displayTodo();
}

init();
