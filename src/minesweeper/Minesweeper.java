package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
    /** User interface. */
    private ConsoleUI userInterface;
    private static long startMillis;
    private static BestTimes bestTimes = new BestTimes();
    private static Minesweeper instance;
 
    /**
     * Constructor.
     */
    private Minesweeper() {
        instance = this;
        userInterface = new ConsoleUI();
        
        Field field = new Field(9, 9, 1);
        startMillis = System.currentTimeMillis();
        userInterface.newGameStarted(field);
    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        new Minesweeper();
    }

    public static BestTimes getBestTimes() {
        return bestTimes;
    }

    public static void setBestTimes(BestTimes bestTimes) {
        Minesweeper.bestTimes = bestTimes;
    }

    public int getPlayingSeconds() {
        return (int) ((System.currentTimeMillis() - startMillis) / 1000);
    }

    public static Minesweeper getInstance() {
        return instance;
    }
}
