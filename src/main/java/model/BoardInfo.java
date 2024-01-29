package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardInfo {
    private Map<String, String> info;
    public BoardInfo(HashMap<String, String> params) {
        this.info = new HashMap<>();
        this.info.put("writer", params.getOrDefault("writer", ""));
        this.info.put("title", params.getOrDefault("title", ""));
        this.info.put("content", params.getOrDefault("content", ""));
    }
    public Map<String, String> getInfo() {return this.info;}
    public void addInfo(String key, String value) {
        this.info.put(key, value);
    }
    public void deleteInfo(String key) {
        info.remove(key);
    }
    public String getWriter() {return this.info.get("writer");}
    public String getTitle() {return this.info.get("title");}
    public String getContent() {return this.info.get("content");}

    public boolean validateInfo(HashMap<String, String> params) {
        List<String> list = new ArrayList<>();
        list.add(params.getOrDefault("writer", ""));
        list.add(params.getOrDefault("title", ""));
        list.add(params.getOrDefault("content", ""));
        for(String info : list) {
            if(info.isEmpty()) return false;
        }
        return true;
    }
}
