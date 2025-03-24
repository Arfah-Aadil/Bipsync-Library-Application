document.addEventListener('DOMContentLoaded', function () {
    const checkoutButtons = document.querySelectorAll('.checkout-btn');
    const checkoutModal = document.getElementById('checkoutModal');
    const modalBody = checkoutModal.querySelector('.modal-body');
    const checkoutForm = document.getElementById('checkoutForm');
    const itemIdInput = document.getElementById('itemId');
    const itemDetailsInput = document.getElementById('itemDetails');
    const stockInfoPre = document.getElementById('stockInfoPre');
    const cancelButton = checkoutModal.querySelector('.btn-secondary');
    const dueDateInput = document.createElement('input');
    dueDateInput.type = 'text';
    dueDateInput.id = 'dueDate';
    dueDateInput.name = 'dueDate';
    dueDateInput.className = 'form-control plain-text';
    dueDateInput.readOnly = true;

    const dueDateLabel = document.createElement('label');
    dueDateLabel.htmlFor = 'dueDate';
    dueDateLabel.className = 'form-label';
    dueDateLabel.textContent = 'Due Date';

    const dueDateContainer = document.createElement('div');
    dueDateContainer.className = 'mb-3';
    dueDateContainer.appendChild(dueDateLabel);
    dueDateContainer.appendChild(dueDateInput);

    checkoutForm.insertBefore(dueDateContainer, checkoutForm.querySelector('.button-container'));

    checkoutButtons.forEach(button => {
        button.addEventListener('click', function () {
            const itemId = this.getAttribute('data-item-id');
            const itemDetails = this.closest('.card-body').querySelector('.card-title').innerText;
            const stockElements = this.closest('.card-body').querySelectorAll('.card-text span');

            let stockInfo = '';
            stockElements.forEach(el => {
                let text = el.textContent;
                const label = el.previousSibling.textContent.trim().replace(':', '');

                stockInfo += `${label}: ${text}\n`;
            });

            itemIdInput.value = itemId;
            itemDetailsInput.value = itemDetails;
            stockInfoPre.textContent = stockInfo.trim();

            const currentDate = new Date();
            const dueDate = new Date(currentDate.setDate(currentDate.getDate() + 28));
            dueDateInput.value = dueDate.toISOString().split('T')[0]; // YYYY-MM-DD

            checkoutModal.classList.add('show');
        });
    });

    checkoutForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(checkoutForm);
        const itemDetails = itemDetailsInput.value;

        fetch('/checkout', {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
            },
            body: formData
        }).then(response => {
            if (response.ok) {
                localStorage.setItem('successMessage', `Item '${itemDetails}' has been checked out.`);
                location.reload();
            } else {
                showAlert('Checkout failed', 'danger');
            }
        }).catch(error => {
            console.error('Error:', error);
            showAlert('Checkout failed', 'danger');
        });
    });
    cancelButton.addEventListener('click', function () {
        checkoutModal.classList.remove('show');
    });

    const storedMessage = localStorage.getItem('successMessage');
    if (storedMessage) {
        showAlert(storedMessage);
        localStorage.removeItem('successMessage');
    }

    function showAlert(message, type = 'success') {
        const alertContainer = document.getElementById('alertContainer');
        const alertMessage = document.getElementById('alertMessage');

        alertMessage.textContent = message;
        alertContainer.classList.remove('alert-success', 'alert-danger');
        alertContainer.classList.add(`alert-${type}`);
        alertContainer.style.display = 'block';

        setTimeout(() => {
            alertContainer.style.display = 'none';
        }, 10000);
    }
});