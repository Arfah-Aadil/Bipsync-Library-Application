document.addEventListener('DOMContentLoaded', function () {
    const checkinButtons = document.querySelectorAll('.checkin-btn');
    const checkinModal = document.getElementById('checkinModal');
    const modalBody = checkinModal.querySelector('.modal-body');
    const checkinForm = document.getElementById('checkinForm');
    const transactionIdInput = document.getElementById('transactionId');
    const checkinItemDetailsInput = document.getElementById('checkinItemDetails');
    const checkinModelInput = document.getElementById('checkinModel');
    const checkinLocationInput = document.getElementById('checkinLocation');
    const cancelButton = checkinModal.querySelector('.btn-secondary');

    checkinButtons.forEach(button => {
        button.addEventListener('click', function () {
            const transactionId = this.getAttribute('data-transaction-id');
            const itemDetails = this.closest('.card-body').querySelector('.item-details').innerText;
            const model = this.closest('.card-body').querySelector('.item-model').innerText;
            const location = this.closest('.card-body').querySelector('.item-location').innerText;

            transactionIdInput.value = transactionId;
            checkinItemDetailsInput.value = itemDetails;
            checkinModelInput.value = model;
            checkinLocationInput.value = location;

            checkinModal.classList.add('show');
        });
    });

    checkinForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(checkinForm);
        const itemDetails = checkinItemDetailsInput.value;

        fetch('/checkin', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                localStorage.setItem('successMessage', `Item '${itemDetails}' has been checked in.`);
                location.reload();
            } else {
                showAlert('Check-in failed', 'danger');
            }
        }).catch(error => {
            console.error('Error:', error);
            showAlert('Check-in failed', 'danger');
        });
    });

    cancelButton.addEventListener('click', function () {
        checkinModal.classList.remove('show');
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