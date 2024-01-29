package service;

import db.BoardDatabase;
import db.UserDatabase;
import model.Board;
import model.BoardInfo;
import model.User;
import model.UserInfo;

public class BoardService {
    public static Board create(BoardInfo boardInfo) {
        try {
            Board board = new Board(boardInfo);
            Board created = BoardDatabase.addBoard(board);
            return created;
        }
        catch (Exception e) {
            return null;
        }
    }
}
