
package src.test;

import src.domain.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class QuoridorTest {
    private Quoridor quoridor;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    // TEST CREATE BOARDS
    @Test
    public void shouldCreateQuoridorBoardAndInitializeThePeonsOddSize() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "4", "2", false,"Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", "beginner");
            assertEquals("Peon2", quoridor.getTypeOfField(0, 4));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldCreateQuoridorBoardAndInitializeThePeonsEvenSize() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            assertEquals("Peon2", quoridor.getTypeOfField(0, 2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    // TEST VALID MOVEMENTS
    @Test
    public void shouldReturnTheValidMovementsOfThePeon1InTheInitialPosition() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            String[]expectedResult = {"n", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.BLUE).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldReturnTheValidMovementsOfThePeon2InTheInitialPosition() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            String[]expectedResult = {"s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.RED).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveUpThePeon1() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            assertEquals("Peon1", quoridor.getTypeOfField(4, 2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveUpThePeon1AndGetTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            String[]expectedResult = {"n", "s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.BLUE).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveDownThePeon2() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.printBoard();
            quoridor.movePeon(Color.BLUE, "e");
            quoridor.printBoard();
            quoridor.movePeon(Color.red, "s");
            quoridor.printBoard();
            assertEquals("Peon2", quoridor.getTypeOfField(2, 2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveDownThePeon2AndGetTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "w");
            quoridor.movePeon(Color.red, "s");
            String[]expectedResult = {"n", "s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void sholdReturnTheValidMovementsofThePeonsInTheCorners() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "w");
            quoridor.movePeon(Color.red, "e");
            quoridor.movePeon(Color.BLUE, "w");
            String[]expectedResultPeon1 = {"n", "e"};
            assertArrayEquals(expectedResultPeon1, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
            quoridor.movePeon(Color.red, "e");
            String[]expectedResultPeon2 = {"s", "w"};
            assertArrayEquals(expectedResultPeon2, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon2ShouldRetunrTheValidMovementOfTheJumpUpThePeon1() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "n");
            String[]expectedResultPeon2 = {"n", "js", "w", "e"};
            assertArrayEquals(expectedResultPeon2, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));

        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon2ShouldReturnTheValidMovementOfTheJumpEastThePeon1() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "e");
            quoridor.movePeon(Color.BLUE, "n");
            String[]expectedResultPeon1 = {"n", "s", "w", "je"};
            assertArrayEquals(expectedResultPeon1, quoridor.getPeonValidMovements(Color.BLUE).toArray(new String[0]));
            String[]expectedResultPeon2 = {"n", "s", "jw", "e"};
            assertArrayEquals(expectedResultPeon2, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));

        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    // MOVE PEON
    @Test
    public void peon2ShouldJumpUpThePeon1() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "js");
            assertEquals("Peon2", quoridor.getTypeOfField(6, 4));

        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1ShouldRetunrTheValidMovementOfTheJumpUpThePeon2() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            String[]expectedResult = {"jn", "s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1ShouldJumpUpThePeon2() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            String[]expectedResult = {"jn", "s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    // BARRIER TEST

    @Test
    public void shouldAddANormalVerticalBarrier() {
        try{
            quoridor = new Quoridor("4", "1", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", "beginner");
            quoridor.addBarrier(Color.blue, 0, 1,  false,  "n");
            assertEquals("Normal", quoridor.getTypeOfField(0,1));
            assertEquals("Normal", quoridor.getTypeOfField(1,1));
            assertEquals("Normal", quoridor.getTypeOfField(2,1));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAddANormalVerticalBarrierAndValidateTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "1", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.addBarrier(Color.blue, 0, 1, false, "n");
            String[]expectedResult = {"s", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAddANormalHorizontalBarrier() {
        try{
            quoridor = new Quoridor("4", "4", "0", "0", "0", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.addBarrier(Color.blue, 1, 0, true,  "n");
            assertEquals("Normal", quoridor.getTypeOfField(1,0));
            assertEquals("Normal", quoridor.getTypeOfField(1,1));
            assertEquals("Normal", quoridor.getTypeOfField(1,2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAddANormalHorizontalBarrierAndValidateTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "4", "0", "0", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.addBarrier(Color.blue, 1, 0,true,  "n");
            String[]expectedResult = {"w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldReturnThatPeonHasAnExit() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            assertTrue(quoridor.peonsHasAnExit());
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAvoidAddABarrierThatTrapThePeon1() {
        try{
            quoridor = new Quoridor("3", "1", "1", "1", "1", "0",
                    "4", "2", false,  "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", "beginner");
            quoridor.addBarrier(Color.blue, 2,1,false,"n");
            quoridor.addBarrier(Color.red, 3, 2, true, "a");
            quoridor.addBarrier(Color.blue, 2, 3, false, "t");
            fail("Not threw the exception that peon has been trapped");
        }
        catch(QuoridorException e){
            assertEquals(QuoridorException.BARRIER_TRAP_PEON1, e.getMessage());
            assertTrue(quoridor.peonsHasAnExit());
        }
    }

    @Test
    public void shouldAvoidAddABarrierThatTrapThePeon2() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.addBarrier(Color.blue, 0,1,false,"n");
            quoridor.addBarrier(Color.red, 3, 2, true, "l");
            quoridor.addBarrier(Color.blue, 0, 5, false, "a");
            fail("Not threw the exception that peon has been trapped");
        }
        catch(QuoridorException e){
            assertEquals(QuoridorException.BARRIER_TRAP_PEON2, e.getMessage());
            assertTrue(quoridor.peonsHasAnExit());
        }
    }

    @Test
    public void shouldAvoidAddABarrierOnTheLastRow() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.addBarrier(Color.blue, 6,1,false,"n");
            assertEquals("Normal", quoridor.getTypeOfField(6,1));
            assertEquals("Normal", quoridor.getTypeOfField(5,1));
            assertEquals("Normal", quoridor.getTypeOfField(4,1));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAvoidAddABarrierOnTheLastColumn() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.addBarrier(Color.blue, 1,6,true,"n");
            assertEquals("Normal", quoridor.getTypeOfField(1,6));
            assertEquals("Normal", quoridor.getTypeOfField(1,5));
            assertEquals("Normal", quoridor.getTypeOfField(1,4));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    // TEST FOR SQUARES

    @Test
    public void peon1CantStepBack2Steps() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "jn");
            quoridor.movePeon(Color.red, "s");
            quoridor.setpBackPeon1(2);
            assertEquals("Peon1", quoridor.getTypeOfField(0, 2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1CanStepBack2Steps() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "4", "2", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30",  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "e");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.setpBackPeon1(2);
            assertEquals("Peon1", quoridor.getTypeOfField(6, 2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

}
