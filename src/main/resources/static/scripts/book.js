$(document).ready(() => {
    let id = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
    getBook(id);
    getReviews(id);
});

let tags = [];
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
        let authorFullName = `${book.data.author}`
        let placeholderAuthor = `<th>Author:</th><td>${book.data.author}</td>`;
        let authorFirstName = authorFullName.substr(0, authorFullName.indexOf(' '));
        let authorLastName = authorFullName.substr(authorFullName.indexOf(' ') + 1);
        let placeholderLanguage = `<th>Language:</th><td>${book.data.language}</td>`;
        let placeholderNrPages= `<th>Number of Pages:</th><td>${book.data.pages}</td>`;
        let placeholderStatus=`<th>Status:</th><td>${book.data.status}</td>`
        let placeholderDescription = `<th>Description:</th><td>${book.data.description}</td>`;
        let placeholderAddedBy = `<th>Added by:</th><td>${book.data.addedBy}</td>`;
        let placeholderAddedAt = `<th>Added at:</th><td>${book.data.addedAt}</td>`;
        let assignButton = '<th><button id = "assign-button" class="btn btn-primary btn-lg">Assign to me</button></th>';
        let waitingAssignButton = '<th><button id = "assign-button" class="btn btn-primary btn-lg">Get in line</button></th>';
        $("#title").html(placeholderTitle);
        $("#book-author").html(placeholderAuthor);
        $("#book-language").html(placeholderLanguage);
        $("#book-pages").html(placeholderNrPages);
        $("#book-description").html(placeholderDescription);
        $("#book-status").html(placeholderStatus);
        $("#book-added-by").html(placeholderAddedBy);
        $("#book-added-at").html(placeholderAddedAt);

        if(book.data.status === "FREE"){
            $("#assignButton").html(assignButton);
        } else {
            $("#assignButton").html(waitingAssignButton);
        }
        $("#txt-title").attr("value", placeholderTitle);
        $("#txt-description").val(`${book.data.description}`);
        $("#txt-first-name").attr("value", authorFirstName);
        $("#txt-last-name").attr("value", authorLastName);
        $("#txt-language").attr("value", `${book.data.language}`);
        $("#txt-pages").attr("value", `${book.data.pages}`);
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
};

let titleValidate = true;
let lastNameValidate = true;
let firstNameValidate = true;
let pagesValidate = true;
let languageValidate = true;
let descriptionValidate = false;
let goodColor = "#66cc66";
let badColor = "#ff6666";

$("#submit-delete").on("click", () => {
    if (descriptionValidate === true) {
        $.ajax({
            url: `/books`,
            type: `DELETE`,
            data: JSON.stringify(deleteBookObj()),
            contentType: "application/json",
            success: function (result) {
                window.location.href = '/all-books';
            },
            error: function (result) {
                window.location.href = '/error-page';
            }
        });
    } else {
        document.getElementById("delete-modal").style.display = "block"
    }
});

const deleteBookObj = () => {
    return {
        bookId: window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
        description: $("#txt-delete-description").val()
    }
}

