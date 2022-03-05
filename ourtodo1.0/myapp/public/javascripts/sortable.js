$(function () {
  $(".sortable-todo").sortable({
    // revert: true,
    update: function () {
      let index = 1;
      const id = [];
      $(".sortable-todo li").each(function () {
        id.push($(this).attr("id"));
        $(this).children("span").attr("class", index);
        index += 1;
      });

      $.ajax({
        url: "/main/todo/idx",
        type: "PATCH",
        data: { todoIdx: id },
      });
    },
  });
  $(".sortable-todo").disableSelection();
});

$(function () {
  $(".sortable-bookmark").sortable({
    // revert: true,
    update: function () {
      let index = 1;
      const id = [];
      $(".sortable-bookmark li").each(function () {
        id.push($(this).attr("id"));
        $(this).children("span").attr("class", index);
        index += 1;
      });

      $.ajax({
        url: "/main/bookmark/idx",
        type: "PATCH",
        data: { bookmarkIdx: id },
      });
    },
  });
  $(".sortable-bookmark").disableSelection();
});
