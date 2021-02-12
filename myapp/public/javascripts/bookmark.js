const B_form = document.querySelector(".bookmarkForm");
const B_URL = document.querySelector(".B_URL");
const B_title = document.querySelector(".B_title");
const parentCB = document.querySelector(".bookmark-CB");

const bookmarks = [];

function loadURL() {
  loadBookmark();
  B_form.addEventListener("submit", handleBookmark);
}

function handleBookmark(e) {
  event.preventDefault();
  makeBookmarkBox(B_URL.value, B_title.value);
}

function makeBookmarkBox(URL, TITLE) {
  if (!URL || !TITLE) {
    alert("둘다 똑띠 입력해라 임마.");
  } else {
    const newId = bookmarks.length + 1;

    const bookmarkObj = {
      URL: URL,
      Title: TITLE,
      id: newId,
    };

    const B_contentBox = document.createElement("div");
    const B_hyper = document.createElement("a");
    const B_del = document.createElement("div");

    B_hyper.target = "_black";

    B_contentBox.classList.add("l_contentBox-contents", "flex");
    B_contentBox.id = newId;
    B_hyper.classList.add("bookmark-content-hyper");
    B_del.classList.add("bookmark-content-del");

    parentCB.appendChild(B_contentBox);
    B_contentBox.appendChild(B_hyper);
    B_contentBox.appendChild(B_del);

    B_hyper.href = URL;
    B_hyper.innerHTML = TITLE;

    B_URL.value = "";
    B_title.value = "";

    B_del.addEventListener("click", delBookmark);

    bookmarks.push(bookmarkObj);

    saveBookmark();
  }
}

function saveBookmark() {
  localStorage.setItem("bookmark", JSON.stringify(bookmarks));
}

function loadBookmark() {
  const currentBookmark = localStorage.getItem("bookmark");
  if (currentBookmark !== null) {
    const parsedBookmark = JSON.parse(currentBookmark);
    paintBookmark(parsedBookmark);
  }
}

function paintBookmark(bookmarks) {
  bookmarks.forEach(function (Book) {
    makeBookmarkBox(Book.URL, Book.Title);
  });
}

function delBookmark(e) {
  parentCB.removeChild(e.target.parentNode);
  const afterBookmarks = bookmarks.filter(function (bookmark) {
    return parseInt(e.target.parentNode.id) != bookmark.id;
  });
  localStorage.setItem("bookmark", JSON.stringify(afterBookmarks));
}

function init() {
  loadURL();
}

init();

//  https:// 자동으로 쳐지게 , scheme?? , 날씨, 시작페이지 설정하는 버튼?, 명언 랜덤하게 뜨게?.
