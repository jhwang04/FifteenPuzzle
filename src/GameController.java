package src;

import javafx.scene.input.KeyCode;

public class GameController {
    private GameState gameState = GameState.WAITING;
    private int[][] game = new int[4][4];
    private int numMoves = 0;
    private long startMillis = 0;
    private long lastTimeMillis = 0;

    private GameRecorder gameRecorder = new GameRecorder();

    public void resetGame() {
        gameState = GameState.WAITING;
        numMoves = 0;

        // Creates a solved board
        for (int r = 0; r < 4; ++r) {
            for (int c = 0; c < 4; ++c) {
                game[r][c] = r * 4 + c + 1;
            }
        }
        game[3][3] = 0;

        // Scrambles the board. Only 81 are necessary, but some "moves" don't do anything because they're at the wrong edge.
        int lastDir = 0;
        for (int i = 0; i < 5000; ++i) {
            int rand = (int) (Math.random() * 4);
            while (rand == lastDir) {
                rand = (int) (Math.random() * 4);
            }
            lastDir = rand;

            if (rand == 0) {
                moveUp();
            } else if (rand == 1) {
                moveDown();
            } else if (rand == 2) {
                moveRight();
            } else {
                moveLeft();
            }
        }

        // Start new game recorder
        gameRecorder.newGame(game);
    }

    private boolean checkIfWin() {
        for (int r = 0; r < 4; ++r) {
            for (int c = 0; c < 4; ++c) {
                if (game[r][c] != 4 * r + c + 1 && !(r == 3 && c == 3)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void startTimer() {
        if (gameState == GameState.WAITING) {
            startMillis = System.currentTimeMillis();
            gameState = GameState.STARTED;
        }
    }

    public void enterKey(KeyCode key) {
        switch (key) {
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

        if (checkIfWin()) {
            gameState = GameState.COMPLETE;
            System.out.println(gameRecorder.getGameRecord(0.0));
        }

        lastTimeMillis = gameState == GameState.WAITING ? 0 : System.currentTimeMillis() - startMillis;
        gameRecorder.enterMove(key);
        numMoves++;
    }

    public int[][] getGame() {
        return game;
    }

    public int getNumMoves() {
        return numMoves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public long getLastTimeMillis() {
        return lastTimeMillis;
    }

    private void moveUp() {
        for (int r = 0; r < 3; ++r) { // can't move up if the space is at bottom
            for (int c = 0; c < 4; ++c) {
                if (game[r][c] == 0) {
                    int temp = game[r][c];
                    game[r][c] = game[r + 1][c];
                    game[r + 1][c] = temp;
                    return;
                }
            }
        }
    }

    private void moveDown() {
        for (int r = 1; r < 4; ++r) { // can't move down if the space is at top
            for (int c = 0; c < 4; ++c) {
                if (game[r][c] == 0) {
                    int temp = game[r][c];
                    game[r][c] = game[r - 1][c];
                    game[r - 1][c] = temp;
                    return;
                }
            }
        }
    }

    private void moveRight() {
        for (int r = 0; r < 4; ++r) { // can't move right if the space is at left
            for (int c = 1; c < 4; ++c) {
                if (game[r][c] == 0) {
                    int temp = game[r][c];
                    game[r][c] = game[r][c - 1];
                    game[r][c - 1] = temp;
                    return;
                }
            }
        }
    }

    private void moveLeft() {
        for (int r = 0; r < 4; ++r) { // can't move left if space is at right
            for (int c = 0; c < 3; ++c) {
                if (game[r][c] == 0) {
                    int temp = game[r][c];
                    game[r][c] = game[r][c + 1];
                    game[r][c + 1] = temp;
                    return;
                }
            }
        }
    }
}
