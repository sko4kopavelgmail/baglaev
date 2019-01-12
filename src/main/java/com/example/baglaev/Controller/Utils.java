package com.example.baglaev.Controller;

import java.util.Map;

public class Utils {
    public String validate(Map<String, String> map) {
        String userName = map.get("username");
        String password = map.get("password");

        if (userName.length() < 5) {
            return "ваш логин сильно короткий";
        }
        if (password.length() < 6) {
            return "ваш пароль сильно короткий";
        }
        return null;

    }

}
