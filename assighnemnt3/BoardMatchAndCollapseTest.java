package columns.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardMatchAndCollapseTest {

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
    void findMatchesDetectsVerticalTripletAndUpdatesScore() {
        board.newField[2][5] = 4;
        board.newField[2][6] = 4;
        board.newField[2][7] = 4;

        board.findMatches();

        assertFalse(board.noChanges);
        assertEquals(10, board.Score);
        assertEquals(1, board.figuresMatchedCounter);
        assertEquals(1, listener.triplets.size());
        assertEquals(0, board.oldField[2][5]);
        assertEquals(0, board.oldField[2][6]);
        assertEquals(0, board.oldField[2][7]);
    }

    @Test
    void findMatchesDetectsHorizontalTriplet() {
        board.newField[1][7] = 5;
        board.newField[2][7] = 5;
        board.newField[3][7] = 5;

        board.findMatches();

        assertFalse(board.noChanges);
        assertEquals(1, listener.triplets.size());
    }

    @Test
    void findMatchesDetectsDiagonalTriplet() {
        board.newField[1][5] = 6;
        board.newField[2][6] = 6;
        board.newField[3][7] = 6;

        board.findMatches();

        assertFalse(board.noChanges);
        assertEquals(1, listener.triplets.size());
    }

    @Test
    void findMatchesDoesNotRemoveOnlyTwoTouchingCells() {
        board.newField[2][5] = 4;
        board.newField[2][6] = 4;

        board.findMatches();

        assertTrue(board.noChanges);
        assertEquals(0, board.Score);
        assertEquals(0, listener.triplets.size());
        assertEquals(4, board.oldField[2][5]);
        assertEquals(4, board.oldField[2][6]);
    }

    @Test
    void collapsePacksRemainingCellsDownAndKeepsTheirOrder() {
        board.oldField[3][2] = 1;
        board.oldField[3][6] = 2;
        board.oldField[3][10] = 3;

        board.collapse();

        assertEquals(3, board.newField[3][GameConfig.DEPTH]);
        assertEquals(2, board.newField[3][GameConfig.DEPTH - 1]);
        assertEquals(1, board.newField[3][GameConfig.DEPTH - 2]);
        assertEquals(0, board.newField[3][GameConfig.DEPTH - 3]);
        assertEquals(1, listener.fieldUpdateCount);
    }

    @Test
    void collapseAfterMatchRemovesMatchedCellsFromField() {
        board.newField[2][5] = 4;
        board.newField[2][6] = 4;
        board.newField[2][7] = 4;

        board.findMatches();
        board.collapse();

        for (int row = 1; row <= GameConfig.DEPTH; row++) {
            assertEquals(0, board.newField[2][row]);
        }
        assertEquals(10, listener.lastScore);
    }
}
