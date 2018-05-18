package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Main application class.
 */
public class Minesweeper {
    /** User interface. */
    private ConsoleUI userInterface;
    private static long startMillis;
    private static BestTimes bestTimes = new BestTimes();
    private static Minesweeper instance;
    private Settings setting;
    SORM sorm;

    private static final String URL = "jdbc:postgresql://localhost/minesweaper";
    private static final String USER = "postgres";
    private static final String PASSWORD = "32756deagl";

    /**
     * Constructor.
     */
    private Minesweeper() {
        try {
            sorm = new SORM(DriverManager.getConnection(URL, USER, PASSWORD));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        instance = this;
        userInterface = new ConsoleUI();
        setSetting(Settings.load());
        
        Field field = new Field(setting.getRowCount(), setting.getColumnCount(),setting.getMineCount());
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

    public Settings getSetting() {
        return setting;
    }

    public void setSetting(Settings setting) {
        this.setting = setting;
        setting.save();
    }

    public void saveBestTimesIntoDatabase() {
        try {
            for (BestTimes.PlayerTime pl : sorm.select(BestTimes.PlayerTime.class)) {
                bestTimes.addPlayerTime(pl.getName(), pl.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(bestTimes.toString());

//        for (int i = 0, )

        try {
            sorm.truncate(BestTimes.PlayerTime.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (BestTimes.PlayerTime p : bestTimes) {
            try {
                sorm.insert(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
