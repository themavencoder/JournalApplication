package com.aloineinc.journalapplication.userauthentication;

interface UserContract {

    interface View{

    }

    interface Presenter {


    void saveLoginDetails(String email, String password);
    void saveResetPassword(String email);
    String showPassword();
    String showEmail();


    }
}
