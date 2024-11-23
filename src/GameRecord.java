package src;

import java.util.ArrayList;
import java.util.Arrays;

public class GameRecord {
    private ArrayList<Input> inputs;
    private double duration;
    int[][] initialBoard;

    public GameRecord(ArrayList<Input> inputs, double duration, int[][] initialBoard) {
        this.inputs = inputs;
        this.duration = duration;
        this.initialBoard = initialBoard;
    }

    public ArrayList<Input> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<Input> inputs) {
        this.inputs = inputs;
    }

    public double getDuration() {
        return duration;
    }

    public void setInitialBoard(int[][] initialBoard) {
        this.initialBoard = initialBoard;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "inputs=" + inputs +
                ", duration=" + duration +
                ", initialBoard=" + Arrays.deepToString(initialBoard) +
                '}';
    }
}
