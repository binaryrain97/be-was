package controller;

import db.BoardDatabase;
import db.UserDatabase;
import db.SessionStorage;
import model.*;
import util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ResourceController {

    public static void route(Request request, Response response, String verifiedSessionId) {
        String path = request.getPath();
        String filePath;

        if(request.getMimeType().equals("text/html"))
            filePath = "./src/main/resources/templates" + path;
        else
            filePath = "./src/main/resources/static" + path;
        File file = new File(filePath);

        if(!file.exists()) {
            response.setStatusCode(StatusCode.FOUND);
            response.addHeader("Location", "/error404.html");
            return;
        }

        if(request.getMimeType().equals("text/html")) {
            HashMap<String, String> replace = new HashMap<>();
            StringBuilder stringBuilder = new StringBuilder();

            if(verifiedSessionId != null) {
                replace.put("{{login}}",
                        "<li><a>" + SessionStorage.findBySessionId(verifiedSessionId).getUserId() + "</a></li>" +
                        "<li><a href=\"/user/logout\" role=\"button\">로그아웃</a></li>\n" +
                        "<li><a href=\"/user/modify\" role=\"button\">개인정보수정</a></li>");
            }
            else {
                replace.put("{{login}}",
                        "<li><a href=\"/user/login.html\" role=\"button\">로그인</a></li>\n" +
                        "<li><a href=\"/user/form.html\" role=\"button\">회원가입</a></li>");
            }
            if(path.equals("/user/list.html")) {
                replace.put("{{userList}}", makeUserList());
            }
            if(path.equals("/index.html")) {
                replace.put("{{boardList}}", makeBoardList());
            }

            String html = Util.readFile(stringBuilder, filePath, replace);
            byte[] data = html.getBytes();

            response.setStatusCode(StatusCode.OK);
            response.setBody(data);
            response.addHeader("Content-Type", "text/html;charset=utf-8");
            response.addHeader("Content-Length", String.valueOf(data.length));
        }
        else {
            byte[] data = new byte[(int) file.length()];

            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                int bytesRead = fileInputStream.read(data);
                if (bytesRead != file.length()) {
                    throw new IOException("Could not read the entire file");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            response.setStatusCode(StatusCode.OK);
            response.setBody(data);
            response.addHeader("Content-Type", request.getMimeType() + ";charset=utf-8");
            response.addHeader("Content-Length", String.valueOf(data.length));
        }
    }

    private static String makeUserList() {
        Collection<User> collection = UserDatabase.findAll();
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for(User user : collection) {
            sb.append("<tr><th scope=\"row\">");
            sb.append(String.valueOf(++idx));
            sb.append("</th> <td>");
            sb.append(user.getUserId());
            sb.append("</td> <td>");
            sb.append(user.getName());
            sb.append("</td> <td>");
            sb.append(user.getEmail());
            sb.append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
        }
        return sb.toString();
    }
    private static String makeBoardList() {
        Collection<Board> collection = BoardDatabase.findAll();
        List<Board> boardList = new ArrayList<>(collection);
        Collections.sort(boardList, (o1, o2) -> {
            return Math.toIntExact(o2.getBoardId() - o1.getBoardId());
        });
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for(Board board : boardList) {
            sb.append("<li><div class=\"wrap\"><div class=\"main\"><strong class=\"subject\"><a href=\"/qna/show.html?index=");
            sb.append(String.valueOf(board.getBoardId()));
            sb.append("\">");
            sb.append(board.getTitle());
            sb.append("</a></strong><div class=\"auth-info\"><i class=\"icon-add-comment\"></i>");
            sb.append("<span class=\"time\">");
            sb.append(Util.convertLocalDateTime(board.getCreatedTime()) + "  ");
            sb.append("</span><a href=\"/user/profile.html\" class=\"author\">");
            sb.append(board.getWriter());
            sb.append("</a></div><div class=\"reply\" title=\"댓글\"><i class=\"icon-reply\"></i>");
            sb.append("<span class=\"point\">");
            sb.append(String.valueOf(board.getBoardId()));
            sb.append("</span></div></div></div></li>");
        }
        return sb.toString();
    }
}
