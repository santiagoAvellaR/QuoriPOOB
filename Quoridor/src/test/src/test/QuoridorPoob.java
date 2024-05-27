package src.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.domain.Quoridor;
import src.domain.QuoridorException;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class QuoridorPoob {
    private Quoridor quoridor;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void shouldCreateBoardsOfDifferentSizes(){
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            assertEquals(7, quoridor.getBoardSize());
            quoridor = new Quoridor("15", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            assertEquals(29, quoridor.getBoardSize());
            quoridor = new Quoridor("9", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            assertEquals(17, quoridor.getBoardSize());
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
    @Test
    public void shouldAssignBarriersToPlayers(){
        try{
            quoridor = new Quoridor("9", "3", "3", "3", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            assertEquals(3, quoridor.getNumberBarrier(Color.BLUE, "n"));
            assertEquals(3, quoridor.getNumberBarrier(Color.BLUE, "t"));
            assertEquals(3, quoridor.getNumberBarrier(Color.BLUE, "l"));
            assertEquals(1, quoridor.getNumberBarrier(Color.BLUE, "a"));;
            assertEquals(3, quoridor.getNumberBarrier(Color.red, "n"));
            assertEquals(3, quoridor.getNumberBarrier(Color.red, "t"));
            assertEquals(3, quoridor.getNumberBarrier(Color.red, "l"));
            assertEquals(1, quoridor.getNumberBarrier(Color.red, "a"));;

        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
    @Test
    public void shouldMoveOrthogonallyAPawn(){
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            String[]expectedResult = {"n", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.BLUE).toArray(new String[0]));
            String[]expectedResult2 = {"s", "w", "e"};
            assertArrayEquals(expectedResult2, quoridor.getPeonValidMovements(Color.RED).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
    @Test
    public void shouldMoveDiagonallyAPawn(){
        try{ //northeast
            quoridor = new Quoridor("6", "4", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.addBarrier(Color.blue, 3,4,true,"n");
            quoridor.addBarrier(Color.red, 2,3,false,"n");
            String[] expectedValidMovements = new String[] {"ne", "s", "w", "e"};
            assertArrayEquals(expectedValidMovements, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
    @Test
    public void shouldPlaceANormalBarrier(){
        try{
            quoridor = new Quoridor("4", "1", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30, "beginner");
            quoridor.addBarrier(Color.blue, 0, 1,  false,  "n");
            assertEquals("Normal", quoridor.getTypeOfField(0,1));
            assertEquals("Normal", quoridor.getTypeOfField(1,1));
            assertEquals("Normal", quoridor.getTypeOfField(2,1));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
    @Test
    public void shouldMoveAPawnOverAPawn(){
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "n");
            String[]expectedResultPeon2 = {"n", "js", "w", "e"};
            assertArrayEquals(expectedResultPeon2, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));

        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
    @Test
    public void shouldNotMoveAPawnOverANonAlliedBarrier(){
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            assertEquals("Peon1", quoridor.getTypeOfField(6,2));
            quoridor.addBarrier(Color.blue, 1,1,true,"a");
            String[] expectedValidMovements = new String[] {"w", "e"};
            assertArrayEquals(expectedValidMovements, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
    @Test
    public void shouldMoveAPawnOverAnAlliedBarrier(){
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            assertEquals("Peon1", quoridor.getTypeOfField(6,2));
            quoridor.addBarrier(Color.blue, 5,1,true,"a");
            quoridor.addBarrier(Color.red, 3, 1, true,"n");
            quoridor.movePeon(Color.BLUE, "n");
            assertEquals("Peon1", quoridor.getTypeOfField(4,2));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
    @Test
    public void shouldKnowWhenSomeoneWonTheGame(){
        try{
            quoridor = new Quoridor("3", "2", "0", "0", "0", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");

            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "js");
            fail("Did not throw exception");
        }
        catch (QuoridorException e) {
            assertEquals(QuoridorException.PLAYER_TWO_WON,e.getMessage());
        }
    }
    @Test
    public void shouldKnowTheBarriersLeftForEachPlayer(){
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            assertEquals("Peon1", quoridor.getTypeOfField(6,2));
            quoridor.addBarrier(Color.blue, 1,1,true,"a");
            assertEquals(0,  quoridor.getNumberBarrier(Color.BLUE, "a"));
            assertEquals(1,  quoridor.getNumberBarrier(Color.red, "a"));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
    @Test
    public void shouldNotBlockThePassageOfAPlayer(){
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "CLASSIC",
                    "Player2", Color.red, "CLASSIC",
                    "NORMAL", 30,  "beginner");
            assertEquals("Peon1", quoridor.getTypeOfField(6,2));
            quoridor.addBarrier(Color.blue, 0,1,false,"a");
            quoridor.movePeon(Color.red, "e");
            quoridor.addBarrier(Color.blue, 0,5,false,"n");
            quoridor.movePeon(Color.red, "w");
            quoridor.addBarrier(Color.blue, 3,2,true,"n");
            assertEquals(0,  quoridor.getNumberBarrier(Color.BLUE, "a"));
            assertEquals(1,  quoridor.getNumberBarrier(Color.red, "a"));
            fail("Did not throw exception");
        } catch (QuoridorException e) {
            assertEquals(QuoridorException.BARRIER_TRAP_PEON2,e.getMessage());
        }
    }


}