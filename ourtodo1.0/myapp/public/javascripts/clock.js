const clockContainer = document.querySelector(".main-clock");

function getTime() {
  const date = new Date();
  const min = date.getMinutes();
  const hour = date.getHours();
  const sec = date.getSeconds();

  clockContainer.innerHTML = `${hour > 9 ? hour : `0${hour}`}:${
    min > 9 ? min : `0${min}`
  }`;
}

function init() {
  getTime();
  setInterval(getTime, 1000);
}

init();
