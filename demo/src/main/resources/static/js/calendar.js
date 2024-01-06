document.addEventListener("DOMContentLoaded", function () {
  let calendarEl = document.getElementById("calendar");
  let modal = document.getElementById("expenseModal");
  let dateInput = document.getElementById("expenseDate");
  let calendar = new FullCalendar.Calendar(calendarEl, {
    locale: "ja",
    height: "auto",
    headerToolbar: {
      start: "prev",
      center: "title",
      end: "next",
    },
    initialView: "dayGridMonth",
    dateClick: function (info) {
      // モーダルを表示
      modal.style.display = "flex";

      // クリックされた日付をフォームの入力に設定
      dateInput.value = info.dateStr;
      // Spring Bootにデータを送信するためのAjaxリクエスト
      // sendDataToSpringBoot(info.dateStr);
    },
    buttonText: { month: "月" },
    buttonHints: {
      prev: "前の$0",
      next: "次の$0",
    },
    dayCellContent: function (arg) {
      // 日を消す
      return arg.date.getDate();
    },
    views: {
      dayGridMonth: {
        titleFormat: { month: "long" },
      },
    },
  });
  calendar.render();

  // モーダルが閉じられたときに非表示にする
  modal.addEventListener("click", function (event) {
    if (event.target === modal) {
      modal.style.display = "none";
      calendar.unselect();
    }
  });

  // フォームが送信されたときにSpring Bootにデータを送信するイベントリスナーを追加
  document
    .getElementById("expenseForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      sendDataToSpringBoot(dateInput.value);
      modal.style.display = "none";
    });

  function sendDataToSpringBoot(selectedDate) {
    // リクエストデータを作成
    let url = "/expense?selectedDate=" + encodeURIComponent(selectedDate);

    // GETリクエストを送信
    fetch(url)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.text();
      })
      .then((data) => {
        // ページを切り替える
        window.location.href = url;
      })
      .catch((error) => {
        console.error(
          "There has been a problem with your fetch operation:",
          error
        );
      });
  }
});
