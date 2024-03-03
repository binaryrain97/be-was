package model;

import java.time.LocalDateTime;

public class Board {
    private Long boardId;
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
    public void setBoardId(Long boardId) {this.boardId = boardId;}
    public Long getBoardId() {return this.boardId;}
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
