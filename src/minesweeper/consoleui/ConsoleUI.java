package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.UserInterface;
import minesweeper.core.*;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /** Playing field. */
    private Field field;
    
    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Reads line of text from the reader.
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Starts the game.
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        System.out.println("Welcome sir or madam "+System.getProperty("user.name")+"\n");
        do {
            update();
            processInput();
            if(field.getState() == GameState.SOLVED) {
                update();
                System.out.println("Solved");
                System.exit(0);
            } else if(field.getState() == GameState.FAILED) {
                update();
                System.out.println("Failed");
                System.exit(0);
            }
        } while(true);
    }
    
    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        char[] rows = {' ','A','B','C','D','E','F','G','H','I'};
        for(int i = 0; i <= field.getRowCount(); i++){
            if(i == 1) {
                System.out.println();
            }
            System.out.print(rows[i]+"  ");
            for(int j = 0; j < field.getColumnCount(); j++){
                if(i == 0) {
                    System.out.print(" " + j + " ");
                } else {
                    switch(field.getTile(i-1, j).getState()){
                        case CLOSED:
                            System.out.print(" _ ");
                            break;
                        case OPEN:
                            if(field.getTile(i-1, j) instanceof Mine){
                                System.out.print(" X ");
                            } else {
                                System.out.print(" "+((Clue) field.getTile(i-1, j)).getValue()+" ");
                            }
                            break;
                        case MARKED:
                            System.out.print(" M ");
                            break;
                    }
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        boolean inNok = true;
        while(inNok) {
            System.out.println();
            System.out.println("Zadaj prikaz alebo \"H\" ak nepoznas prikazy");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            Pattern p = Pattern.compile("(?i)([X])?(/help)?(([OM])([A-I])([0-8]))?");
            Matcher m = p.matcher(input.toLowerCase());
            if(m.matches()) {
                inNok = false;
                switch(m.group(0).charAt(0)) {
                    case 'x':
                        System.exit(0);
                        break;
                    case 'h':
                        System.out.println(
                                "X - ukonci" + "\n" +
                                "MXY - oznac pole" + "\n" +
                                "OXY - otvor pole" + "\n" +
                                "X -> A-I, Y -> 0-8 (suradnice)");
                        inNok = true;
                        break;
                    case 'm':
                        int rowM = m.group(0).charAt(1);
                        int colM = m.group(0).charAt(2);
                        field.markTile(rowM-97,colM-48);
                        break;
                    case 'o':
                        int rowO = m.group(0).charAt(1);
                        int colO = m.group(0).charAt(2);
                        field.openTile(rowO-97,colO-48);
                        break;
                }
            }
        }
    }
}
