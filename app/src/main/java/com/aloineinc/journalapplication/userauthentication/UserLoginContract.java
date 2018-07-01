package com.aloineinc.journalapplication.userauthentication;

public interface UserLoginContract {

    interface View{
        void showError();

        void showMessage();

    }

    interface Presenter {
    void changeActivity();

    void saveLoginDetails();
    }
}
