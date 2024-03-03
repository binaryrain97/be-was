package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);
    private static final HashSet<String> printedKey =  // logger.debug로 출력할 헤더값들
            new HashSet<>(Arrays.asList("accept", "cookie")); // etc. User-Agent, Host
    private String method, path, version;
    private HashMap<String, String> header, param, body;
    private String mimeType, sessionId;

    public String getMethod() {return this.method;}
    public String getVersion() {return this.version;}
    public String getPath() {return this.path;}
    public HashMap<String, String> getHeader() {return this.header;}
    public HashMap<String, String> getParam() {return this.param;}
    public HashMap<String, String> getBody() {return this.body;}
    public String getMimeType() {return this.mimeType;}
    public String getSessionId() {return this.sessionId;}

    public Request(BufferedReader br) throws IOException {
        readStartLine(br);
        readHeader(br);
        readBody(br);
    }
    private void readStartLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        parseStartLine(line);
        logger.debug("HTTP start line = " + line);
    }
    private void readHeader(BufferedReader br) throws IOException {
        String line;
        this.header = new HashMap<>();
        while(true) {
            line = br.readLine();
            if(line == null || line.isEmpty()) break;
            int indexOfDelimiter = line.indexOf(":");
            String key = line.substring(0, indexOfDelimiter).trim().toLowerCase();
            String value = line.substring(indexOfDelimiter+1).trim();
            this.header.put(key, value);
            if(printedKey.contains(key))
                logger.debug(line);
        }
        setMimeType();
        setSessionId();
    }
    private void readBody(BufferedReader br) throws IOException {
        int contentLength = Integer.parseInt(header.getOrDefault("content-length", "0"));
        if(contentLength == 0) return;
        this.body = new HashMap<>();
        String contentType = header.get("content-type");
        char[] body = new char[contentLength];
        br.read(body);

        if("application/x-www-form-urlencoded".equals(contentType)) {
            HashMap<String, String> hashMap = Util.parseQueryString(new String(body));
            this.body = hashMap;
        }
        else if("application/json".equals(contentType)) {
            HashMap<String, String> hashMap = Util.parseStringJson(new String(body));
            this.body = hashMap;
        }
    }
    private void setMimeType() {
        String accept = this.header.get("accept");
        if(accept == null) return;
        String[] types = accept.split(",");
        this.mimeType = types[0].trim();
    }
    public void parseStartLine(String line) {
        String[] tokens = line.split(" ");
        if(tokens.length != 3) throw new RuntimeException("HTTP start line error");
        this.method = tokens[0];
        parsePath(tokens[1]);
        this.version = tokens[2];
    }
    private void parsePath(String path) {
        int indexOfDelimiter = path.indexOf("?");
        if(indexOfDelimiter == -1) {
            this.path = path;
            return;
        }
        this.path = path.substring(0, indexOfDelimiter);
        String queryString = path.substring(indexOfDelimiter+1);
        parseParameter(queryString);
    }
    private void parseParameter(String queryString) {
        param = new HashMap<>();
        if(!queryString.isEmpty())
            this.param = Util.parseQueryString(queryString);
    }
    public void setSessionId() {
        String cookies = this.header.get("cookie");
        if(cookies == null) return;
        Map<String, String> map = Util.parseSemicolon(cookies);
        this.sessionId = map.get("sessionId");
    }
    @Override
    public String toString() {
        return "[method= " + method + ", target= " + path + ", version= " + version + "]";
    }
}