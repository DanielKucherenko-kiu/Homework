package columns.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerEventTest {

    private FakePlatform platform;
    private GameController controller;

    @BeforeEach
    void setUp() {
        platform = new FakePlatform(new FakeRandomGenerator(0, 1, 2));
        controller = new GameController(platform);
        controller.board.initBoard();
        controller.board.figure = new Figure(new FakeRandomGenerator(0, 1, 2));
        controller.board.figure.x = 3;
        controller.board.figure.y = 5;
    }

    @Test
    void leftEventMovesCurrentFigureLeftWhenAllowed() {
        controller.processEvent(GameEvent.LEFT);

        assertEquals(2, controller.board.figure.x);
    }

    @Test
    void leftEventDoesNotMoveCurrentFigureThroughLeftWall() {
        controller.board.figure.x = 1;

        controller.processEvent(GameEvent.LEFT);

        assertEquals(1, controller.board.figure.x);
    }

    @Test
    void rightEventMovesCurrentFigureRightWhenAllowed() {
        controller.processEvent(GameEvent.RIGHT);

        assertEquals(4, controller.board.figure.x);
    }

    @Test
    void rightEventDoesNotMoveCurrentFigureThroughRightWall() {
        controller.board.figure.x = GameConfig.WIDTH;

        controller.processEvent(GameEvent.RIGHT);

        assertEquals(GameConfig.WIDTH, controller.board.figure.x);
    }

    @Test
    void upEventRotatesCurrentFigureUp() {
        controller.processEvent(GameEvent.UP);

        assertEquals(2, controller.board.figure.c[1]);
        assertEquals(3, controller.board.figure.c[2]);
        assertEquals(1, controller.board.figure.c[3]);
    }

    @Test
    void downEventRotatesCurrentFigureDown() {
        controller.processEvent(GameEvent.DOWN);

        assertEquals(3, controller.board.figure.c[1]);
        assertEquals(1, controller.board.figure.c[2]);
        assertEquals(2, controller.board.figure.c[3]);
    }

    @Test
    void dropEventMovesFigureToLowestPositionAndResetsTimer() {
        platform.setTc(1234);

        controller.processEvent(GameEvent.DROP);

        assertEquals(GameConfig.DEPTH - 2, controller.board.figure.y);
        assertFalse(controller.board.figureMayMoveDown());
        assertEquals(0, platform.getTc());
    }

    @Test
    void levelUpEventIncreasesLevelAndResetsMatchCounter() {
        controller.board.level = 2;
        controller.board.figuresMatchedCounter = 10;

        controller.processEvent(GameEvent.LEVEL_UP);

        assertEquals(3, controller.board.level);
        assertEquals(0, controller.board.figuresMatchedCounter);
    }

    @Test
    void levelUpEventDoesNotExceedMaximumLevel() {
        controller.board.level = GameConfig.MAX_LEVEL;

        controller.processEvent(GameEvent.LEVEL_UP);

        assertEquals(GameConfig.MAX_LEVEL, controller.board.level);
    }

    @Test
    void levelDownEventDecreasesLevelAndResetsMatchCounter() {
        controller.board.level = 3;
        controller.board.figuresMatchedCounter = 10;

        controller.processEvent(GameEvent.LEVEL_DOWN);

        assertEquals(2, controller.board.level);
        assertEquals(0, controller.board.figuresMatchedCounter);
    }

    @Test
    void levelDownEventDoesNotGoBelowZero() {
        controller.board.level = 0;

        controller.processEvent(GameEvent.LEVEL_DOWN);

        assertEquals(0, controller.board.level);
    }

    @Test
    void processEventClearsPressedKeyFlag() {
        platform.setEvent(GameEvent.RIGHT);

        controller.processEvent(platform.getEvent());

        assertFalse(platform.isKeyPressed());
    }
}
