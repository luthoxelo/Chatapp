package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTestt {
    Login login = new Login();

    @Test
    public void testValidUsername(){
        assertTrue(login.checkUserName("lu_x2"));
    }

    @Test
    public void testInvalidUsername_NoUnderscore(){
        assertFalse(login.checkUserName("lutho2"));
    }

    @Test
    public void testInvalidUsername_TooLong(){
        assertFalse(login.checkUserName("lutho_x"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity("Lutho@10"));
    }

    @Test
    public void testInvalidPassword_NoCapital() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testInvalidPassword_NoNumber() {
        assertFalse(login.checkPasswordComplexity("Password@"));
    }

    @Test
    public void testInvalidPassword_NoSpecialChar() {
        assertFalse(login.checkPasswordComplexity("Password1"));
    }

    @Test
    public void testInvalidPassword_TooShort(){
        assertFalse(login.checkPasswordComplexity("Lu2"));
    }

    @Test
    public void testValidPhoneNumber() {
        assertTrue(login.checkCellphoneNumber("+27838968897"));
    }

    @Test
    public void testInvalidPhoneNumber_MissingPlus27() {
        assertFalse(login.checkCellphoneNumber("0838968976"));
    }

    @Test
    public void testLoginSuccess() {
        login.registerUser("lu_x2", "Lutho@10", "+27838968897");
        assertTrue(login.loginUser("lu_x2", "Lutho@10"));
    }

    @Test
    public void testLoginFail() {
        login.registerUser("lu_x2", "Lutho@10", "+27838968897");
        assertFalse(login.loginUser("lu_x2", "wrongpassword"));
    }
}