package com.aloineinc.journalapplication.userauthentication;

interface UserLoginContract {

    interface View{

    }

    interface Presenter {


    void saveLoginDetails(String email, String password);
    String showPassword();
    String showEmail();


    }
}
