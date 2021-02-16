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
    console.log(data[i]);
    makeBox(data[i].todo_content, data[i].todo_id);
  }
}

const parentBox = document.querySelector(".l_contentBox");

function makeBox(text, id) {
  const CONTENTBOX_TAG = document.createElement("div");
  const CONTENT_TAG = document.createElement("div");
  const P_TAG = document.createElement("P");
  const DELETE_TAG = document.createElement("div");

  parentBox.appendChild(CONTENTBOX_TAG);
  CONTENTBOX_TAG.appendChild(CONTENT_TAG);
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

  // todo update 요청

  // displayTodo();
}

const toggle = document.querySelectorAll(".toggle");

const toggleTD = document.querySelector(".toDo-CB");
const toggleBM = document.querySelector(".bookmark-CB");

function loadtoggle() {
  toggle.forEach(function (e) {
    e.addEventListener("click", handleToggle);
  });
}

function handleToggle(event) {
  console.log(event.target.parentNode.className);

  if (event.target.parentNode.className == "toDo-header") {
    if (event.target.innerHTML == "SHOW") {
      event.target.innerHTML = "HIDE";
      toggleTD.classList.remove(SHOWING_CN);
    } else {
      event.target.innerHTML = "SHOW";
      toggleTD.classList.add(SHOWING_CN);
    }
  } else {
    if (event.target.innerHTML == "SHOW") {
      event.target.innerHTML = "HIDE";
      toggleBM.classList.remove(SHOWING_CN);
    } else {
      event.target.innerHTML = "SHOW";
      toggleBM.classList.add(SHOWING_CN);
    }
  }
}

function init() {
  displayTodo();
  loadtoggle();
}

init();
