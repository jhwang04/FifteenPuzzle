package src;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class GameRecorder {
    private ArrayList<Input> currentGame;
    private int[][] initialBoard;

    public void newGame(int[][] game) {
        currentGame = new ArrayList<>();
        initialBoard = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                initialBoard[i][j] = game[i][j];
            }
        }
    }

    public void moveUp() {
        if (currentGame == null) {
            throw new RuntimeException("Moving board without having initialized game");
        }
        currentGame.add(Input.UP);
    }

    public void moveDown() {
        if (currentGame == null) {
            throw new RuntimeException("Moving board without having initialized game");
        }
        currentGame.add(Input.DOWN);
    }

    public void moveRight() {
        if (currentGame == null) {
            throw new RuntimeException("Moving board without having initialized game");
        }
        currentGame.add(Input.RIGHT);
    }

    public void moveLeft() {
        if (currentGame == null) {
            throw new RuntimeException("Moving board without having initialized game");
        }
        currentGame.add(Input.LEFT);
    }

    public void enterMove(KeyCode code) {
        switch(code) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    }

    public GameRecord getGameRecord(double duration) {
        return new GameRecord(currentGame, duration, initialBoard);
    }
}
