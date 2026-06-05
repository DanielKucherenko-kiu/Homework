package columns.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FigureTest {

    @Test
    void constructorUsesDeterministicRandomColors() {
        Figure figure = new Figure(new FakeRandomGenerator(0, 1, 2));

        assertEquals(1, figure.c[1]);
        assertEquals(2, figure.c[2]);
        assertEquals(3, figure.c[3]);
    }

    @Test
    void moveLeftDecreasesColumnByOne() {
        Figure figure = new Figure(new FakeRandomGenerator(0, 1, 2));
        int initialX = figure.x;

        figure.moveLeft();

        assertEquals(initialX - 1, figure.x);
    }

    @Test
    void moveRightIncreasesColumnByOne() {
        Figure figure = new Figure(new FakeRandomGenerator(0, 1, 2));
        int initialX = figure.x;

        figure.moveRight();

        assertEquals(initialX + 1, figure.x);
    }

    @Test
    void moveDownIncreasesRowByOne() {
        Figure figure = new Figure(new FakeRandomGenerator(0, 1, 2));
        int initialY = figure.y;

        figure.moveDown();

        assertEquals(initialY + 1, figure.y);
    }

    @Test
    void rotateUpMovesTopColorToBottom() {
        Figure figure = new Figure(new FakeRandomGenerator(0, 1, 2));

        figure.rotateUp();

        assertEquals(2, figure.c[1]);
        assertEquals(3, figure.c[2]);
        assertEquals(1, figure.c[3]);
    }

    @Test
    void rotateDownMovesBottomColorToTop() {
        Figure figure = new Figure(new FakeRandomGenerator(0, 1, 2));

        figure.rotateDown();

        assertEquals(3, figure.c[1]);
        assertEquals(1, figure.c[2]);
        assertEquals(2, figure.c[3]);
    }
}
