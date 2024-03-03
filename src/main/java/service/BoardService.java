package service;

import db.BoardDatabase;
import model.Board;
import model.BoardInfo;

import java.util.Map;

public class BoardService {
    public static Board create(Map<String, String> params) {
        BoardInfo boardInfo = new BoardInfo(params);
        if(!boardInfo.validateInfo()) return null;
        Board board = new Board(boardInfo);
        return BoardDatabase.addBoard(board);
    }
}
