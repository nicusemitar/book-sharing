let goodColor = "#66cc66";
let badColor = "#ff6666";
let emailValidate = false;
let passvalidate = false;
let usernameValidate = false;
let pass1Validate = false;

$('input:checkbox').prop('checked', false);

function checkPass() {
    const pass1 = document.getElementById('pass1');
    const pass2 = document.getElementById('pass2');
    const message = document.getElementById('confirmMessage');

    if (pass1.value === pass2.value) {
        pass2.style.border = "medium solid #66cc66"
        message.style.color = goodColor;
        message.innerHTML = "Passwords Match"
        passvalidate = true;
    } else {
        pass2.style.border = "medium solid #ff6666"
        message.style.color = badColor;
        message.innerHTML = "Passwords Do Not Match!"
        passvalidate = false;
    }
}

function validatePassword(password) {
    let regAdd = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/

    if (regAdd.test(password) === false) {
        document.getElementById("passwordMessage").innerHTML = "<span class='warning'>Minimum eight characters, at least one letter and one number</span>";
        document.getElementById("passwordMessage").style.color = badColor;
        document.getElementById("pass1").style.border = "medium solid #ff6666"
        pass1Validate = false;
    } else {
        document.getElementById("passwordMessage").innerHTML = "<span class='valid'>Your password looks good!</span>";
        document.getElementById("passwordMessage").style.color = goodColor;
        document.getElementById("pass1").style.border = "medium solid #66cc66"
        pass1Validate = true;
    }
}

function Validate(txt) {
    let regAdd = /^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$/
    if (regAdd.test(txt) === false) {
        document.getElementById("errLast").innerHTML = "<span class='warning'>Please use only letters and numbers!</span>";
        document.getElementById("errLast").style.color = badColor;
        document.getElementById("txt").style.border = "medium solid #ff6666"
        usernameValidate = false;
    } else {
        document.getElementById("errLast").innerHTML = "<span class='valid'>Your username looks good!</span>";
        document.getElementById("errLast").style.color = goodColor;
        document.getElementById("txt").style.border = "medium solid #66cc66"
        usernameValidate = true;
    }
}

function emailValidate(email) {
    const regMail = /^([_a-zA-Z0-9-]+)(\.[_a-zA-Z0-9-]+)*@([a-zA-Z0-9-]+\.)+([a-zA-Z]{2,3})$/;

    if (regMail.test(email) === false) {
        document.getElementById("status").innerHTML = "<span class='warning'>Email address is not valid yet.</span>";
        document.getElementById("status").style.color = badColor;
        document.getElementById("email").style.border = "medium solid #ff6666"
        emailValidate = false;

    } else {
        document.getElementById("status").innerHTML = "<span class='valid'>Thanks, you have entered a valid Email address!</span>";
        document.getElementById("status").style.color = goodColor;
        document.getElementById("email").style.border = "medium solid #66cc66"
        emailValidate = true;
    }
}

const validInput = () => {
    if (emailValidate === true && passvalidate === true && usernameValidate === true && pass1Validate === true) {
        return true;
    } else {
        return false;
    }
}

$("#register").on("click", () => {
    if (validInput() === true) {
        $.ajax({
            method: "POST",
            url: "/users",
            data: JSON.stringify(userObject()),
            contentType: "application/json",
            success: function (result) {
                document.getElementById("success_tic").style.display = "block"
                window.location.href = '/login';
            },
            error: function (result) {
                window.location.href = '/errorRegister';
            }
        });
    } else {
        document.getElementById("myModal").style.display = "block"
    }
});

let span = document.getElementsByClassName("close")[0];
let modal = document.getElementById("myModal");
span.onclick = function () {
    modal.style.display = "none";
}

window.onclick = function (event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

const userObject = () => {
    return {
        username: $("#txt").val(),
        password: $("#pass1").val(),
        email: $("#email").val()
    };
};
