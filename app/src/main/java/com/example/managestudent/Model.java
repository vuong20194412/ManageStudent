package com.example.managestudent;

import java.util.Date;

public class Model {
    int code;
    String fullName;
    String email;
    String birthday;

    public Model(int code, String fullName, String email, Date birthday) {
        this.code = code;
        this.fullName = fullName;
        this.email = email;
        if (birthday == null)
            this.birthday = null;
        else
            this.birthday = birthday.toString();
    }
}
