let titleValidate = false;
let lastNameValidate = false;
let firstNameValidate = false;
let pagesValidate = false;
let languageValidate = false;
let descriptionValidate = false;
let genresValidate = false;
let bindingValidate = false;
let qualityValidate = false;
let goodColor = "#66cc66";
let badColor = "#ff6666";


$(document).ready(() => {
    getAssignedBooks();
    getGenreTags();
    getQualityTags();
    getBindingTags();
});
var currentAssignments;
var currentAssignment;

function getAssignedBooks() {
    $.ajax({
        url: "/assignments/current-user",
        method: "GET",
        success: response => {
            currentAssignments = response;
            displayAssignedBooks(response);
        },
        error: err => {
            let responseObj = err.responseJSON;
            alert(`ERROR: " ${responseObj.message} " TIME ${responseObj.time}`);
        }
    })
}

function displayAssignedBooks(assignments) {
    if (assignments.length > 0) {
        let placeholder = "";
        $.each(assignments, (index, assignments) => {
            let currentDate = new Date(getCurrentDate());
            let assignDate = new Date(assignments.assignDate);
            if (currentDate.getTime() >= assignDate.getTime()) {
                placeholder +=
                    `<span class="shadow float-left p-4 mb-4 bg-white">
                    <input class='user-id' type='hidden' value='${assignments.id}'>
                    <input class='book-id' type='hidden' value='${assignments.bookId}'>
                    <img src="/images/book-image.jpg" style="width: 100%; padding: 10%" align="center">
                    <p align="center">
                        ${assignments.bookName}<br>
                        Assign date: ${assignments.assignDate}<br>
                        Due date: ${assignments.dueDate}
                    </p>
                    <button type="button" class="extend-button extend1" value="${index}"
                    id="extend-open" data-toggle="modal" data-target="#exampleModal">Extend Time</button>
                </span>`
            } else {
                placeholder +=
                    `<span class="shadow float-left p-4 mb-4 bg-white" style="opacity: 0.4;">
                    <input class='user-id' type='hidden' value='${assignments.id}'>
                    <input class='book-id' type='hidden' value='${assignments.bookId}'>
                    <img src="/images/waiting-assignment.png" style="width: 100%; padding: 10%" align="center">
                    <p align="center">
                        ${assignments.bookName}<br>
                        This book will be
                        available for you on: ${assignments.assignDate}<br>
                    </p>
                    <button disabled="true" type="button" class="extend-button extend1" value="${index}"
                    id="extend-open" data-toggle="modal" data-target="#exampleModal">Extend Time</button>
                </span>`
            }
        });
        $("#books-placeholder").html(placeholder);
    } else {
        $("#books-placeholder").html("<p>You don't have any assigned books.</p>");
    }

    $(".extend1").on('click', function () {
        currentAssignment = currentAssignments[this.value];
        console.log(this);
    });
}

function getCurrentDate() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();
    return today = yyyy + '-' + mm + '-' + dd;
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

function validateGenres(genres) {
    if (!genres || genres.length === 0) {
        document.getElementById("errGenre").innerHTML = "<span class='warning'>Please select at least one genre!</span>";
        document.getElementById("errGenre").style.color = badColor;
        genresValidate = false;
    } else {
        document.getElementById("errGenre").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errGenre").style.color = goodColor;
        genresValidate = true;
    }
}

function validateBinding(binding) {
    if (!binding) {
        document.getElementById("errBinding").innerHTML = "<span class='warning'>Please select binding!</span>";
        document.getElementById("errBinding").style.color = badColor;
        bindingValidate = false;
    } else {
        document.getElementById("errBinding").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errBinding").style.color = goodColor;
        bindingValidate = true;
    }
}

function validateQuality(quality) {
    if (!quality) {
        document.getElementById("errQuality").innerHTML = "<span class='warning'>Please select quality!</span>";
        document.getElementById("errQuality").style.color = badColor;
        qualityValidate = false;
    } else {
        document.getElementById("errQuality").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errQuality").style.color = goodColor;
        qualityValidate = true;
    }
}

function validateDescription(txt) {
    if (txt.trim().length < 1) {
        document.getElementById("errDescription").innerHTML = "<span class='warning'>Please enter the description!</span>";
        document.getElementById("errDescription").style.color = badColor;
        document.getElementById("txt-description").style.border = "medium solid #ff6666"
        descriptionValidate = false;
    } else {
        document.getElementById("errDescription").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errDescription").style.color = goodColor;
        document.getElementById("txt-description").style.border = "medium solid #66cc66"
        descriptionValidate = true;
    }
}

// TAGS BELOW!!!
let tags = [];

