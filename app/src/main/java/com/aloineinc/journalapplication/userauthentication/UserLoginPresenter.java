package com.aloineinc.journalapplication.userauthentication;

public class UserLoginPresenter implements UserLoginContract.Presenter {
    private final Person person;

    public  UserLoginPresenter() {
        this.person = new Person();
    }

    @Override
    public void saveLoginDetails(String email, String password) {
        person.setEmail(email);
        person.setPassword(password);


    }

    @Override
    public String showPassword() {
        return person.getPassword();
    }

    @Override
    public String showEmail() {
        return person.getEmail();
    }


}
