$(document).ready(() => {
    getAssignedBooks();
});
var currentAssigments;
var currentAssignment;

function getAssignedBooks() {
    $.ajax({
        url: "/assignments/current-user",
        method: "GET",
        success: response => {
            currentAssigments = response;
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
        let columnNumber = 0;
        let placeholder = `<tr>`;
        $.each(assignments, (index, assignments) => {
            placeholder +=
                `<td id="book${index}">
                    <div class="shadow p-4 mb-4 bg-white" style="width: 250px; margin: 25px">
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
                    </div>
                </td>`
            columnNumber++;
            if (columnNumber === 3) {
                placeholder += `</tr>`;
                columnNumber = 0;
            }
        });
        $("#books-table tbody").html(placeholder);
    } else {
        $("#assignedBooks").html("<p>No Assigned Books.</p>");
    }

    $(".extend1").on('click', function () {
        currentAssignment = currentAssigments[this.value];
        console.log(this);
    });
}

function clearTextArea() {
    $("#textArea").val("");
    $("#setDate").val("");
    $("#messageSucces").css("display", "none");
    $("#modalForSubmitAndError").css("display", "block");
    $("#messageError").css("display", "none");
    $("#submit").css("display", "block");
    $("#invalidReason").css("display", "none");
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
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

$("#books-table").on("click", ".shadow", function (e) {
    if (e.target !== this) {
        return;
    }
    let id = this.querySelector(".book-id").value;
    window.location.href = `/book/${id}`;
})