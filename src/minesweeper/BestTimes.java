package minesweeper;

import java.util.*;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
    /** List of best player times. */
    private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

    /**
     * Returns an iterator over a set of  best times.
     * @return an iterator
     */
    public Iterator<PlayerTime> iterator() {
        return playerTimes.iterator();
    }

    /**
     * Adds player time to best times.
     * @param name name ot the player
     * @param time player time in seconds
     */
    public void addPlayerTime(String name, int time) {
        PlayerTime plTim = new PlayerTime(name, time);
        playerTimes.add(plTim);
    }

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    public String toString() {
        Formatter f = new Formatter();
        for (int i = 0; i < playerTimes.size(); i++) {
            f.format("%d %d %s", i+playerTimes.get(i).getTime()+playerTimes.get(i).getName());
        }
        return f.toString();
    }

    void reset() {
        playerTimes.clear();
    }


    /**
     * Player time.
     */
    public static class PlayerTime {
        private final String name;

        private final int time;

        /**
         * Constructor.
         * @param name player name
         * @param time playing game time in seconds
         */
        public PlayerTime(String name, int time) {
            this.name = name;
            this.time = time;
        }

        /** Player name. */
        public String getName() {
            return name;
        }

        /** Playing time in seconds. */
        public int getTime() {
            return time;
        }
    }
}