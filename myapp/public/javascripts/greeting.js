const form = document.querySelector(".main-enterName");
const input = form.querySelector("input");
const greeting = document.querySelector(".main-welcome");
const clearAll = document.querySelector(".clearAll");

const USER_LS = "currentUser";
const SHOWING_CN = "show";

function handleSubmit(event) {
  event.preventDefault();
  const currentValue = input.value;
  paintGreeting(currentValue);
  saveName(currentValue);
}

function handleClear(event) {
  alert("clear");
  // localStorage.clear();
  // location.reload()
}

function askForName() {
  form.classList.add(SHOWING_CN);
  form.addEventListener("submit", handleSubmit);
}

function saveName(text) {
  localStorage.setItem(USER_LS, text);
}

function paintGreeting(text) {
  form.classList.remove(SHOWING_CN);
  greeting.classList.add(SHOWING_CN);
  // greeting.innerHTML = `Enjoy your day, ${text} !`;
}

function loadName() {
  const currentUser = localStorage.getItem(USER_LS);
  if (currentUser === null) {
    // none classList.remove(show) or add 로 show deshow 해버리기
    askForName();
  } else {
    paintGreeting(currentUser);
  }
}

function init() {
  clearAll.addEventListener("click", handleClear);
  // loadName();
}

init();
