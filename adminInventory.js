document.addEventListener('DOMContentLoaded', function () {
    const deleteButtons = document.querySelectorAll('.delete-btn');
    const deleteModal = document.getElementById('deleteModal');
    const deleteForm = document.getElementById('deleteForm');
    const deleteItemIdInput = document.getElementById('deleteItemId');
    const deleteItemDetailsInput = document.getElementById('deleteItemDetails');
    const deleteCancelButton = deleteModal.querySelector('.btn-secondary');

    const storedMessage = localStorage.getItem('successMessage');
    if (storedMessage) {
        showAlert(storedMessage);
        localStorage.removeItem('successMessage');
    }

    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            const itemId = this.getAttribute('data-item-id');
            const itemDetails = this.closest('.card-body').querySelector('.card-title').innerText;

            deleteItemIdInput.value = itemId;
            deleteItemDetailsInput.value = itemDetails;
            deleteModal.style.display = 'block';
        });
    });

    deleteForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(deleteForm);
        fetch('/adminInventory/delete', {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
            },
            body: formData
        }).then(response => {
            if (response.ok) {
                const itemId = deleteItemIdInput.value;
                const itemCard = document.querySelector(`[data-item-id="${itemId}"]`);
                if (itemCard) {
                    itemCard.remove();
                }
                localStorage.setItem('successMessage', `Item "${deleteItemDetailsInput.value}" has been removed from inventory.`);
                location.reload();
            } else {
                showAlert('Delete failed. Please try again.', 'danger');
            }
        }).catch(error => {
            console.error('Error:', error);
            showAlert('Delete failed. Please try again.', 'danger');
        });
    });

    function showAlert(message, type = 'success') {
        const alertContainer = document.getElementById('alertContainer');
        const alertMessage = document.getElementById('alertMessage');

        alertMessage.textContent = message;
        alertContainer.classList.remove('alert-success', 'alert-danger');
        alertContainer.classList.add(`alert-${type}`);
        alertContainer.style.display = 'block';

        setTimeout(() => {
            alertContainer.style.display = 'none';
        }, 3000);
    }

    deleteCancelButton.addEventListener('click', function () {
        deleteModal.style.display = 'none';
    });

    const approveButtons = document.querySelectorAll('.approve-btn');
    const approveModal = document.getElementById('approveModal');
    const approveForm = document.getElementById('approveForm');
    const approveItemIdInput = document.getElementById('approveItemId');
    const approveItemDetailsInput = document.getElementById('approveItemDetails');
    const approveCancelButton = approveModal.querySelector('.btn-secondary');

    approveButtons.forEach(button => {
        button.addEventListener('click', function () {
            const itemId = this.getAttribute('data-item-id');
            const itemDetails = this.closest('.card-body').querySelector('.card-title').innerText;

            approveItemIdInput.value = itemId;
            approveItemDetailsInput.value = itemDetails;
            approveModal.style.display = 'block';
        });
    });

    approveForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(approveForm);

        fetch('/adminInventory/approve', {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
            },
            body: formData
        }).then(response => {
            if (response.ok) {
                localStorage.setItem('successMessage', 'Approve successful.');
                location.reload();
            } else {
                response.text().then(errorMessage => {
                    showAlert(errorMessage, 'danger');
                });
            }
        }).catch(error => {
            console.error('Error:', error);
            showAlert('Approve failed', 'danger');
        });
    });

    approveCancelButton.addEventListener('click', function () {
        approveModal.style.display = 'none';
    });

    const editButtons = document.querySelectorAll('.edit-btn');
    const editModal = document.getElementById('editModal');
    const editForm = document.getElementById('editForm');
    const editItemIdInput = document.getElementById('editItemId');
    const editItemStockInput = document.getElementById('editItemStock');
    const editItemLocationInput = document.getElementById('editItemLocation');
    const editItemStatusSelect = document.getElementById('editItemStatus');
    const editCancelButton = editModal.querySelector('.btn-secondary');

    let originalStock, originalLocation, originalStatus;

    editButtons.forEach(button => {
        button.addEventListener('click', function () {
            const itemId = this.getAttribute('data-item-id');
            const cardBody = this.closest('.card-body');

            originalStock = cardBody.querySelector('span:nth-of-type(3)').innerText;
            originalLocation = cardBody.querySelector('span:nth-of-type(4)').innerText;
            originalStatus = cardBody.querySelector('span:nth-of-type(2)').innerText.toLowerCase();

            editItemIdInput.value = itemId;
            editItemStockInput.value = originalStock;
            editItemLocationInput.value = originalLocation;
            editItemStatusSelect.value = originalStatus;

            editModal.style.display = 'block';
        });
    });

    editForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const currentStock = editItemStockInput.value;
        const currentLocation = editItemLocationInput.value;
        const currentStatus = editItemStatusSelect.value.toLowerCase();

        if (currentStock !== originalStock || currentLocation !== originalLocation || currentStatus !== originalStatus) {
            const formData = new FormData(editForm);
            fetch('/adminInventory/edit', {
                method: 'POST',
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
                },
                body: formData
            }).then(response => {
                if (response.ok) {
                    localStorage.setItem('successMessage', 'Item changes have been saved successfully.');
                    location.reload();
                } else {
                    showAlert('Edit failed. Please try again.', 'danger');
                }
            }).catch(error => {
                console.error('Error:', error);
                showAlert('Edit failed. Please try again.', 'danger');
            });
        } else {
            showAlert('No changes have been made.', 'warning');
            editModal.style.display = 'none';
        }
    });

    editCancelButton.addEventListener('click', function () {
        editModal.style.display = 'none';
    });

    const addItemButton = document.getElementById('addItemButton');
    const addItemModal = document.getElementById('addItemModal');
    const addItemForm = document.getElementById('addItemForm');
    const addItemCancelButton = addItemModal.querySelector('.btn-secondary');
    const acquisitionDateInput = document.getElementById('acquisitionDate');

    addItemButton.addEventListener('click', function () {
        const today = new Date().toISOString().split('T')[0];
        acquisitionDateInput.value = today;
        addItemModal.style.display = 'block';
    });

    addItemCancelButton.addEventListener('click', function () {
        addItemModal.style.display = 'none';
    });

    window.addEventListener('click', function (event) {
        if (event.target === addItemModal) {
            addItemModal.style.display = 'none';
        }
    });

    addItemForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(addItemForm);

        fetch('/adminInventory/add', {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
            },
            body: formData
        }).then(response => {
            if (response.ok) {
                localStorage.setItem('successMessage', 'Item added successfully.');
                location.reload();
            } else {
                response.text().then(errorMessage => {
                    showAlert(errorMessage, 'danger');
                });
            }
        }).catch(error => {
            console.error('Error:', error);
            showAlert('Add item failed. Please try again.', 'danger');
        });
    });
});