package controller;

import model.*;
import service.BoardService;
import service.UserService;

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
            BoardInfo boardInfo = new BoardInfo(request.getBody());
            Board board = BoardService.create(boardInfo);
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
