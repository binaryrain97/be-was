package webserver;

import controller.BoardController;
import controller.ResourceController;
import controller.HomeController;
import controller.UserController;
import db.SessionStorage;
import model.Request;
import model.Response;

public class DispatcherServlet {

    public static Response dispatch(Request request) {
        Response response = new Response();
        String path = request.getPath();

        // 만료기한이 지난 세션을 삭제 처리
        SessionStorage.updateStorage();

        // 요청에 실린 sessionId가 유효한지 판단해서 로그인 여부 저장
        String verifiedSessionId = SessionStorage.verifySession(request.getSessionId());

        if(path.startsWith("/user"))
            UserController.route(request, response, verifiedSessionId);
        else if(path.startsWith("/qna"))
            BoardController.route(request, response, verifiedSessionId);
        else
            HomeController.route(request, response, verifiedSessionId);

        if(response.getStatusCode() == null)
            ResourceController.route(request, response, verifiedSessionId);

        return response;
    }
}
