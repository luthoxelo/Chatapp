package com.mycompany.chatapppart1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.chatapp.Login;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    
     Login login = new Login();

     @Test
     public void testValidUsername(){
         //this checks the username must have the underscore "_"
         //also checks the lenghth of the username must now than 5 (e.g "Lu_x1"}
     assertTrue(login.checkUserName("lu_x2"));
     }
     
     @Test
     public void testInvalidUsername_NoUnderscore(){
         //checks the usrename with no underscore
     assertFalse(login.checkUserName("john7"));
     }
     
     @Test
     public void testInvalidUsername_TooLong(){
         //reason why it is FALSE, the username is more than 5 characters 
         //it was supposssed to be 5 (e.g "lu_x2")
     assertFalse (login.checkUserName("lutho_x"));
     }
     //----------------- USERNAME TESTS -----------------
   
     
     
     @Test
     public void testInvalidPassword_NoCapital() {
         assertFalse(login.checkPasswordComplexity("password"));
     }

//----------------- PASSWORD TESTS -----------------
     @Test
     public void testValidPassword() {
         //assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99"));
         boolean result = login.checkPasswordComplexity("Ch&&sec@ke99");
         assertTrue(result);
     }
     
     @Test
     public void testInvalidPassword_NoCapital() {
         assertFalse(login.checkPasswordComplexity("password"));
     }
     
@Test
public void testInvalidPassword_NoNumber() {
assertFalse(login.checkPasswordComplexity("password@"));
}

@Test
public void testInvalidPassword_NoSpecialChar() {
    assertFalse(login.checkPasswordComplexity("password"));
}


@Test
public void testInvalidPassword_TooShort(){
assertFalse(login.checkPasswordComplexity("Pa!"));
}

//----------------- PHONE NUMBER TESTS -----------------
@Test
public void testValidPhoneNumber() {
    assertTrue(login.checkCellphoneNumber("+278389688976"));
}

@Test
public void testInvalidPhoneNumber_MissingPlus27() {
    assertFalse(login.checkCellphoneNumber("0838968976"));

}

//LOGIN SUCCESSFUL
@Test
public void testLoginSuccess() {
    login.registerUser("lu_x2", "Ch&&sec@ke99", "+278389688976");
    assertTrue(login.loginUser("lu_x2", "Ch&&sec@ke99"));
}

//LOGIN FAILED
@Test
public void testLoginFail() {
    login.registerUser("lu_x2", "Ch&&sec@ke99", "+278389688976");
    assertFalse(login.loginUser("lu_x2", "wrongpassword"));
}
}