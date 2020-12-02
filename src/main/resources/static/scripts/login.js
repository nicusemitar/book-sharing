$("#add-user-submit").on("click", () => {
    var error = validateUsername($("#username-input").val());

    if(error != ""){
        $("#valid-error").show();
        $("#validerr").text(error);
    }else{
        $("#add-user-submit").prop('type','submit');
    }
});

$( document ).ready(function() {
    $("#valid-error").hide();
});


const validateUsername = (str) => {
    var error = "";
    var illegalChars = /\W/; // allow letters, numbers, and underscores

    if (str == "") {
        error = "Please enter Username";
    } else if ((str.length < 3) || (str.length > 18)) {
        error = "Username must have 3-18 characters";
    } else if (illegalChars.test(str)) {
        error = "Please enter valid Username. Use only numbers and alphabets";
    } else {
        error = "";
    }
    return error;
}