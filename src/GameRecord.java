package src;

import java.util.ArrayList;
import java.util.Arrays;

public class GameRecord {
    private Input[] inputs;
    private long duration;
    int[][] initialBoard;

    /**
     * Create new game record instance
     * @param inputs inputs to the game
     * @param duration number of milliseconds the game lasted
     * @param initialBoard initial configuration of board
     */
    public GameRecord(ArrayList<Input> inputs, long duration, int[][] initialBoard) {
        this.inputs = inputs.toArray(new Input[inputs.size()]);
        this.duration = duration;
        this.initialBoard = initialBoard;
    }

    public Input[] getInputs() {
        return inputs;
    }

    public double getDuration() {
        return duration;
    }

    public int getNumInputs() {
        return inputs.length;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "inputs=" + Arrays.toString(inputs) +
                ", duration=" + duration +
                ", initialBoard=" + Arrays.deepToString(initialBoard) +
                '}';
    }
}
