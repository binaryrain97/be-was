package service;

import db.UserDatabase;
import db.SessionStorage;
import model.Session;
import model.User;
import model.UserInfo;

import java.util.Map;

public class UserService {

    public static User create(Map<String, String> params) {
        UserInfo userInfo = new UserInfo(params);
        if(!userInfo.validateInfo()) return null;
        User user = new User(userInfo);
        UserDatabase.addUser(user);
        return user;
    }

    public static String login(UserInfo userInfo) {
        String userId = userInfo.getUserId();
        String password = userInfo.getPassword();
        User user = UserDatabase.findUserById(userId);
        if(user == null || !user.getPassword().equals(password))
            return null;
        Session createdSession = new Session(userId);
        SessionStorage.addSession(createdSession);
        return createdSession.getSessionId();
    }
}
