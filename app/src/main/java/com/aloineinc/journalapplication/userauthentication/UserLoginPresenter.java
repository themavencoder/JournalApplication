package com.aloineinc.journalapplication.userauthentication;

public class UserLoginPresenter implements UserLoginContract.Presenter {
    private Person person;
    private UserLoginContract.View view;

    public  UserLoginPresenter(UserLoginContract.View view) {
        this.person = new Person();
    }

    @Override
    public void loadMessage() {
            if (person.getEmail() == null && person.getPassword() == null) {
                view.showError("No person name was found");
                return;
            }
        String message = "Hi " + person.getEmail();
            view.showMessage(message);
    }
    @Override
    public void changeActivity() {

    }

    @Override
    public void saveLoginDetails(String email, String password) {
        person.setEmail(email);
        person.setPassword(password);


    }


}
