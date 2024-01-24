document.addEventListener("DOMContentLoaded", function () {
  let calendarEl = document.getElementById("calendar");
  let modal = document.getElementById("modal");
  let dateInput = document.getElementById("date");
  const closeBtn = document.getElementById("closeBtn");
  let incomeContainer = document.getElementById("day-income");
  let expenseContainer = document.getElementById("day-expense");

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
      dateInput.textContent = info.dateStr;

      let [year, month, day] = info.dateStr.split("-");

      // サーバーからデータを取得
      fetchTheDayData(parseInt(year), parseInt(month), parseInt(day))
        .then((data) => {
          data.forEach((entry) => {
            console.log(entry);
            if (entry.type === "income") {
              // 収入データの処理
              let incomeElement = document.createElement("ul");
              let nameElement = document.createElement("li");
              let priceElement = document.createElement("li");

              incomeElement.classList.add("income-list")
              nameElement.classList.add("income-name");
              priceElement.classList.add("income-price");

              nameElement.textContent = entry.theDayIncomeName;
              priceElement.textContent = entry.theDayIncomePrice;

              incomeElement.appendChild(nameElement);
              incomeElement.appendChild(priceElement);
              incomeContainer.appendChild(incomeElement);

            } else if (entry.type === "expense") {
              // 支出データの処理
              let expenseElement = document.createElement("ul");
              let nameElement = document.createElement("li");
              let priceElement = document.createElement("li");
              let paymentTypeElement = document.createElement("li");
              let categoryElement = document.createElement("li");

              incomeElement.classList.add("income-list")
              nameElement.classList.add("income-name");
              priceElement.classList.add("income-price");
              paymentTypeElement.classList.add("income-payment-type");
              categoryElement.classList.add("income-category");

              nameElement.textContent = entry.theDayExpenseName;
              priceElement.textContent = entry.theDayExpensePrice;
              paymentTypeElement.textContent = entry.theDayExpensePaymentType;
              categoryElement.textContent = entry.theDayExpenseCategory;

              expenseElement.appendChild(nameElement);
              expenseElement.appendChild(priceElement);
              expenseElement.appendChild(paymentTypeElement);
              expenseElement.appendChild(categoryElement);
              expenseContainer.appendChild(expenseElement);
            }
          });
        })
        .catch((error) => {
          console.error("データの取得に失敗しました:", error);
        });
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

  modal.addEventListener("click", function (event) {
    if (event.target === modal) {
      modal.style.display = "none";
      calendar.unselect();
    }
  });

  closeBtn.addEventListener("click", () => {
    modal.style.display = "none";
  });
  window.addEventListener("click", (e) => {
    if (
      !e.target.closest(".modal__content-inner") &&
      e.target.id !== "openBtn"
    ) {
      modal.style.display = "none";
    }
  });

  // サーバーからデータを取得する関数（先ほどと同じものを使用）
  function fetchTheDayData(year, month, day) {
    return fetch(`/api/v1/data/${year}/${month}/${day}`) // サーバーのAPIエンドポイントに合わせて変更
      .then((response) => response.json());
  }

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
