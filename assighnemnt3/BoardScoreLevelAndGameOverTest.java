package columns.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardScoreLevelAndGameOverTest {

    private Board board;
    private RecordingModelListener listener;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.initFields();
        listener = new RecordingModelListener();
        board.setModelListener(listener);
        board.initBoard();
    }

    @Test
    void scoreUsesCurrentLevelWhenTripletIsDetected() {
        board.level = 2;
        board.newField[1][7] = 3;
        board.newField[2][7] = 3;
        board.newField[3][7] = 3;

        board.findMatches();

        assertEquals(30, board.Score);
    }

    @Test
    void collapseAddsDropScoreAndNotifiesScoreListener() {
        board.DScore = 15;

        board.collapse();

        assertEquals(15, board.Score);
        assertEquals(15, listener.lastScore);
        assertEquals(1, listener.scoreUpdateCount);
    }

    @Test
    void levelIncreasesWhenEnoughFiguresHaveMatched() {
        board.figuresMatchedCounter = GameConfig.NEXT_LEVEL_THRESHOLD;

        board.changeLevelIfNeeded();

        assertEquals(1, board.level);
        assertEquals(0, board.figuresMatchedCounter);
        assertEquals(1, listener.levelChangeCount);
        assertEquals(1, listener.lastLevel);
    }

    @Test
    void levelDoesNotIncreasePastMaximum() {
        board.level = GameConfig.MAX_LEVEL;
        board.figuresMatchedCounter = GameConfig.NEXT_LEVEL_THRESHOLD;

        board.changeLevelIfNeeded();

        assertEquals(GameConfig.MAX_LEVEL, board.level);
        assertEquals(0, board.figuresMatchedCounter);
    }

    @Test
    void fieldIsFullWhenRowThreeContainsAnyBlock() {
        board.newField[4][3] = 7;

        assertTrue(board.isFieldFull());
    }

    @Test
    void fieldIsNotFullWhenRowThreeIsEmpty() {
        board.newField[4][4] = 7;

        assertFalse(board.isFieldFull());
    }
}
