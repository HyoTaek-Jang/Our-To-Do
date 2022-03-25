const toggle = document.querySelectorAll(".toggle");
const toggleTD = document.querySelector(".toDo-CB");
const toggleBM = document.querySelector(".bookmark-CB");
const SHOWING_CN = "show";

function loadtoggle() {
  toggle.forEach(function (e) {
    e.addEventListener("click", handleToggle);
  });
}

function handleToggle(event) {
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

loadtoggle();
