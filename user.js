// $(document).ready(function() {
//     // Example: Form validation
//     $('#createUserForm').submit(function(event) {
//         const username = $('#username').val();
//         const password = $('#password').val();
//         const email = $('#email').val();
//
//         if (!username || !password || !email) {
//             alert('All fields are required!');
//             event.preventDefault();
//         }
//     });
//
//     // Example: AJAX delete request
//     $('.delete-user').click(function(event) {
//         event.preventDefault();
//         const userId = $(this).data('id');
//         const confirmation = confirm('Are you sure you want to delete this user?');
//         if (confirmation) {
//             $.ajax({
//                 url: '/user/delete/' + userId,
//                 type: 'POST',
//                 success: function(result) {
//                     location.reload();
//                 },
//                 error: function(err) {
//                     alert('Error deleting user');
//                 }
//             });
//         }
//     });
// });
