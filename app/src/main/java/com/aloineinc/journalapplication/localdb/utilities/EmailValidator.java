package com.aloineinc.journalapplication.localdb.utilities;

public abstract class EmailValidator {
    public static boolean isValidEmail(String email)
    {
        String emailRegex ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if(email.matches(emailRegex))
        {
            return true;
        }
        return false;
    }
}
