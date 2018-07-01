package com.aloineinc.journalapplication.userauthentication;

public interface UserLoginContract {

    interface View{
        void showError(String error);

        void showMessage(String message);

    }

    interface Presenter {
    void changeActivity();

    void saveLoginDetails(String email, String password);

    void loadMessage();
    }
}
