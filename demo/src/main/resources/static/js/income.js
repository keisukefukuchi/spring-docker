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

let editButtons = document.getElementsByClassName("edit-income");

for (let i = 0; i < editButtons.length; i++) {
  editButtons[i].addEventListener("click", function () {
    let customData = this.getAttribute("data-custom-data");
    fetchEditIncome(customData)
      .then((data) => {
        createEditIncomeForm(data.incomeId, data.date, data.name, data.price);
      })
      .catch((error) => {
        console.error("データの取得に失敗しました:", error);
      });
  });
}

function fetchEditIncome(incomeId) {
  return fetch(`/api/v1/edit/income/${incomeId}`).then((response) =>
    response.json()
  );
}

function createEditIncomeForm(incomeId, date, name, price) {
  let container = document.getElementsByClassName("container")[0];
  let modalContainer = document.createElement("div");
  modalContainer.id = "edit-modal";
  modalContainer.classList.add("edit-modal");
  let content = document.createElement("div");
  content.classList.add("modal__content");
  let contentInner = document.createElement("div");
  contentInner.classList.add("modal__content-inner");

  let h2Element = document.createElement("h2");
  h2Element.textContent = "収入";

  let spanElement = document.createElement("span");
  spanElement.id = "edit-closeBtn";
  spanElement.className = "edit-closeBtn edit-batsu";

  contentInner.appendChild(h2Element);
  contentInner.appendChild(spanElement);

  let form = document.createElement("form");
  form.classList.add("income-form");
  let baseUrl = "/edit/income/";
  form.action = baseUrl + incomeId;
  form.method = "post";

  createInputField("date", "editIncomeDate", "登録日", date, true, form);
  createInputField("text", "editName", "収入名", name, true, form);
  createInputField("number", "editIncomeAmount", "金額", price, true, form);

  let buttonContainer = document.createElement("div");
  buttonContainer.classList.add("button-position");

  let submitButton = document.createElement("button");
  submitButton.type = "submit";
  submitButton.textContent = "編集";

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
    if (editModal) {
      editModal.style.display = "block";
    }
  });

  editCloseBtn.addEventListener("click", () => {
    let parentElement = editModal.parentElement;
    if (parentElement) {
      parentElement.removeChild(editModal);
    }
  });

  window.addEventListener("click", (e) => {
    if (
      !e.target.closest(".modal__content-inner") &&
      e.target.id !== "edit-openBtn"
    ) {
      let parentElement = editModal.parentElement;
      if (parentElement) {
        parentElement.removeChild(editModal);
      }
    }
  });
}

function createInputField(type, id, name, value, required, form) {
  let label = document.createElement("label");
  label.htmlFor = id;
  label.textContent = name.charAt(0).toUpperCase() + name.slice(1);

  let input = document.createElement("input");
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
}

let deleteCloseBtn = document.getElementById("delete-closeBtn");
let deleteModal = document.getElementById("delete-modal");

// 各ボタンに対してイベントリスナーを追加
document.querySelectorAll(".delete-openBtn").forEach((btn) => {
  btn.addEventListener("click", function () {
    // クリックされたボタンに対する処理を行う
    let deleteData = this.getAttribute("delete-data");
    let buttonFlex = document.getElementsByClassName("button-flex")[0];

    let childElements = buttonFlex.children;

    // 子要素の数だけループ
    for (let i = 0; i < childElements.length; i++) {
      let childElement = childElements[i];
      // 子要素が form タグであれば削除
      if (childElement.tagName.toLowerCase() === "form") {
        buttonFlex.removeChild(childElement);
        break; // 1回削除すれば十分な場合はループを抜ける
      }
    }

    let form = document.createElement("form");
    form.classList.add("income-form");
    let baseUrl = "/delete/income/";
    form.action = baseUrl + deleteData;
    form.method = "post";

    let confirmDeleteBtn = document.createElement("button");
    confirmDeleteBtn.setAttribute("type", "submit");
    confirmDeleteBtn.setAttribute("class", "btn btn-danger");
    confirmDeleteBtn.setAttribute("id", "confirmDeleteBtn");
    confirmDeleteBtn.textContent = "はい";

    form.appendChild(confirmDeleteBtn);
    buttonFlex.appendChild(form);
    deleteModal.style.display = "block";
  });
});

deleteCloseBtn.addEventListener("click", () => {
  deleteModal.style.display = "none";
});
