$(document).ready(() => {
    getAssignedBooks();
});

let currentAssignments;
let currentAssignment;
let titleValidate = true;
let lastNameValidate = true;
let firstNameValidate = true;
let pagesValidate = true;
let languageValidate = true;
let descriptionValidate = false;
let goodColor = "#66cc66";
let badColor = "#ff6666";

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
        document.getElementById("errLast-description").innerHTML = "<span class='warning'>Please enter the description</span>";
        document.getElementById("errLast-description").style.color = badColor;
        document.getElementById("txt-description").style.border = "medium solid #ff6666"
        descriptionValidate = false;
    } else {
        document.getElementById("errLast-description").innerHTML = "<span class='valid'>Good!</span>";
        document.getElementById("errLast-description").style.color = goodColor;
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
        && languageValidate === true && descriptionValidate === true) {
        return true;
    } else {
        validateTitle($("#txt-title").val());
        validateFirstName($("#txt-first-name").val());
        validateLastName($("#txt-last-name").val());
        validateLanguage($("#txt-language").val());
        validatePages($("#txt-pages").val());
        validateDescription($("#txt-description").val());
        return false;
    }
}

$("#submit-add-book").on("click", () => {
    if (validInput() === true) {
        $.ajax({
            url: "/books",
            method: "POST",
            data: JSON.stringify(bookObject()),
            contentType: "application/json",
            success: function (result) {
                alert("Your book was added successfully");
                window.location.href = '/personal-cabinet';
            },
            error: function (result) {
                alert("Could not add your book!");
                window.location.href = '/personal-cabinet';
            }
        });
    } else {
        document.getElementById("myModal").style.display = "block"
    }
});

const bookObject = () => {
    let tagList2 = []

    tags.forEach(function (element) {
        const tagName = () => {
            return {
                tagName: element.text
            };
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
    if (e.target !== this) {
        return;
    }
    let id = this.querySelector(".book-id").value;
    window.location.href = `/book/${id}`;
})
