package com.hexagraph.pattagobhi.util

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return email.matches(emailRegex.toRegex())
}

fun validatePassword(password: String): String? {
    if (password.length < 8) {
        return "Password should be at least 8 characters long"
    }
    if (!password.any { it.isUpperCase() }) {
        return "Password should contain at least one uppercase letter"
    }
    if (!password.any { it.isLowerCase() }) {
        return "Password should contain at least one lowercase letter"
    }
    if (!password.any { it.isDigit() }) {
        return "Password should contain at least one digit"
    }
//    if (!password.contains(Regex("[@#$%^&+=]"))) {
//        return "Password should contain at least one special character (@, #, $, %, ^, &, +, =)"
//    }
    return null
}


fun nameCheck(name: String, error: (Boolean, String)-> Unit): Boolean{
    if(name.isEmpty()){
        error(true, "Name cannot be empty")
        return true
    }
    error(false, "")
    return false
}

fun emailCheck(email: String, error: (Boolean, String) -> Unit): Boolean{
    if(isValidEmail(email)){
        error(false, "")
        return false
    }
    error(true, "Invalid email format")
    return true
}

fun passwordCheck(password: String, error: (Boolean, String)-> Unit):Boolean{
    val errorText = validatePassword(password)
    if(errorText == null){
        error(false, "")
        return false
    }
    error(true, errorText)
    return true
}

fun confirmPasswordCheck(password: String, confirmPassword: String,
                         error: (Boolean, String) -> Unit):Boolean{
    if(password != confirmPassword){
        error(true, "Passwords must match")
        return true
    }
    error(false, "")
    return false
}
