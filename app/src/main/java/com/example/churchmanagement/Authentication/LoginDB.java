package com.example.churchmanagement.Authentication;

import io.realm.Realm;
import io.realm.RealmQuery;

public class LoginDB {


    public boolean checkUsernameExist(String username) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Login> query = realm.where(Login.class)
                .equalTo("email", username);

        Login result = query.findFirst();
        if (result != null) {
            realm.close();
            return true;
        } else {
            realm.close();
            return false;
        }

    }

    public boolean login(String username,String password) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Login> query = realm.where(Login.class)
                .equalTo("email", username)
                .equalTo("password", password);

        Login result = query.findFirst();
        if (result != null) {
            realm.close();
            return true;
        } else {
            realm.close();
            return false;
        }

    }
}
