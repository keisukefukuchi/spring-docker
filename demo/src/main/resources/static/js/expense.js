const openBtn = document.getElementById("openBtn");
const closeBtn = document.getElementById("closeBtn");
const modal = document.getElementById("modal");
openBtn.addEventListener("click", () => {
  modal.style.display = "block";
});
closeBtn.addEventListener("click", () => {
  modal.style.display = "none";
});
window.addEventListener("click", (e) => {
  if (!e.target.closest(".modal__content-inner") && e.target.id !== "openBtn") {
    modal.style.display = "none";
  }
});

let editButtons = document.getElementsByClassName("edit-expense");

for (let i = 0; i < editButtons.length; i++) {
  editButtons[i].addEventListener("click", function () {
    let customData = this.getAttribute("data-custom-data");
    fetchEditExpense(customData)
      .then((data) => {
        let expenseDetails = data.expenseDetails;
        createEditExpenseForm(
          expenseDetails.expenseId,
          expenseDetails.date,
          expenseDetails.name,
          expenseDetails.price,
          expenseDetails.categoryName,
          expenseDetails.paymentTypeName,
          data.categoryList,
          data.paymentTypeList
        );
      })
      .catch((error) => {
        console.error("データの取得に失敗しました:", error);
      });
  });
}

function fetchEditExpense(expenseId) {
  return fetch(`/api/v1/edit/expense/${expenseId}`).then((response) =>
    response.json()
  );
}

function createEditExpenseForm(
  expenseId,
  date,
  name,
  price,
  categoryName,
  paymentTypeName,
  categoryList,
  paymentTypeList
) {
  let container = document.getElementsByClassName("container")[0];
  let modalContainer = document.createElement("div");
  modalContainer.id = "edit-modal";
  modalContainer.classList.add("edit-modal");
  let content = document.createElement("div");
  content.classList.add("modal__content");
  let contentInner = document.createElement("div");
  contentInner.classList.add("modal__content-inner");

  let h2Element = document.createElement("h2");
  h2Element.textContent = "支出";

  let spanElement = document.createElement("span");
  spanElement.id = "edit-closeBtn";
  spanElement.className = "edit-closeBtn edit-batsu";

  contentInner.appendChild(h2Element);
  contentInner.appendChild(spanElement);

  let form = document.createElement("form");
  form.classList.add("expense-form");
  let baseUrl = "/edit/expense/";
  form.action = baseUrl + expenseId;
  form.method = "post";

  createInputField("date", "editExpenseDate", "登録日", date, true, null, form);
  createInputField("text", "editName", "支出名", name, true, null, form);
  createInputField("select", "editExpenseCategoryId", "カテゴリー", categoryName, true, categoryList, form);
  createInputField("select", "editExpensePaymentTypeId", "支払い系統", paymentTypeName, true, paymentTypeList, form);
  createInputField("number", "editExpenseAmount", "金額", price, true, null, form);

  let buttonContainer = document.createElement("div");
  buttonContainer.classList.add("button-position");

  let submitButton = document.createElement("button");
  submitButton.type = "submit";
  submitButton.textContent = "修正";

  buttonContainer.appendChild(submitButton);
  form.appendChild(buttonContainer);

  contentInner.appendChild(form);
  content.appendChild(contentInner);
  modalContainer.appendChild(content);
  container.appendChild(modalContainer);

  let editOpenBtn = document.getElementById("edit-openBtn");
  let editCloseBtn = document.getElementById("edit-closeBtn");
  let editModal = document.getElementById("edit-modal");

  editOpenBtn.addEventListener("click", () => {
    editModal.style.display = "block";
  });

  editCloseBtn.addEventListener("click", () => {
    let parentElement = editModal.parentElement;
    parentElement.removeChild(editModal);
  });

  window.addEventListener("click", (e) => {
    if (
      !e.target.closest(".modal__content-inner") &&
      e.target.id !== "edit-openBtn"
    ) {
      let parentElement = editModal.parentElement;
      parentElement.removeChild(editModal);
    }
  });
}

function createInputField(type, id, name, value, required, list, form) {
  let label = document.createElement("label");
  label.htmlFor = id;
  label.textContent = name.charAt(0).toUpperCase() + name.slice(1);

  let input;
  if (type === "date" || type === "number" || type === "text") {
    input = document.createElement("input");
    input.type = type;
    input.id = id;
    input.value = value;
    input.name = id;
    input.required = required;
    if (type === "number") {
      input.min = 0;
    }
    form.appendChild(label);
    form.appendChild(input);
  } else {
    let container = document.createElement("div");
    container.classList.add("label-container");
    input = document.createElement("select");
    input.id = id;
    input.name = id;
    input.required = required;

    Object.entries(list).forEach(([key, item]) => {
      let optionElement = document.createElement("option");
      optionElement.value = key;
      optionElement.text = item;
      if (item === value) {
        optionElement.selected = true;
      }
      input.appendChild(optionElement);
    });
    container.appendChild(label);
    container.appendChild(input);
    form.appendChild(container);
  }
}
