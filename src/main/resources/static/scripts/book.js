$(document).ready(() => {
    let id = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
    getBook(id);
    getReviews(id);
});

function getBook(id) {
    $.ajax({
        url: `/books/${id}`,
        method: "GET",
        success: response => {
            displayBook(response);
        },
    })
}

function getReviews(id) {
    $.ajax({
        url: `/books/${id}/review`,
        method: "GET",
        success: response => {
            displayReviews(response);
        },
    })
}

function displayBook(book) {
    if (book !== null) {
        let placeholderTitle = `${book.data.title}`;
        let placeholderAuthor = `<th>Author:</th><td>${book.data.author}</td>`;
        let placeholderLanguage = `<th>Language:</th><td>${book.data.language}</td>`;
        let placeholderNrPages= `<th>Number of Pages:</th><td>${book.data.pages}</td>`;
        let placeholderStatus=`<th>Status:</th><td>${book.data.status}</td>`
        let placeholderDescription = `<th>Description:</th><td>${book.data.description}</td>`;
        let placeholderAddedBy = `<th>Added by:</th><td>${book.data.addedBy}</td>`;
        let placeholderAddedAt = `<th>Added at:</th><td>${book.data.addedAt}</td>`;
        $("#title").html(placeholderTitle);
        $("#book-author").html(placeholderAuthor);
        $("#book-language").html(placeholderLanguage);
        $("#book-pages").html(placeholderNrPages);
        $("#book-description").html(placeholderDescription);
        $("#book-status").html(placeholderStatus);
        $("#book-added-by").html(placeholderAddedBy);
        $("#book-added-at").html(placeholderAddedAt);
    }
}

function displayReviews(reviews) {
    if (reviews.data.length > 0) {
        let placeholder = "";
        $.each(reviews.data, (index, review) => {
            placeholder += `
                <div class="card mb-3 w-75">
                  <h5 class="card-header">${review.username}</h5>
                  <div class="card-body">
                    <p class="card-text">${review.textReview}</p>
                  </div>
                </div>
                `;
        });
        $("#no-review").css("display", "none");
        $("#reviews").html(placeholder);
    }
}

function addReview() {
    if ($("#text-review").val().trim().length > 0 && $("#text-review").val().length <= 2048) {
        let id = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
        $.ajax({
            method: "POST",
            url: `/books/${id}/review`,
            data: JSON.stringify(reviewObject()),
            contentType: "application/json",
            success: response => {
                $("#text-review").val("");
                $("#invalid-review").remove();
                displayReview(response);
            },
        });
    } else {
        $("#invalid-review").html("<div class=\"alert alert-danger\" role=\"alert\">\n" +
            "Invalid Review!</div>");
    }
}

function displayReview(review) {
    if (review !== null) {
        let placeholder = `
            <div class="card mb-3 w-75">
                <h5 class="card-header">${review.data.username}</h5>
                <div class="card-body">
                    <p class="card-text">${review.data.textReview}</p>
                </div>
            </div>
            `;
        $("#no-review").css("display", "none");
        $("#reviews").prepend(placeholder);
    }
}

const reviewObject = () => {
    return {
        textReview: $("#text-review").val(),
    };
}