[].forEach.call(document.getElementsByClassName('tags-input'), function (el) {
    let hiddenInput = document.createElement('input'),
        mainInput = document.createElement('input');

    hiddenInput.setAttribute('type', 'hidden');
    hiddenInput.setAttribute('name', el.getAttribute('data-name'));

    mainInput.setAttribute('type', 'text');
    mainInput.classList.add('main-input');
    mainInput.addEventListener('input', function () {
        let enteredTags = mainInput.value.split(',');

        if (enteredTags.length > 1) {
            enteredTags.forEach(function (t) {
                let filteredTag = filterTag(t);
                if (filteredTag.length > 0)
                    addTag(filteredTag);
            });
            mainInput.value = '';
        }
    });

    mainInput.addEventListener('keydown', function (e) {
        let keyCode = e.which || e.keyCode;
        if (keyCode === 8 && mainInput.value.length === 0 && tags.length > 0) {
            removeTag(tags.length - 1);
        }
    });

    el.appendChild(mainInput);
    el.appendChild(hiddenInput);

    function addTag(text) {
        let tag = {
            text: text,
            element: document.createElement('span'),
        };
        tag.element.classList.add('tag');
        tag.element.textContent = tag.text;

        let closeBtn = document.createElement('span');
        closeBtn.classList.add('close1');
        closeBtn.addEventListener('click', function () {
            removeTag(tags.indexOf(tag));
        });
        tag.element.appendChild(closeBtn);

        let countSame = 0;
        tags.forEach(element => {
            if (tag.text.localeCompare(element.text) === 0)
                countSame++;
        })
        if (countSame === 0) {
            tags.push(tag);
            el.insertBefore(tag.element, mainInput)
        }
    }

    function removeTag(index) {
        let tag = tags[index];
        tags.splice(index, 1);
        el.removeChild(tag.element);
        refreshTags();
    }

    function refreshTags() {
        let tagsList = [];
        tags.forEach(function (t) {
            tagsList.push(t.text);
        });
        hiddenInput.value = tagsList.join(',');
    }

    function filterTag(tag) {
        return tag.replace(/[^\w -]/g, '').trim().replace(/\W+/g, '-');
    }
})

const validInput = () => {
    if (titleValidate === true && lastNameValidate === true && firstNameValidate === true && pagesValidate === true
        && languageValidate === true && descriptionValidate === true && genresValidate === true
        && bindingValidate === true && qualityValidate === true) {
        return true;
    } else {
        validateTitle($("#txt-title").val());
        validateFirstName($("#txt-first-name").val());
        validateLastName($("#txt-last-name").val());
        validateLanguage($("#txt-language").val());
        validatePages($("#txt-pages").val());
        validateDescription($("#txt-description").val());
        validateGenres($("#genre").val());
        validateQuality($("#quality").val());
        validateBinding($("#binding").val());
        return false;
    }
}

function openGeneralContent() {
    $("#error-content").css("display", "none");
    $("#success-content").css("display", "none");
    $("#general-content").css("display", "block");
    $("#submit-add-book").css("display", "block");
    $("#txt-title").val("");
    $("#txt-first-name").val("");
    $("#txt-last-name").val("");
    $("#txt-language").val("");
    $("#txt-pages").val("");
    $("#txt-description").val("");
    $("#genre").val('default').selectpicker("refresh");
    $("#quality").val('default').selectpicker("refresh");
    $("#binding").val('default').selectpicker("refresh");
}

function openSuccessContent() {
    $("#error-content").css("display", "none");
    $("#general-content").css("display", "none");
    $("#submit-add-book").css("display", "none");
    $("#success-content").css("display", "block");
}

function openErrorContent() {
    $("#success-content").css("display", "none");
    $("#general-content").css("display", "none");
    $("#submit-add-book").css("display", "none");
    $("#error-content").css("display", "block");
}

$("#submit-add-book").on("click", () => {
    if (validInput() === true) {
        $.ajax({
            url: "/books",
            method: "POST",
            data: JSON.stringify(bookObject()),
            contentType: "application/json",
            success: () => {
                openSuccessContent();
            },
            error: () => {
                openErrorContent();
            }
        });
    }
});

const bookObject = () => {
    let tagList2 = []

    tags.forEach(function (element) {
        const tagName = () => {
            return {
                tagName: element.text,
                tagType: "CUSTOM"
            };
        };
        tagList2.push(tagName());
    })

    let selectedTag = () => {
        return {
            tagName: $("#quality").val(),
            tagType: "QUALITY"
        }
    }
    tagList2.push(selectedTag());

    selectedTag = () => {
        return {
            tagName: $("#binding").val(),
            tagType: "BINDING"
        }
    }
    tagList2.push(selectedTag());

    $("#genre").val().forEach(function (element) {
        const tagName = () => {
            return {
                tagName: element,
                tagType: "GENRE"
            }
        };
        tagList2.push(tagName());
    })

    return {
        title: $("#txt-title").val(),
        authorFirstName: $("#txt-first-name").val(),
        authorLastName: $("#txt-last-name").val(),
        bookLanguage: $("#txt-language").val(),
        pages: $("#txt-pages").val(),
        tagList: tagList2,
        description: $("#txt-description").val()
    };
};

