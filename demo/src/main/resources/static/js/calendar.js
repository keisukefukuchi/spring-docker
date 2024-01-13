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
      url: "/api/v1/all", //ここにRestControllerを呼び出すurlを記載
    },
    eventDidMount: function (info) {
      if (info.event.id === "sum-expense") {
        info.el.querySelector(".fc-event-title").style.fontWeight = "bold"; // 太字に変更
        info.el.style.backgroundColor = "#fff";
        info.el.querySelector(".fc-event-title").style.color = "red"; // 赤色に変更
      }

      if (info.event.id === "sum-income") {
        info.el.querySelector(".fc-event-title").style.fontWeight = "bold"; // 太字に変更
        info.el.style.backgroundColor = "#fff";
        info.el.querySelector(".fc-event-title").style.color = "green"; // 赤色に変更
      }

      if (info.event.start.toDateString() === new Date().toDateString()) {
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
        titleFormat: {
          year: "numeric",
          month: "long",
        },
      },
    },
  });

  calendar.render();

  document
    .getElementsByClassName("fc-prev-button")[0]
    .addEventListener("click", function () {
      // fc-prev-buttonがクリックされたときの処理
      fetchDataAndUpdate();
    });

  document
    .getElementsByClassName("fc-next-button")[0]
    .addEventListener("click", function () {
      // fc-next-buttonがクリックされたときの処理
      fetchDataAndUpdate();
    });

  window.addEventListener("load", function () {
    if (window.location.pathname === "/") {
      console.log(1);
      fetchDataAndUpdate();
    }
  });

  // 前月や次月が表示されたときの処理
  function fetchDataAndUpdate() {
    // カレンダーのタイトルを取得
    let calendarTitle = calendar.view.title;

    let [year, month] = calendarTitle.split("年");
    console.log(calendarTitle.split(" "));
    console.log(parseInt(year));
    console.log(parseInt(month));

    // サーバーからデータを取得
    fetchDataFromServer(parseInt(year), parseInt(month))
      .then((data) => {
        // 月ごとの支出額と収入額を更新
        updateAmounts(data.totalExpense, data.totalIncome);
      })
      .catch((error) => {
        console.error("データの取得に失敗しました:", error);
      });
  }

  // サーバーからデータを取得する関数（先ほどと同じものを使用）
  function fetchDataFromServer(year, month) {
    return fetch(`/api/v1/data/${year}/${month}`) // サーバーのAPIエンドポイントに合わせて変更
      .then((response) => response.json());
  }

  // 月ごとの支出額と収入額を更新する関数（先ほどと同じものを使用）
  function updateAmounts(expenseAmount, incomeAmount) {
    document.getElementById("expenseAmount").textContent = expenseAmount + "円";
    document.getElementById("incomeAmount").textContent = incomeAmount + "円";
  }
});
