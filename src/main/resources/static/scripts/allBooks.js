$(document).ready(() => {
    getAllBooks();
});

function getAllBooks() {
    $.ajax({
        url: "/books",
        method: "GET",
        success: response => {
            displayBooks(response.books);
            displayPages(response.totalPages, response.currentPage + 1);
        },
        error: () => {
            alert("Something went wrong")
        }
    })
}

function displayBooks(books) {
    let placeholder = "";
    $.each(books, (index, book) => {
        if (book.status === "FREE") {
            st = '<img src = "/images/free.png" class = "img-for-status" width="50px" height="50px">';
        } else {
            st = '<img src = "/images/busy.png" class = "img-for-status" width="50px" height="50px">';
        }
        placeholder +=
            `<span class="shadow float-left p-4 mb-4 bg-white" style="height: 320px">
                <input class='user-id' type='hidden' value='${book.id}'>
                ${st}
                <img src="/images/book-image.jpg" style="width: 100%; padding: 10%" align="center">
                <p align="center">
                    ${book.title}<br>
                    ${book.authorName}<br>
                    Language: ${book.language}
                </p>
            </span>`
    });
    $("#books-placeholder").html(placeholder);
}

function displayPages(totalPages, currentPage) {
    let placeholder = "";
    for (let i = 1; i <= totalPages; i++) {
        if (i < currentPage) {
            placeholder +=
                `<li class="page-item" id="page${i}" value="${i}"><button class="page-link" onclick="goToPage(${i})">${i}</button></li>`;
        } else if (i === currentPage) {
            placeholder +=
                `<li class="page-item active" id="page${i}" value="${i}"><button class="page-link" onclick="goToPage(${i})">${i}</button></li>`;
        } else if (i > currentPage) {
            placeholder +=
                `<li class="page-item" id="page${i}" value="${i}"><button class="page-link" onclick="goToPage(${i})">${i}</button></li>`;
        }
    }
    $("#pagination-placeholder ul").html(placeholder);
    limitNumberOfShownPages(totalPages, currentPage);
}

function goToPage(i) {
    $.ajax({
        url: "/books",
        method: "GET",
        data: {page: i - 1},
        success: response => {
            displayBooks(response.books);
            displayPages(response.totalPages, response.currentPage + 1);
        },
        error: () => {
            alert("Something went wrong")
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

$("#search-key").on("keyup", function () {
    let key = $(this).val();
    $.ajax({
        url: "/books",
        method: "GET",
        data: {find: key},
        success: response => {
            displayBooks(response.books);
            displaySearchPages(response.totalPages, response.currentPage + 1);
        },
        error: () => {
            alert("Something went wrong")
        }
    })
})

function displaySearchPages(totalPages, currentPage) {
    let placeholder = "";
    for (let i = 1; i <= totalPages; i++) {
        if (i < currentPage) {
            placeholder +=
                `<li class="page-item" id="page${i}" value="${i}"><button class="page-link" onclick="goToSearchPage(${i})">${i}</button></li>`;
        } else if (i === currentPage) {
            placeholder +=
                `<li class="page-item active" id="page${i}" value="${i}"><button class="page-link" onclick="goToSearchPage(${i})">${i}</button></li>`;
        } else if (i > currentPage) {
            placeholder +=
                `<li class="page-item" id="page${i}" value="${i}"><button class="page-link" onclick="goToSearchPage(${i})">${i}</button></li>`;
        }
    }
    $("#pagination-placeholder ul").html(placeholder);
    limitNumberOfShownPages(totalPages, currentPage);
}

function goToSearchPage(i) {
    let key = $("#search-key").val();
    $.ajax({
        url: "/books",
        method: "GET",
        data: {page: i - 1, find: key},
        success: response => {
            displayBooks(response.books);
            displaySearchPages(response.totalPages, response.currentPage + 1);
        },
        error: () => {
            alert("Something went wrong")
        }
    })
}

$("#books-placeholder").on("click", ".shadow", function () {
    let id = this.querySelector(".user-id").value;
    window.location.href = `/book/${id}`;
})