$(() => {
    getAllBooks();
    displayAllCategories();
});

let filterApplied = false;

function getAllBooks() {
    $.ajax({
        url: "/books",
        method: "GET",
        data: {
            sort: $('#sortSelect').prop('value'),
            size: $('#itemsNumber').prop('value'),
            find: $('#search-key').prop('value')
        },
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
        let bookLanguage;
        if (book.language != null) {
            bookLanguage = book.language;
        } else {
            bookLanguage = 'Not indicated';
        }
        placeholder +=
            `<span class="shadow float-left p-4 mb-4 bg-white" style="height: 320px">
                <input class='user-id' type='hidden' value='${book.id}'>
                ${st}
                <img src="/images/book-image.jpg" style="width: 100%; padding: 10%" align="center">
                <p align="center">
                    ${book.title}<br>
                    ${book.authorName}<br>
                    Language: ${bookLanguage}
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
    if (!filterApplied) {
        $.ajax({
            url: "/books",
            method: "GET",
            data: {
                page: i - 1,
                size: $('#itemsNumber').prop('value'),
                sort: $('#sortSelect').prop('value'),
                find: $('#search-key').prop('value')
            },
            success: response => {
                displayBooks(response.books);
                displayPages(response.totalPages, response.currentPage + 1);
            },
            error: () => {
                alert("Something went wrong")
            }
        })
    } else {
        filterRequest()
    }
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
    if (filterApplied) {
        filterRequest();
    } else {
        let key = $(this).val();
        $.ajax({
            url: "/books",
            method: "GET",
            data: {
                find: key,
                size: $('#itemsNumber').prop('value'),
                sort: $('#sortSelect').prop('value')
            },
            success: response => {
                displayBooks(response.books);
                displaySearchPages(response.totalPages, response.currentPage + 1);
            },
            error: () => {
                alert("Something went wrong")
            }
        })
    }
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
    if (filterApplied) {
        filterRequest(i);
    } else {
        $.ajax({
            url: "/books",
            method: "GET",
            data: {
                page: i - 1,
                find: $("#search-key").val(),
                size: $('#itemsNumber').prop('value')
            },
            success: response => {
                displayBooks(response.books);
                displaySearchPages(response.totalPages, response.currentPage + 1);
            },
            error: () => {
                alert("Something went wrong")
            }
        })
    }
}

$("#books-placeholder").on("click", ".shadow", function () {
    let id = this.querySelector(".user-id").value;
    window.location.href = `/book/${id}`;
})

$("#filter-button").on("click", filterRequest);

$("#sortSelect").on("change", function () {
    if (filterApplied) {
        filterRequest();
    } else {
        getAllBooks();
    }
})

function filterRequest(pageNumber = 1) {
    let $genTags = [];
    $.each($("input[name='GENRE']:checked"), function () {
        $genTags.push($(this).prop('id'));
    });
    let $tags = [];
    $.each($("input[type='radio'].filter-box:checked"), function () {
        if ($(this).prop('id') !== "") {
            $tags.push($(this).prop('id'));
        }

    });
    if (isNaN(pageNumber)) pageNumber = 1;

    let request = {
        authorName: $('#authorSearch').val(),
        language: $('#language').prop('value'),
        genTags: $genTags,
        tags: $tags,
        tagsFind: transformNameToTagName($('#tagsSearch').val()),
        sort: $('#sortSelect').prop('value'),
        size: $('#itemsNumber').prop('value'),
        find: $('#search-key').val(),
        status: $("input[name='availability']:checked").val(),
        page: pageNumber - 1,
    };

    if (!filterApplied && request.genTags.length === 0 && request.tags.length === 0
        && request.language === "" && request.authorName === "" && request.tagsFind === "" && request.status === "") {
        alert("Apply some filters first");
    } else {
        $.ajax({
            type: 'POST',
            url: '/books/filter',
            data: request,
            success: response => {
                displayBooks(response.books);
                displaySearchPages(response.totalPages, response.currentPage + 1);
                filterApplied = true;
                $("#reset-filters").css("display", "inline-block");
            },
            error: () => {
                alert("Something went wrong in filter request");
            }
        })
    }
}

$("#reset-filters").on("click", function () {
    $.each($("input[name='GENRE']:checked"), function () {
        $(this).prop('checked', false);
    });
    $.each($("input[type='radio']:checked"), function () {
        $(this).prop('checked', false);
    });
    $("#language").val("");
    $("#authorSearch").val("");
    $("#tagsSearch").val("")
    $("#reset-filters").hide();
    filterApplied = false;
    getAllBooks();
});

$("#itemsNumber").on("change", function () {
    if (filterApplied) {
        filterRequest();
    } else {
        getAllBooks();
    }
});

function getTagsByType(tagType) {
    let stringResponse = ""
    $.ajax({
        type: 'GET',
        url: "/tags/type/" + tagType,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        async: !1,
        success: response => {
            stringResponse = response;
        },
        error: () => {
            alert("Error while getting tags")
        }
    });
    return stringResponse;
}

function displayFilterCategory(tagsType, inputType = "checkbox") {
    let tags = getTagsByType(tagsType)
    let placeholder = "";

    placeholder += `<h6 class="filter-header">${transformNameToValidForm(tagsType)}</h6>`

    if (inputType === "radio") {
        placeholder += `<label><input type="${inputType}" name="${tagsType}" class="filter-box"> All</label>`
    }
    $.each(tags, (index) => {

        placeholder +=
            `<tr>
                <td>
                  <input  id="${tags[index].tagName}" type="${inputType}" name="${tagsType}" class="filter-box">
                  <label for="${tagsType}">${transformNameToValidForm(tags[index].tagName)}</label>
                </td>
             </tr>`
    });
    return placeholder;
}

function transformNameToValidForm(name) {
    return name.charAt(0).toUpperCase() + name.slice(1).toLowerCase().replace(/-/g, ' ');
}

function transformNameToTagName(name) {
    return name.toLowerCase().replace(/\s/g, '-');
}

function returnBookLangs() {
    let stringResponse = ""
    $.ajax({
        type: "GET",
        url: "/books/lang",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        async: !1,
        success: response => {
            stringResponse = response;
        },
        error: () => {
            alert("Error while getting languages")
        }
    });
    return stringResponse;
}

function displaySelector(selectorName, values, withFieldAll = true) {
    let placeholder = `<label for="${selectorName}"><h6 class="filter-header">${transformNameToValidForm(selectorName)}</h6></label>
                        <select class="custom-select" id="${selectorName}">`

    if (withFieldAll) placeholder += `<option value="">All</option>`;

    $.each(values, (index) => {
        placeholder += `<option value="${values[index]}">${transformNameToValidForm(values[index])}</option>`
    })
    return placeholder + `</select>`
}

function displayAllCategories() {
    let placeholder = "";
    placeholder += displayFilterCategory("GENRE");
    placeholder += displayFilterCategory("QUALITY", "radio");
    placeholder += displayFilterCategory("BINDING", "radio");
    placeholder += displaySelector("language", returnBookLangs());
    $("#filters-placeholder tbody").html(placeholder);
}