package controller;

import db.SessionStorage;
import model.*;
import service.UserService;

public class UserController {

    public static void route(Request request, Response response, String verifiedSessionId) {

        String path = request.getPath();

        if(path.equals("/user/list")) {
            if(verifiedSessionId != null) {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/user/list.html");
            }
            else {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/user/login.html");
            }
            return;
        }

        if(path.equals("/user/create")) {
            User user = UserService.create(request.getBody());
            if (user != null) {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/index.html");
            }
            else {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/user/form.html");
            }
            return;
        }

        if(request.getPath().equals("/user/login")) {
            UserInfo userInfo = new UserInfo(request.getBody());
            String sessionId = UserService.login(userInfo);
            if(sessionId != null) {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/index.html");
                response.addHeader("Set-Cookie", "sessionId=" + sessionId + "; Path=/; Max-Age=" + SessionStorage.SESSION_TIME);
            }
            else {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/user/login_failed.html");
            }
            return;
        }

        if(request.getPath().equals("/user/logout")) {
            if(verifiedSessionId != null) {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/index.html");
                response.addHeader("Set-Cookie", "sessionId=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/; Secure; HttpOnly");
            }
            else {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/user/login.html");
            }
            return;
        }
    }
}
