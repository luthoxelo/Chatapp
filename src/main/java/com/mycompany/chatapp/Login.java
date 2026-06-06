package com.mycompany.chatapp;

public class Login {
    String username;
    String password;
    String phoneNumber;

    public boolean checkUserName(String username){
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password){
        if (password == null) return false;
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) hasCapital = true;
            else if (Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellphoneNumber(String phone) {
        return phone!= null && phone.startsWith("+27") && phone.length() == 12;
    }

    public String registerUser(String username, String password, String phone) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellphoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        this.username = username;
        this.password = password;
        this.phoneNumber = phone;
        return "User registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        return this.username!= null && this.password!= null
                && this.username.equals(username)
                && this.password.equals(password);
    }

    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + username + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}