package controller;

import model.Request;
import model.Response;
import model.StatusCode;

public class BoardController {
    public static void route(Request request, Response response, String verifiedSessionId) {

        if(request.getPath().equals("/qna/form")) {
            if(verifiedSessionId != null) {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/qna/form.html");
            }
            else {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/user/login.html");
            }
            return;
        }

        if(request.getPath().equals("/qna/write")) {

        }

        LastController.route(request, response, verifiedSessionId);
    }
}
