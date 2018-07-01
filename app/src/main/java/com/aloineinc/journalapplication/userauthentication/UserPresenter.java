/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
