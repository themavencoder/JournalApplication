package com.aloineinc.journalapplication.userauthentication;

public class UserPresenter implements UserContract.Presenter {
    private final Person person;

    public UserPresenter() {
        this.person = new Person();
    }

    @Override
    public void saveLoginDetails(String email, String password) {
        person.setEmail(email);
        person.setPassword(password);


    }

    @Override
    public void saveResetPassword(String email) {
        person.setEmail(email);
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
