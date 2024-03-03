package model;

import db.UserDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo {
    private HashMap<String, String> info;
    public UserInfo(Map<String, String> params) {
        this.info = new HashMap<>();
        this.info.put("userId", params.get("userId"));
        this.info.put("password", params.get("password"));
        this.info.put("name", params.get("name"));
        this.info.put("email", params.get("email"));
    }
    public HashMap<String, String> getInfo() {return this.info;}
    public void addInfo(String key, String value) {
        this.info.put(key, value);
    }
    public void deleteInfo(String key) {
        info.remove(key);
    }
    public String getUserId() {return this.info.get("userId");}
    public String getPassword() {return this.info.get("password");}
    public String getName() {return this.info.get("name");}
    public String getEmail() {return this.info.get("email");}

    public boolean validateInfo() {
        for(String key : info.keySet()) {
            String value = info.getOrDefault(key, "");
            if(value == null || value.isEmpty()) return false;
        }
        if(UserDatabase.findUserById(info.getOrDefault("userId", "")) != null)
            return false;
        return true;
    }
}
