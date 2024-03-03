package db;

import com.google.common.collect.Maps;
import model.Board;

import java.util.Collection;
import java.util.Map;

public class BoardDatabase {
    private static Map<Long, Board> boards = Maps.newHashMap();
    private static Long boardCount = 0L;

    public static Board addBoard(Board board) {
        Long index = ++boardCount;
        board.setBoardId(index);
        boards.put(index, board);
        return board;
    }

    public static Board findByIndex(Long index) {return boards.get(index);}

    public static Collection<Board> findAll() {
        return boards.values();
    }
}
