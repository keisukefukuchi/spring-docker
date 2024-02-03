document.addEventListener("DOMContentLoaded", function () {
  let calendarEl = document.getElementById("calendar");
  let modal = document.getElementById("modal");
  let dateInput = document.getElementById("date");
  const closeBtn = document.getElementById("closeBtn");
  let incomeTable = document.getElementById("income_content");
  let expenseTable = document.getElementById("expense_content");

  let calendar = new FullCalendar.Calendar(calendarEl, {
    locale: "ja",
    height: "auto",
    headerToolbar: {
      start: "prev",
      center: "title",
      end: "next",
    },
    events: {
      url: "/api/v1/all",
    },
    eventDidMount: function (info) {
      if (info.event.id === "sum-expense") {
        info.el.querySelector(".fc-event-title").style.fontWeight = "bold";
        info.el.style.backgroundColor = "#fff";
        info.el.querySelector(".fc-event-title").style.color = "red";
      }

      if (info.event.id === "sum-income") {
        info.el.querySelector(".fc-event-title").style.fontWeight = "bold";
        info.el.style.backgroundColor = "#fff";
        info.el.querySelector(".fc-event-title").style.color = "green";
      }

      if (info.event.start.toDateString() === new Date().toDateString()) {
        info.el.style.backgroundColor = "rgba(255, 220, 40, 0.01)";
        info.el.style.borderColor = "rgba(255, 220, 40, 0.01)";
      }
    },
    initialView: "dayGridMonth",
    dateClick: function (info) {
      modal.style.display = "flex";
      dateInput.textContent = info.dateStr;

      let [year, month, day] = info.dateStr.split("-");

      let incomeSecondElement =
        incomeTable.querySelectorAll("tr:nth-child(n+2)");
      if (incomeSecondElement) {
        incomeSecondElement.forEach((element) => {
          incomeTable.removeChild(element);
        });
      }

      let expenseSecondElement =
        expenseTable.querySelectorAll("tr:nth-child(n+2)");
      if (expenseSecondElement) {
        expenseSecondElement.forEach((element) => {
          expenseTable.removeChild(element);
        });
      }

      fetchTheDayData(parseInt(year), parseInt(month), parseInt(day))
        .then((data) => {
          data.forEach((entry) => {
            if (entry.type === "income") {
              let incomeElement = document.createElement("tr");
              if(entry.noMessage) {
                let noElement = document.createElement("td");
                noElement.setAttribute("colspan", "2");
                noElement.style.paddingTop = "30px";
                noElement.textContent = entry.noMessage;
                incomeElement.appendChild(noElement);
              }else {
                let nameElement = document.createElement("td");
                let priceElement = document.createElement("td");

                incomeElement.classList.add("income_box");
                nameElement.classList.add("income-name");
                priceElement.classList.add("income-price");

                nameElement.textContent = entry.theDayIncomeName;
                priceElement.textContent = entry.theDayIncomePrice + "円";

                incomeElement.appendChild(nameElement);
                incomeElement.appendChild(priceElement);
              }

              incomeTable.appendChild(incomeElement);
            } else if (entry.type === "expense") {
              let expenseElement = document.createElement("tr");
              if(entry.noMessage) {
                let noElement = document.createElement("td");
                noElement.setAttribute("colspan", "2");
                noElement.style.paddingTop = "30px";
                noElement.textContent = entry.noMessage;
                expenseElement.appendChild(noElement);
              }else {
                 let nameElement = document.createElement("td");
                 let priceElement = document.createElement("td");
                 let paymentTypeElement = document.createElement("td");
                 let categoryElement = document.createElement("td");

                 expenseElement.classList.add("expense_box");
                 nameElement.classList.add("expense-name");
                 priceElement.classList.add("expense-price");
                 paymentTypeElement.classList.add("expense-payment-type");
                 categoryElement.classList.add("expense-category");

                 nameElement.textContent = entry.theDayExpenseName;
                 priceElement.textContent = entry.theDayExpensePrice + "円";
                 paymentTypeElement.textContent = entry.theDayExpensePaymentType;
                 categoryElement.textContent = entry.theDayExpenseCategory;

                 expenseElement.appendChild(nameElement);
                 expenseElement.appendChild(priceElement);
                 expenseElement.appendChild(paymentTypeElement);
                 expenseElement.appendChild(categoryElement);
              }
              expenseTable.appendChild(expenseElement);
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

  function fetchTheDayData(year, month, day) {
    return fetch(`/api/v1/data/${year}/${month}/${day}`).then((response) =>
      response.json()
    );
  }

  document
    .getElementsByClassName("fc-prev-button")[0]
    .addEventListener("click", function () {
      fetchDataAndUpdate();
    });

  document
    .getElementsByClassName("fc-next-button")[0]
    .addEventListener("click", function () {
      fetchDataAndUpdate();
    });

  window.addEventListener("load", function () {
    if (window.location.pathname === "/") {
      fetchDataAndUpdate();
    }
  });

  function fetchDataAndUpdate() {
    let calendarTitle = calendar.view.title;

    let [year, month] = calendarTitle.split("年");

    fetchDataFromServer(parseInt(year), parseInt(month))
      .then((data) => {
        updateAmounts(data.totalExpense, data.totalIncome);
      })
      .catch((error) => {
        console.error("データの取得に失敗しました:", error);
      });
  }

  function fetchDataFromServer(year, month) {
    return fetch(`/api/v1/data/${year}/${month}`).then((response) =>
      response.json()
    );
  }

  function updateAmounts(expenseAmount, incomeAmount) {
    document.getElementById("expenseAmount").textContent = expenseAmount + "円";
    document.getElementById("incomeAmount").textContent = incomeAmount + "円";
  }
});
