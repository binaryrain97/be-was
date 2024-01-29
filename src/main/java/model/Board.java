package model;

import java.time.LocalDateTime;

public class Board {
    private Long index;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdTime;

    public Board(BoardInfo boardInfo) {
        this.writer = boardInfo.getInfo().get("writer");
        this.title = boardInfo.getInfo().get("title");
        this.content = boardInfo.getInfo().get("name");
        this.createdTime = LocalDateTime.now();
    }
    public Board(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdTime = LocalDateTime.now();
    }
    public void setIndex(Long index) {this.index = index;}
    public Long getIndex() {return this.index;}
    public String getWriter() {return this.writer;}
    public String getTitle() {return this.title;}
    public String getContent() {return this.content;}
    public LocalDateTime getCreatedTime() {return this.createdTime;}
    @Override
    public String toString() {
        String writer = this.writer;
        String title = this.title;
        String content = this.content;
        return "Board [writer=" + writer + ", title=" + title + ", content=" + content + "]";
    }

}
