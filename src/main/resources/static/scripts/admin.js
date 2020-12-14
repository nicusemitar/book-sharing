$(document).ready(() => {
    getReviews();
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
                `<li class="page-item" id="page${i}" value="${i}"><a class="page-link" onclick="goToPage(${i})">${i}</a></li>`;
        } else if (i === currentPage) {
            placeholder +=
                `<li class="page-item active" id="page${i}" value="${i}"><a class="page-link" onclick="goToPage(${i})">${i}</a></li>`;
        } else if (i > currentPage) {
            placeholder +=
                `<li class="page-item" id="page${i}" value="${i}"><a class="page-link" onclick="goToPage(${i})">${i}</a></li>`;
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