function clearTextArea() {
    $("#textArea").val("");
    $("#setDate").val("");
    $("#messageSucces").css("display", "none");
    $("#modalForSubmitAndError").css("display", "block");
    $("#messageError").css("display", "none");
    $("#submit").css("display", "block");
    $("#invalidReason").css("display", "none");
    location.reload();
}

function submitRequest() {
    if ($("#textArea").val().trim().length > 0 && $("#textArea").val().length <= 2048) {
        if ($("#setDate").val() > currentAssignment.dueDate) {
            let id = currentAssignment.id;
            $.ajax({
                url: `/assignments/${id}/extends`,
                method: "POST",
                data: JSON.stringify(timeExtendObject()),
                contentType: "application/json",
                success: () => {
                    submitSucces();
                },
                error: () => {
                    submitError();
                }
            })
        } else {
            $("#invalidReason").html("<div class=\"alert alert-danger\" role=\"alert\">\n" +
                "Please choose at least next day for new Due Date !</div>");
            $("#invalidReason").css("display", "block");
        }
    } else {
        $("#invalidReason").html("<div class=\"alert alert-danger\" role=\"alert\">\n" +
            "Please fill up extend reason !</div>");
        $("#invalidReason").css("display", "block");
    }
}

const timeExtendObject = () => {
    return {
        description: $("#textArea").val(),
        requestedDate: $("#setDate").val()
    };
}

$(document).on('show.bs.modal', '#exampleModal', function () {
    console.log(currentAssignment.dueDate);
    $("#setDate").val(currentAssignment.dueDate);
    console.log(currentAssignment);

    var assignDate = new Date(currentAssignment.assignDate);
    assignDate.setDate(assignDate.getDate() + 29);

    $("#setDate").attr({
        "max": formatDate(assignDate),
        "min": currentAssignment.dueDate
    });
});

function submitSucces() {
    $("#textArea").val("");
    $("#setDate").val("");
    $("#messageSucces").css("display", "block");
    $("#modalForSubmitAndError").css("display", "none");
    $("#messageError").css("display", "none");
    $("#submit").css("display", "none");
    $("#invalidReason").css("display", "none");
}

function submitError() {
    $("#textArea").val("");
    $("#setDate").val("");
    $("#messageSucces").css("display", "none");
    $("#modalForSubmitAndError").css("display", "none");
    $("#messageError").css("display", "block");
    $("#submit").css("display", "none");
    $("#invalidReason").css("display", "none");
}

function formatDate(date) {
    let d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

$("#books-placeholder").on("click", ".shadow", function (e) {
    if (e.target !== this.querySelector("#extend-open")) {
        let id = this.querySelector(".book-id").value;
        window.location.href = `/book/${id}`;
    }
    return;
})

function getGenreTags() {
    $.ajax({
        url: "/tags/type/genre",
        method: "GET",
        success: response => {
            displayGenreTags(response);
        }
    })
}

function displayGenreTags(genres) {
    if (genres.length > 0) {
        let placeholder = "";
        $.each(genres, (index, genre) => {
            placeholder += `
                <option value="${genre.tagName}">${genre.tagName}</option>
                `;
        });
        $("#genre").append(placeholder);
        $("#genre").selectpicker("refresh");
    }
}

function getQualityTags() {
    $.ajax({
        url: "/tags/type/quality",
        method: "GET",
        success: response => {
            displayQualityTags(response);
        }
    })
}

function displayQualityTags(qualities) {
    if (qualities.length > 0) {
        let placeholder = "";
        $.each(qualities, (index, quality) => {
            placeholder += `
                <option value="${quality.tagName}">${quality.tagName}</option>
                `;
        });
        $("#quality").append(placeholder);
        $("#quality").selectpicker("refresh");
    }
}

function getBindingTags() {
    $.ajax({
        url: "/tags/type/binding",
        method: "GET",
        success: response => {
            displayBindingTags(response);
        }
    })
}

function displayBindingTags(bindings) {
    if (bindings.length > 0) {
        let placeholder = "";
        $.each(bindings, (index, binding) => {
            placeholder += `
                <option value="${binding.tagName}">${binding.tagName}</option>
                `;
        });
        $("#binding").append(placeholder);
        $("#binding").selectpicker("refresh");
    }
}