function validateTitle(txt) {
    let regAdd = /^[A-z0-9]([- ',.A-z0-9]{0,40}[A-z0-9])$/
    if (regAdd.test(txt) === false) {
        document.getElementById("errLast-title").innerHTML = "<span class='warning'>Invalid Book Title!</span>";
        document.getElementById("errLast-title").style.color = badColor;
        document.getElementById("txt-title").style.border = "medium solid #ff6666"
        titleValidate = false;
    } else {
        document.getElementById("errLast-title").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errLast-title").style.color = goodColor;
        document.getElementById("txt-title").style.border = "medium solid #66cc66"
        titleValidate = true;
    }
}

function validateFirstName(txt) {
    let regAdd = /^[A-z]([- ',.A-z]{0,23}[A-z])$/
    if (regAdd.test(txt) === false) {
        document.getElementById("errLast-first-name").innerHTML = "<span class='warning'>Please use legal characters!</span>";
        document.getElementById("errLast-first-name").style.color = badColor;
        document.getElementById("txt-first-name").style.border = "medium solid #ff6666"
        firstNameValidate = false;
    } else {
        document.getElementById("errLast-first-name").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errLast-first-name").style.color = goodColor;
        document.getElementById("txt-first-name").style.border = "medium solid #66cc66"
        firstNameValidate = true;
    }
}

function validateLastName(txt) {
    let regAdd = /^[A-z]([- ',.A-z]{0,23}[A-z])$/
    if (regAdd.test(txt) === false) {
        document.getElementById("errLast-last-name").innerHTML = "<span class='warning'>Please use legal characters!</span>";
        document.getElementById("errLast-last-name").style.color = badColor;
        document.getElementById("txt-last-name").style.border = "medium solid #ff6666"
        lastNameValidate = false;
    } else {
        document.getElementById("errLast-last-name").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errLast-last-name").style.color = goodColor;
        document.getElementById("txt-last-name").style.border = "medium solid #66cc66"
        lastNameValidate = true;
    }
}

function validatePages(txt) {
    if (txt < 1 || txt > 5000) {
        document.getElementById("errLast-pages").innerHTML = "<span class='warning'>Between 1 and 5000!</span>";
        document.getElementById("errLast-pages").style.color = badColor;
        document.getElementById("txt-pages").style.border = "medium solid #ff6666"
        pagesValidate = false;
    } else {
        document.getElementById("errLast-pages").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errLast-pages").style.color = goodColor;
        document.getElementById("txt-pages").style.border = "medium solid #66cc66"
        pagesValidate = true;
    }
}

function validateLanguage(txt) {
    let regAdd = /^[a-zA-Z]{1,30}$/
    if (regAdd.test(txt) === false) {
        document.getElementById("errLast-language").innerHTML = "<span class='warning'>Please use only letters!</span>";
        document.getElementById("errLast-language").style.color = badColor;
        document.getElementById("txt-language").style.border = "medium solid #ff6666"
        languageValidate = false;
    } else {
        document.getElementById("errLast-language").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errLast-language").style.color = goodColor;
        document.getElementById("txt-language").style.border = "medium solid #66cc66"
        languageValidate = true;
    }
}

function validateDescription(txt) {
    if (txt.trim().length < 1) {
        document.getElementById("errLast-description").innerHTML = "<span class='warning'>Please provide a reason</span>";
        document.getElementById("errLast-description").style.color = badColor;
        document.getElementById("txt-delete-description").style.border = "medium solid #ff6666"
        descriptionValidate = false;
    } else {
        document.getElementById("errLast-description").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errLast-description").style.color = goodColor;
        document.getElementById("txt-delete-description").style.border = "medium solid #66cc66"
        descriptionValidate = true;
    }
}



const validInput = () => {
    if (titleValidate === true || lastNameValidate === true || firstNameValidate === true || pagesValidate === true
        || languageValidate === true || descriptionValidate === true) {
        return true;
    } else {
        return false;
    }
}

$("#submit-update").on("click", () => {
    let id = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
    if (validInput() === true) {
        $.ajax({
            url: `/books/update/${id}`,
            method: "POST",
            data: JSON.stringify(bookObject()),
            contentType: "application/json",
            success: function (result) {
                window.location.href = `/book/${id}`;
            },
            error: function (result) {
                document.getElementById("update-modal").style.display = "block"
            }
        });
    } else {
        document.getElementById("update-modal").style.display = "block"
    }
});

const bookObject = () => {
    return {
        title: $("#txt-title").val(),
        authorFirstName: $("#txt-first-name").val(),
        authorLastName: $("#txt-last-name").val(),
        bookLanguage: $("#txt-language").val(),
        pages: $("#txt-pages").val(),
        tagList: [],
        description: $("#txt-description").val()
    };
};

