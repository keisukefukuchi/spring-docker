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

let editButtons = document.getElementsByClassName("edit-payment-type");

for (let i = 0; i < editButtons.length; i++) {
  editButtons[i].addEventListener("click", function () {
    let customData = this.getAttribute("data-custom-data");
    fetchEditPaymentType(customData)
      .then((data) => {
        createEditPaymentTypeForm(data.paymentTypeId, data.name);
      })
      .catch((error) => {
        console.error("データの取得に失敗しました:", error);
      });
  });
}

function fetchEditPaymentType(paymentTypeId) {
  return fetch(`/api/v1/edit/payment-type/${paymentTypeId}`).then((response) =>
    response.json()
  );
}

function createEditPaymentTypeForm(paymentTypeId, name) {
  let container = document.getElementsByClassName("container")[0];
  let modalContainer = document.createElement("div");
  modalContainer.id = "edit-modal";
  modalContainer.classList.add("edit-modal");
  let content = document.createElement("div");
  content.classList.add("modal__content");
  let contentInner = document.createElement("div");
  contentInner.classList.add("modal__content-inner");

  let h2Element = document.createElement("h2");
  h2Element.textContent = "支払い系統編集画面";

  let spanElement = document.createElement("span");
  spanElement.id = "edit-closeBtn";
  spanElement.className = "edit-closeBtn edit-batsu";

  contentInner.appendChild(h2Element);
  contentInner.appendChild(spanElement);

  let form = document.createElement("form");
  form.classList.add("payment-type-form");
  let baseUrl = "/edit/payment-type/";
  form.action = baseUrl + paymentTypeId;
  form.method = "post";

  createInputField("text", "editPaymentTypeName", "支払い系統", name, true, form);

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

  form.appendChild(label);
  form.appendChild(input);
}