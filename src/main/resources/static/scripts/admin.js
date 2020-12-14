$(document).ready(() => {
    getReviews();
    getAllRequests();
});

function getReviews() {
    $.ajax({
        url: `/review`,
        method: "GET",
        success: response => {
            displayReviews(response.reviews, response.currentPage);
            displayPages(response.totalPages, response.currentPage + 1);
        }
    })
}

function displayReviews(reviews, currentPage) {
    let placeholder = "";
    let elementNr = currentPage * 8;
    $.each(reviews, (index, review) => {
        elementNr++;
        placeholder +=
            `<tr>
                <input class="review-id" type="hidden" value='${review.id}'>
                <th scope="row">${elementNr}</th>
                <td>${review.username}</td>
                <td>${review.textReview}</td>
                <td><button class="delete-button btn btn-primary" data-toggle="modal" data-target="#deleteModal">
                Delete</button></td>    
            </tr>`
    });
    $("#reviews-placeholder tbody").html(placeholder);

    $("#reviews-placeholder").on('click', ".delete-button", function () {
        styleOpenModal();
        let currentReviewId = this.parentNode.parentElement.querySelector(".review-id").value;
        $("#review-id-delete").val(currentReviewId);
    });
}

function styleOpenModal() {
    $("#messageError").css("display", "none");
    $("#messageSuccess").css("display", "none");
    $("#messageDefault").css("display", "block");
    $("#confirm-delete").css("display", "block");
}

$("#confirm-delete").on('click', function () {
    let id = $("#review-id-delete").val();
    $.ajax({
        method: "DELETE",
        url: `/review/${id}`,
        success: () => {
            styleSuccessModal();
            getReviews();
        },
        error: () => {
            styleErrorModal();
        }
    });
    $("#review-id-delete").val("");
});

function styleSuccessModal() {
    $("#messageDefault").css("display", "none");
    $("#confirm-delete").css("display", "none");
    $("#messageError").css("display", "none");
    $("#messageSuccess").css("display", "block");
}

function styleErrorModal() {
    $("#messageDefault").css("display", "none");
    $("#confirm-delete").css("display", "none");
    $("#messageSuccess").css("display", "none");
    $("#messageError").css("display", "block");
}

$("#cancel-delete").on('click', function () {
    $("#review-id-delete").html("");
});

function displayPages(totalPages, currentPage) {
    let placeholder = "";
    for (let i = 1; i <= totalPages; i++) {
        if (i < currentPage) {
            placeholder +=
                `<li class="page-item" id="page${i}" value="${i}"><button class="page-link" href="" onclick="goToPage(${i})">${i}</button></li>`;
        } else if (i === currentPage) {
            placeholder +=
                `<li class="page-item active" id="page${i}" value="${i}"><button class="page-link" href="" onclick="goToPage(${i})">${i}</button></li>`;
        } else if (i > currentPage) {
            placeholder +=
                `<li class="page-item" id="page${i}" value="${i}"><button class="page-link" href="" onclick="goToPage(${i})">${i}</button></li>`;
        }
    }
    $("#pagination-placeholder ul").html(placeholder);
    limitNumberOfShownPages(totalPages, currentPage);
}

function goToPage(i) {
    $.ajax({
        url: "/review",
        method: "GET",
        data: {page: i - 1},
        success: response => {
            displayReviews(response.reviews, response.currentPage);
            displayPages(response.totalPages, response.currentPage + 1);
        }
    })
}

function limitNumberOfShownPages(totalPages, currentPage) {
    let n = currentPage - 2;
    let m = currentPage + 2;
    for (let i = 0; i < (n - 1); i++) {
        $(".page-item").filter(function (index) {
            return index === i
        }).hide();
    }
    for (let j = m; j <= totalPages; j++) {
        $(".page-item").filter(function (index) {
            return index === j
        }).hide();
    }
}


let requestList;

function getAllRequests() {
    $.ajax({
        url: "/assignments/extends",
        method: "GET",
        success: response => {
            requestList = response.timeExtendResponseDtoList;
            displayRequests(response.timeExtendResponseDtoList, response.currentPage);
            displayPagesRequest(response.totalPages, response.currentPage + 1);
        },
        error: () => {
            alert("Something went wrong")
        }
    })
}

function displayRequests(requests, currentPage) {
    if (requests.length > 0) {
        let placeholder = "";
        let elementNr = currentPage * 8;

        $.each(requests, (index, requests) => {
            placeholder +=
                `<tr>
                    <th scope="row">${index + 1}</th>
                    <td>${requests.username}</td>
                    <td>${requests.bookId}</td>
                    <td class="description">${requests.description}</td>
                    <td>${requests.dueDate}</td>
                    <td class="requestedDate">${requests.requestedDate}</td>
                    <td><button class="button-option update" id="update" type="button" value="${requests.requestId}" data-toggle="modal" data-target="#exampleModal">APPROVE</button></td>
                    <td><button class="button-option delete" id="delete" type="button" value="${requests.requestId}" data-toggle="modal" data-target="#exampleModal">DECLINE</button></td>
                 </tr>`
        });
        $("#request-placeholder tbody").html(placeholder);
    }

    $(".delete").on("click", function () {
        let id = this.value;
        $("#modalForSubmitAndError").css("display", "block");
        $("#modalForAccept").css("display", "none");
        $("#submit").unbind('click').on("click", function () {
            $.ajax({
                url: `/assignments/extends/${id}`,
                method: "DELETE",
                success: response => {
                    window.location.href = `/admin-page`;
                },
                error: () => {
                    alert("Something went wrong")
                }
            })
        });
    });

    $(".update").on("click", function () {
        let id = parseInt(this.value, 10);
        let request;
        request = requestList.filter(function (e) {
            return e.requestId === id;
        });

        const requestObject = () => {
            return {
                description: request[0].description,
                requestedDate: request[0].requestedDate
            };
        };

        $("#modalForAccept").css("display", "block");
        $("#modalForSubmitAndError").css("display", "none");
        $("#submit").unbind('click').on("click", function () {
            $.ajax({
                url: `/assignments/extends/${id}`,
                method: "POST",
                data: JSON.stringify(requestObject()),
                contentType: "application/json",
                success: response => {
                    window.location.href = `/admin-page`;
                },
                error: () => {
                    alert("Something went wrong!")
                }
            })
        });
    });
}

function displayPagesRequest(totalPages, currentPage) {
    let placeholder = "";
    for (let i = 1; i <= totalPages; i++) {
        if (i < currentPage) {
            placeholder +=
                `<li class="page-item" id="page${i}" value="${i}"><button class="page-link" onclick="goToPageRequest(${i})">${i}</button></li>`;
        } else if (i === currentPage) {
            placeholder +=
                `<li class="page-item active" id="page${i}" value="${i}"><button class="page-link" onclick="goToPageRequest(${i})">${i}</button></li>`;
        } else if (i > currentPage) {
            placeholder +=
                `<li class="page-item" id="page${i}" value="${i}"><button class="page-link" onclick="goToPageRequest(${i})">${i}</button></li>`;
        }
    }
    $("#pagination-placeholder-request ul").html(placeholder);
    limitNumberOfShownPages(totalPages, currentPage);
}


function goToPageRequest(i) {
    $.ajax({
        url: "/assignments/extends",
        method: "GET",
        data: {page: i - 1},
        success: response => {
            displayRequests(response.timeExtendResponseDtoList, response.currentPage);
            displayPagesRequest(response.totalPages, response.currentPage + 1);
        }
    })
}
