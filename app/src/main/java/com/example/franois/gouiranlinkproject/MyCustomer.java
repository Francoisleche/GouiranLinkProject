package com.example.franois.gouiranlinkproject;

import java.io.Serializable;

class MyCustomer implements Serializable {
    private boolean mFacebook;
    private boolean mGoogle;
    private boolean mGouiranLink;
    private String name;
    private String surname;
    private String email;
    private String birthday;

    MyCustomer() {
        mFacebook = false;
        mGoogle = false;
        mGouiranLink = false;
        name = "";
        surname = "";
        email = "";
        birthday = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    boolean ismFacebook() {
        return mFacebook;
    }

    void setmFacebook(boolean mFacebook) {
        this.mFacebook = mFacebook;
    }

    boolean ismGoogle() {
        return mGoogle;
    }

    void setmGoogle(boolean mGoogle) {
        this.mGoogle = mGoogle;
    }

    public boolean ismGouiranLink() {
        return mGouiranLink;
    }

    void setmGouiranLink(boolean mGouiranLink) {
        this.mGouiranLink = mGouiranLink;
    }

}
