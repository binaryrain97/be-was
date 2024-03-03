package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardInfo {
    private Map<String, String> info;
    public BoardInfo(Map<String, String> params) {
        this.info = new HashMap<>();
        this.info.put("writer", params.get("writer"));
        this.info.put("title", params.get("title"));
        this.info.put("content", params.get("content"));
    }
    public Map<String, String> getInfo() {return this.info;}
    public String getWriter() {return this.info.get("writer");}
    public String getTitle() {return this.info.get("title");}
    public String getContent() {return this.info.get("content");}

    public boolean validateInfo() {
        for(String key : info.keySet()) {
            String value = info.getOrDefault(key, "");
            if(value == null || value.isEmpty()) return false;
        }
        return true;
    }
}
