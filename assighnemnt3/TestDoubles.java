package columns.model;

import columns.model.kernel.ModelListener;
import columns.model.kernel.Platform;
import columns.model.kernel.RandomGenerator;
import columns.model.kernel.Screen;

import java.util.ArrayList;
import java.util.List;

final class FakeRandomGenerator implements RandomGenerator {
    private final int[] values;
    private int index;

    FakeRandomGenerator(int... values) {
        this.values = values.length == 0 ? new int[] {0} : values;
    }

    @Override
    public int nextInt() {
        int value = values[index % values.length];
        index++;
        return value;
    }
}

final class RecordingModelListener implements ModelListener {
    int lastLevel = -1;
    long lastScore = -1;
    int levelChangeCount;
    int scoreUpdateCount;
    int fieldUpdateCount;
    final List<String> triplets = new ArrayList<>();

    @Override
    public void levelHasChanged(int level) {
        lastLevel = level;
        levelChangeCount++;
    }

    @Override
    public void tripletDetected(int a, int b, int c, int d, int i, int j) {
        triplets.add(a + "," + b + ";" + c + "," + d + ";" + i + "," + j);
    }

    @Override
    public void fieldWasUpdated(int[][] newField) {
        fieldUpdateCount++;
    }

    @Override
    public void scoreUpdated(long score) {
        lastScore = score;
        scoreUpdateCount++;
    }
}

final class FakeScreen implements Screen {
    final List<String> calls = new ArrayList<>();

    @Override
    public void setColor(int color) {
        calls.add("setColor:" + color);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        calls.add("fillRect");
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        calls.add("drawRect");
    }

    @Override
    public void drawString(String string, int x, int y) {
        calls.add("drawString:" + string);
    }

    @Override
    public void clearRect(int x, int y, int width, int height) {
        calls.add("clearRect");
    }

    @Override
    public int Black() {
        return 0;
    }

    @Override
    public int White() {
        return 8;
    }
}

final class FakePlatform implements Platform {
    private final FakeScreen screen = new FakeScreen();
    private final RandomGenerator randomGenerator;
    private long currentTime;
    private long tc;
    private boolean keyPressed;
    private GameEvent event = GameEvent.NONE;
    long lastDelay = -1;

    FakePlatform(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    FakeScreen screen() {
        return screen;
    }

    void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    void setEvent(GameEvent event) {
        this.event = event;
        this.keyPressed = true;
    }

    @Override
    public void delay(long t) {
        lastDelay = t;
    }

    @Override
    public long currentTime() {
        return currentTime;
    }

    @Override
    public boolean isKeyPressed() {
        return keyPressed;
    }

    @Override
    public void setKeyPressed(boolean isKeyPressed) {
        this.keyPressed = isKeyPressed;
    }

    @Override
    public Screen getScreen() {
        return screen;
    }

    @Override
    public long getTc() {
        return tc;
    }

    @Override
    public void setTc(long time) {
        this.tc = time;
    }

    @Override
    public int getKeyPressed() {
        return 0;
    }

    @Override
    public GameEvent getEvent() {
        return event;
    }

    @Override
    public RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }
}
