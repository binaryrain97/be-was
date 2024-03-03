package controller;

import model.*;
import service.BoardService;
import service.UserService;

public class BoardController {
    public static void route(Request request, Response response, String verifiedSessionId) {

        String path = request.getPath();

        if(path.equals("/qna/form")) {
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

        if(path.equals("/qna/write")) {
            Board board = BoardService.create(request.getBody());
            if (board != null) {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/index.html");
            }
            else {
                response.setStatusCode(StatusCode.FOUND);
                response.addHeader("Location", "/qna/form.html");
            }
            return;
        }
    }
}
