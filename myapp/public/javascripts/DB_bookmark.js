const parentCB = document.querySelector(".bookmark-CB");

$(".bookmarkForm").submit((e) => {
  e.preventDefault();
  const title = $("#bookmark-input-title").val();
  let url = $("#bookmark-input-url").val();

  if (!title || !url) {
    alert("빈칸이 존재합니다.");
    return false;
  }

  if (!url.includes("http")) {
    url = "http://" + url;
  }
  $.ajax({
    url: "/main/bookmark",
    type: "POST",
    data: {
      "bookmark-title": title,
      "bookmark-url": url,
    },
    success: (result) => {
      displayBookmark(result["LAST_INSERT_ID()"], url, title);

      $("#bookmark-input-title").val("");
      $("#bookmark-input-url").val("");
      $("#bookmark-input-title").focus();
    },
  });
});

function loadBookmark() {
  $.ajax({
    url: "/main/bookmark",
    type: "GET",
    dataType: "json",
    success: (data) => {
      for (i = 0; i < data.length; i++) {
        displayBookmark(
          data[i].bookmark_id,
          data[i].bookmark_link,
          data[i].bookmark_title
        );
      }
    },
  });
}

function displayBookmark(id = 0, url, title) {
  const B_contentBox = document.createElement("li");
  const B_hyper = document.createElement("a");
  const B_del = document.createElement("div");
  const IDX_TAG = document.createElement("span");

  B_contentBox.classList.add("l_contentBox-contents", "flex");
  B_contentBox.id = id;
  B_hyper.classList.add("bookmark-content-hyper");
  B_del.classList.add("bookmark-content-del");

  parentCB.appendChild(B_contentBox);
  B_contentBox.appendChild(B_hyper);
  B_contentBox.appendChild(IDX_TAG);
  B_contentBox.appendChild(B_del);

  B_hyper.href = url;
  B_hyper.innerHTML = title;
  B_hyper.target = "_blank";

  B_del.addEventListener("click", delBookmark);
}

function delBookmark(e) {
  const removeT = e.target.parentNode;
  parentCB.removeChild(removeT);

  $.ajax({
    url: "/main/bookmark",
    type: "DELETE",
    data: { del_id: `${removeT.id}` },
    success: (result) => {
      console.log("delete : ", result);
    },
  });
}

function cleanBookmark() {
  while (parentCB.hasChildNodes()) {
    parentCB.removeChild(parentCB.firstChild);
  }
}

function init() {
  loadBookmark();
}

init();
