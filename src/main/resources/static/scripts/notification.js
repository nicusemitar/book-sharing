$(document).ready(() => {
    getAllRequests();
});

var el = document.querySelector('.notification');

function showNotification(count) {
    if (count !== 0) {
        el.setAttribute('data-count', count);
        el.classList.remove('notify');
        el.classList.add('notify');
        el.classList.add('show-count');
    }
}

function hideNotification() {
    el.classList.remove('show-count');
}

$(".notification").on("click", function () {
    hideNotification();
    window.location.href = `/admin-page`;
})

function getAllRequests() {
    $.ajax({
        url: "/assignments/extends",
        method: "GET",
        success: response => {
            showNotification(response.totalItems);
        },
        error: () => {
            alert("Something went wrong")
        }
    })
}