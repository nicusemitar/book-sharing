$(document).ready(() => {
    getAssignedBooks();
});

function getAssignedBooks() {
    $.ajax({
        url: "/assignments/currentuser",
        method: "GET",
        success: response => {
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
                        <img src="/images/book-image.jpg" style="width: 100%; padding: 10%" align="center">
                        <p align="center">
                            ${assignments.bookName}<br>
                            Assign date: ${assignments.assignDate}<br>
                            Due date: ${assignments.dueDate}
                        </p>
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
}