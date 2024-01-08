document.addEventListener("DOMContentLoaded", function () {
  let calendarEl = document.getElementById("calendar");
  let modal = document.getElementById("expenseModal");
  let dateInput = document.getElementById("expenseDate");
  const closeBtn = document.getElementById("closeBtn");

  let calendar = new FullCalendar.Calendar(calendarEl, {
    locale: "ja",
    height: "auto",
    headerToolbar: {
      start: "prev",
      center: "title",
      end: "next",
    },
    events: {
      url: "/api/event/all", //ここにRestControllerを呼び出すurlを記載
    },
    eventDidMount: function (info) {
      if (info.event.id === "sum-expense") {
        info.el.querySelector(".fc-event-title").style.fontWeight = "bold"; // 太字に変更
        info.el.style.backgroundColor = "#fff";
        info.el.querySelector(".fc-event-title").style.color = "red"; // 赤色に変更
      }

      if (
        info.event.id === "sum-expense" &&
        info.event.start.toDateString() === new Date().toDateString()
      ) {
        info.el.style.backgroundColor = "rgba(255, 220, 40, 0.01)";
        info.el.style.borderColor = "rgba(255, 220, 40, 0.01)"; // ボーダーカラーを設定
      }
    },
    initialView: "dayGridMonth",
    dateClick: function (info) {
      // モーダルを表示
      modal.style.display = "flex";
      // クリックされた日付をフォームの入力に設定
      dateInput.value = info.dateStr;
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

  closeBtn.addEventListener("click", () => {
    modal.style.display = "none";
  });
});
