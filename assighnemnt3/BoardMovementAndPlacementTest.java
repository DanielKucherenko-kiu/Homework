package columns.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardMovementAndPlacementTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.initFields();
        board.setModelListener(new RecordingModelListener());
        board.initBoard();
        board.figure = new Figure(new FakeRandomGenerator(0, 1, 2));
    }

    @Test
    void initBoardClearsFieldScoreLevelAndMatchCounter() {
        board.newField[2][5] = 4;
        board.oldField[2][5] = 4;
        board.Score = 100;
        board.level = 3;
        board.figuresMatchedCounter = 10;

        board.initBoard();

        assertEquals(0, board.newField[2][5]);
        assertEquals(0, board.oldField[2][5]);
        assertEquals(0, board.Score);
        assertEquals(0, board.level);
        assertEquals(0, board.figuresMatchedCounter);
    }

    @Test
    void pasteFigureWritesThreeFigureColorsIntoOneColumn() {
        board.figure.x = 2;
        board.figure.y = 4;

        board.pasteFigure(board.figure);

        assertEquals(1, board.newField[2][4]);
        assertEquals(2, board.newField[2][5]);
        assertEquals(3, board.newField[2][6]);
    }

    @Test
    void dropFigureMovesFigureToLowestFreeRow() {
        board.figure.x = 2;
        board.figure.y = 1;

        board.dropFigure(board.figure);

        assertEquals(GameConfig.DEPTH - 2, board.figure.y);
        assertFalse(board.figureMayMoveDown());
    }

    @Test
    void dropFigureStopsAboveExistingBlockInSameColumn() {
        board.figure.x = 2;
        board.figure.y = 1;
        board.newField[2][12] = 6;
        board.newField[2][13] = 6;
        board.newField[2][14] = 6;
        board.newField[2][15] = 6;

        board.dropFigure(board.figure);

        assertEquals(9, board.figure.y);
    }

    @Test
    void canMoveLeftIsFalseAtLeftWall() {
        board.figure.x = 1;

        assertFalse(board.canMoveLeft());
    }

    @Test
    void canMoveLeftIsFalseWhenBottomLeftCellIsBlocked() {
        board.figure.x = 3;
        board.figure.y = 5;
        board.newField[2][7] = 5;

        assertFalse(board.canMoveLeft());
    }

    @Test
    void canMoveLeftIsTrueWhenInsideBoardAndTargetCellIsEmpty() {
        board.figure.x = 3;
        board.figure.y = 5;

        assertTrue(board.canMoveLeft());
    }

    @Test
    void canMoveRightIsFalseAtRightWall() {
        board.figure.x = GameConfig.WIDTH;

        assertFalse(board.canMoveRight());
    }

    @Test
    void canMoveRightIsFalseWhenBottomRightCellIsBlocked() {
        board.figure.x = 3;
        board.figure.y = 5;
        board.newField[4][7] = 5;

        assertFalse(board.canMoveRight());
    }

    @Test
    void canMoveRightIsTrueWhenInsideBoardAndTargetCellIsEmpty() {
        board.figure.x = 3;
        board.figure.y = 5;

        assertTrue(board.canMoveRight());
    }

    @Test
    void figureMayMoveDownIsFalseAtBottom() {
        board.figure.y = GameConfig.DEPTH - 2;

        assertFalse(board.figureMayMoveDown());
    }

    @Test
    void figureMayMoveDownIsFalseWhenCellBelowFigureIsBlocked() {
        board.figure.x = 3;
        board.figure.y = 5;
        board.newField[3][8] = 7;

        assertFalse(board.figureMayMoveDown());
    }
}
