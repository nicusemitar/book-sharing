$(document).ready(() => {
    let id = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
    getBook(id);
    getReviews(id);
});

let tags = [];

function verifyUserAssignmentForThisBook(bookID) {
    $.ajax({
        url: `/assignments/current-user`,
        method: "GET",
        success: response => {
            $.each(response, (index, assignment) => {
                if (assignment.bookId === bookID) {
                    let placeholder = `
                        <span class="d-inline-block" data-toggle="popover" data-trigger="hover" title="Sorry mate!" data-content="You already have an assignment for this book">
                            <button class="btn btn-primary" style="pointer-events: none;" type="button" disabled>Get in line</button>
                        </span>`
                    $("#assignButtons").html(placeholder);
                    $('[data-toggle="popover"]').popover();
                }
            })
            if (response.length >= 3) {
                let placeholder = `
                        <span class="d-inline-block" data-toggle="popover" data-trigger="hover" title="Sorry mate!" data-content="You already have assignments for 3 books">
                            <button class="btn btn-primary" style="pointer-events: none;" type="button" disabled>Get in line</button>
                        </span>`
                $("#assignButtons").html(placeholder);
                $('[data-toggle="popover"]').popover();
            }
        },
    })
}

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
        let placeholderNoLanguage = `<th>Language:</th><td>Language is not indicated</td>`;
        let placeholderNrPages = `<th>Number of Pages:</th><td>${book.data.pages}</td>`;
        let placeholderStatus = `<th>Status:</th><td>${book.data.status}</td>`
        let placeholderDescription = `<th>Description:</th><td>${book.data.description}</td>`;
        let placeholderNoDescription = `<th>Description:</th><td>Description is not indicated</td>`;
        let placeholderTags = `<th>Tags:</th><td>
                                <div class="clearfix" id="tag-div"></div></td>`;
        let placeholderAddedBy = `<th>Added by:</th><td>${book.data.addedBy}</td>`;
        let placeholderNoAddedBy = `<th>Added by:</th><td>User is not indicated</td>`;
        let placeholderAddedAt = `<th>Added at:</th><td>${book.data.addedAt}</td>`;
        let assignButton = '<button id = "assign-button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#assignModal">Assign to me</button>';
        let waitingAssignButton = '<button id = "assign-button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#assignModal">Get in line</button>';
        $("#title").html(placeholderTitle);
        $("#book-author").html(placeholderAuthor);
        if (book.data.language !== null) {
            $("#book-language").html(placeholderLanguage);
        } else {
            $("#book-language").html(placeholderNoLanguage);
        }
        $("#book-pages").html(placeholderNrPages);
        if (book.data.description !== null) {
            $("#book-description").html(placeholderDescription);
        } else {
            $("#book-description").html(placeholderNoDescription);
        }
        $("#book-status").html(placeholderStatus);
        $("#book-tags").html(placeholderTags);
        if (book.data.addedBy !== null) {
            $("#book-added-by").html(placeholderAddedBy);
        } else {
            $("#book-added-by").html(placeholderNoAddedBy);
        }
        $("#book-added-at").html(placeholderAddedAt);
        displayTags($(book.data.tags));

        if (book.data.status === "FREE") {
            $("#assignButton th").html(assignButton);
            $("#waitingListMessage").css("display", "none");
            $("#messageSucces").css("display", "none");
            $("#messageError").css("display", "none");
            $("#afterAction").css("display", "none");

        } else {
            $("#assignButton th").html(waitingAssignButton);
            $("#assignBookMessage").css("display", "none");
            $("#messageSucces").css("display", "none");
            $("#messageError").css("display", "none");
            $("#afterAction").css("display", "none");
        }
        $("#txt-title").attr("value", placeholderTitle);
        if (book.data.description !== null) {
            $("#txt-description").val(`${book.data.description}`);
        } else {
            $("#txt-description").attr("placeholder",`Currently is not indicated`);
        }
        $("#txt-first-name").attr("value", authorFirstName);
        $("#txt-last-name").attr("value", authorLastName);
        $("#txt-pages").attr("value", `${book.data.pages}`);
        getLanguageTags(transformNameToTagName(book.data.language));
    }
    verifyUserAssignmentForThisBook(book.data.id);
    $("#submit-assign").on("click", () => {
        $.ajax({
            method: "POST",
            url: `/assignments/${book.data.id}/assign`,
            data: JSON.stringify(reviewObject()),
            contentType: "application/json",
            success: response => {
                let currentDate = new Date(getCurrentDate());
                let assignDate = new Date(response.assignDate);
                if(currentDate.getTime() === assignDate.getTime()){
                    $(".message-assign").text(`Congratulations! you can have this book starting from  today`);
                } else {
                    $(".message-assign").text(`Congratulations! you can have this book starting from  ` + `${response.assignDate}`);
                }
                $("#assignBook").css("display", "none");
                $("#afterAction").css("display", "block")
                $("#messageSucces").css("display", "block");
                $("#assignBookMessage").css("display", "none");
                $("#waitingListMessage").css("display", "none");
                $("#messageError").css("display", "none");
                $('#assignModal').on('hidden.bs.modal', function () {
                    location.reload();
                })

            }, error: err => {
                window.location.href = "/error";
            }
        })
    });
}

function displayTags(tags) {
    if (tags.length > 0) {
        let placeholder = "";
        $.each(tags, (index, tag) => {
            placeholder += `
        <span class="float-left border border-secondary">${tag}</span>
        `;
        });
        $("#tag-div").html(placeholder);
    } else {
        $("#tag-div").html("<p>There are no tags for this book</p>")
    }
}

function getCurrentDate() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();
    return today = yyyy + '-' + mm + '-' + dd;
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

function validateLanguage(language) {
    if (!language) {
        document.getElementById("errLast-language").innerHTML = "<span class='warning'>Please select language!</span>";
        document.getElementById("errLast-language").style.color = badColor;
        languageValidate = false;
    } else {
        document.getElementById("errLast-language").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errLast-language").style.color = goodColor;
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
        bookLanguage: transformNameToValidForm($("#txt-language").val()),
        pages: $("#txt-pages").val(),
        tagList: [],
        description: $("#txt-description").val()
    };
};

function getLanguageTags(currentLanguage) {
    $.ajax({
        url: "/tags/type/language",
        method: "GET",
        success: response => {
            displayLanguageTags(response, currentLanguage);
        }
    })
}

function displayLanguageTags(languages, currentLanguage) {
    if (languages.length > 0) {
        let placeholder = "";
        $.each(languages, (index, language) => {
            if(language.tagName === currentLanguage) {
                placeholder += `
                <option value="${language.tagName}" selected>${transformNameToValidForm(language.tagName)}</option>
                `;
            } else {
                placeholder += `
                <option value="${language.tagName}">${transformNameToValidForm(language.tagName)}</option>
                `;
            }
        });
        $("#txt-language").append(placeholder);
        $("#txt-language").selectpicker("refresh");
    }
}

function transformNameToValidForm(name) {
    return name.charAt(0).toUpperCase() + name.slice(1).toLowerCase().replace(/-/g, ' ');
}

function transformNameToTagName(name) {
    return name.toLowerCase().replace(/\s/g, '-');
}