package controller;

import model.Request;
import model.Response;
import model.StatusCode;

public class HomeController {
    public static void route(Request request, Response response, String verifiedSessionId){


        String path = request.getPath();

        if (path.equals("/")) {
            response.setStatusCode(StatusCode.FOUND);
            response.addHeader("Location", "/index.html");
            return;
        }
    }
}
