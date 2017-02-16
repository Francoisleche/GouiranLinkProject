package com.example.franois.gouiranlinkproject.ToolsClasses;

import java.io.Serializable;

public class MyCustomer implements Serializable {
    private boolean mFacebook;
    private boolean mGoogle;
    private boolean mGouiranLink;
    private String name;
    private String surname;
    private String email;
    private String birthday;

   public MyCustomer() {
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

    public void setBirthday(String birthday) {
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

    public boolean ismFacebook() {
        return mFacebook;
    }

    public void setmFacebook(boolean mFacebook) {
        this.mFacebook = mFacebook;
    }

    public boolean ismGoogle() {
        return mGoogle;
    }

    public void setmGoogle(boolean mGoogle) {
        this.mGoogle = mGoogle;
    }

    public boolean ismGouiranLink() {
        return mGouiranLink;
    }

    public void setmGouiranLink(boolean mGouiranLink) {
        this.mGouiranLink = mGouiranLink;
    }

}
