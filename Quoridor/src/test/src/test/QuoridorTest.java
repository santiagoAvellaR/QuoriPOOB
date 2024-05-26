
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

    // ----------------------------      TEST CREATE BOARDS          ----------------------------------------------------
    @Test
    public void shouldCreateQuoridorBoardAndInitializeThePeonsOddSize() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "0", "0", false,"Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30, "beginner");
            assertEquals("Peon2", quoridor.getTypeOfField(0, 4));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldCreateQuoridorBoardAndInitializeThePeonsEvenSize() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            assertEquals("Peon2", quoridor.getTypeOfField(0, 2));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    // ----------------------------------------------     TEST VALID MOVEMENTS        ----------------------------------------------
    @Test
    public void shouldReturnTheValidMovementsOfThePeon1InTheInitialPosition() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            String[]expectedResult = {"n", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.BLUE).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldReturnTheValidMovementsOfThePeon2InTheInitialPosition() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            String[]expectedResult = {"s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.RED).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveUpThePeon1() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            assertEquals("Peon1", quoridor.getTypeOfField(4, 2));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveUpThePeon1AndGetTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            String[]expectedResult = {"n", "s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.BLUE).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveDownThePeon2() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.printBoard();
            quoridor.movePeon(Color.BLUE, "e");
            quoridor.printBoard();
            quoridor.movePeon(Color.red, "s");
            quoridor.printBoard();
            assertEquals("Peon2", quoridor.getTypeOfField(2, 2));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveDownThePeon2AndGetTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "w");
            quoridor.movePeon(Color.red, "s");
            String[]expectedResult = {"n", "s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void sholdReturnTheValidMovementsofThePeonsInTheCorners() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "w");
            quoridor.movePeon(Color.red, "e");
            quoridor.movePeon(Color.BLUE, "w");
            String[]expectedResultPeon1 = {"n", "e"};
            assertArrayEquals(expectedResultPeon1, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
            quoridor.movePeon(Color.red, "e");
            String[]expectedResultPeon2 = {"s", "w"};
            assertArrayEquals(expectedResultPeon2, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon2ShouldRetunrTheValidMovementOfTheJumpUpThePeon1() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
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
    public void peon2ShouldReturnTheValidMovementOfTheJumpEastThePeon1() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
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
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1ShouldNotPassThroughAlliedBarrierOfPeon2() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
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
    public void peon1ShouldValidateDiagonalMovementsAtNorthEast() {
        try{
            quoridor = new Quoridor("6", "4", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
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
    public void peon1ShouldValidateDiagonalMovementsAtNorthWest() {
        try{
            quoridor = new Quoridor("6", "4", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.addBarrier(Color.blue, 3,2,true,"n");
            quoridor.addBarrier(Color.red, 2,5,false,"n");
            String[] expectedValidMovements = new String[] {"nw", "s", "w", "e"};
            assertArrayEquals(expectedValidMovements, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1ShouldValidateDiagonalMovementsAtNorthWestAndNorthEast() {
        try{
            quoridor = new Quoridor("6", "4", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.addBarrier(Color.blue, 3,2,true,"n");
            String[] expectedValidMovements = new String[] {"nw", "ne", "s", "w", "e"};
            assertArrayEquals(expectedValidMovements, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    // ----------------------------------------        MOVE PEON          ---------------------------------------------------------------------
    @Test
    public void peon2ShouldJumpUpThePeon1() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "js");
            assertEquals("Peon2", quoridor.getTypeOfField(6, 4));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1ShouldRetunrTheValidMovementOfTheJumpUpThePeon2() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            String[]expectedResult = {"jn", "s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1ShouldJumpUpThePeon2() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            String[]expectedResult = {"jn", "s", "w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    // ----------------------------------------     BARRIER TEST       ----------------------------------------------------

    @Test
    public void shouldAddANormalVerticalBarrier() {
        try{
            quoridor = new Quoridor("4", "1", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
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
    public void shouldAddANormalVerticalBarrierAndValidateTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "1", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addBarrier(Color.blue, 0, 1, false, "n");
            String[]expectedResult = {"s", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAddANormalHorizontalBarrier() {
        try{
            quoridor = new Quoridor("4", "4", "0", "0", "0", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addBarrier(Color.blue, 1, 0, true,  "n");
            assertEquals("Normal", quoridor.getTypeOfField(1,0));
            assertEquals("Normal", quoridor.getTypeOfField(1,1));
            assertEquals("Normal", quoridor.getTypeOfField(1,2));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAddANormalHorizontalBarrierAndValidateTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "4", "0", "0", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addBarrier(Color.blue, 1, 0,true,  "n");
            String[]expectedResult = {"w", "e"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldReturnThatPeonHasAnExit() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
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
                    "0", "0", false,  "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30, "beginner");
            quoridor.addBarrier(Color.blue, 2,1,false,"n");
            quoridor.addBarrier(Color.red, 3, 2, true, "a");
            quoridor.addBarrier(Color.blue, 2, 3, false, "t");
            fail("Not threw the exception that peon has been trapped");
        }
        catch(Exception e){
            assertEquals(QuoridorException.BARRIER_TRAP_PEON1, e.getMessage());
            assertTrue(quoridor.peonsHasAnExit());
        }
    }

    @Test
    public void shouldAvoidAddABarrierThatTrapThePeon2() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addBarrier(Color.blue, 0,1,false,"n");
            quoridor.addBarrier(Color.red, 3, 2, true, "l");
            quoridor.addBarrier(Color.blue, 0, 5, false, "a");
            fail("Not threw the exception that peon has been trapped");
        }
        catch(Exception e){
            assertEquals(QuoridorException.BARRIER_TRAP_PEON2, e.getMessage());
            assertTrue(quoridor.peonsHasAnExit());
        }
    }

    @Test
    public void shouldAddABarrierOnTheLastRow() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addBarrier(Color.blue, 6,1,false,"n");
            assertEquals("Normal", quoridor.getTypeOfField(6,1));
            assertEquals("Normal", quoridor.getTypeOfField(5,1));
            assertEquals("Normal", quoridor.getTypeOfField(4,1));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAddABarrierOnTheLastColumn() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addBarrier(Color.blue, 1,6,true,"n");
            assertEquals("Normal", quoridor.getTypeOfField(1,6));
            assertEquals("Normal", quoridor.getTypeOfField(1,5));
            assertEquals("Normal", quoridor.getTypeOfField(1,4));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1ShouldPassThroughAlliedBarrier() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
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
    public void temporaryShouldAlertToEraseIt() {
        try{
            quoridor = new Quoridor("4", "2", "1", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addBarrier(Color.blue, 5,1,true,"t");
            quoridor.addBarrier(Color.red, 3, 1, true,"n");
            quoridor.movePeon(Color.BLUE, "e");
            quoridor.movePeon(Color.red, "s");
            assertEquals("Peon1", quoridor.getTypeOfField(4,2));
            fail("did not threw the exception of temporary");
        }
        catch(Exception e){
            assertEquals(QuoridorException.ERASE_TEMPORARY_BARRIER, e.getMessage());
        }
    }

    // -------------------------------------     TEST FOR SQUARES      -----------------------------------------------------------

    @Test
    public void peon1CanStepBack2Steps1() {
        try{
            quoridor = new Quoridor("5", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.stepBackPeon1(2);
            assertEquals("Peon1", quoridor.getTypeOfField(8, 4));
        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1CanStepBack2Steps2() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "e");
            quoridor.movePeon(Color.BLUE, "n");
            quoridor.movePeon(Color.red, "s");
            quoridor.stepBackPeon1(2);
            assertEquals("Peon1", quoridor.getTypeOfField(6, 2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void rewindShouldStepBack2StepsPeon1() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addRewindSquare(2, 2);
            quoridor.movePeon(Color.BLUE, "n"); // turno 0
            quoridor.movePeon(Color.red, "e"); // turno 1
            quoridor.movePeon(Color.BLUE, "n"); // turno 2
            fail("did not threw the exception of rewind");
        }
        catch(QuoridorException e){
            assertEquals(QuoridorException.PEON_STEPPED_BACK, e.getMessage());
            assertEquals("Peon1", quoridor.getTypeOfField(6, 2));
        }
    }

    @Test
    public void teleporterShouldTranslateThePeon1AndWarn() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addTeleporterSquare(4, 2, 2, 0);
            quoridor.movePeon(Color.BLUE, "n");
            fail("did not threw the exception");
        }
        catch(QuoridorException e){
            assertEquals(QuoridorException.PEON_HAS_BEEN_TELEPORTED, e.getMessage());
            assertEquals("TeleporterPeon1", quoridor.getTypeOfField(2, 0));
        }
    }

    @Test
    public void skipTurnShouldWarnToModifyTheTurns() {
        int turns = 0;
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addSkipTurnSquare(4, 2);
            turns = quoridor.getTurns();
            quoridor.movePeon(Color.BLUE, "n");
            fail("did not threw the exception");
        }
        catch(QuoridorException e){
            assertEquals(QuoridorException.PLAYER_PLAYS_TWICE, e.getMessage());
            assertEquals(turns+2, quoridor.getTurns());
        }
    }

    @Test
    public void skipTurnShouldWarnToModifyTheTurnsAndOtherPlayerCantPlayTheNextTurn() {
        int turns = 0;
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.addSkipTurnSquare(4, 2);
            turns = quoridor.getTurns();
            quoridor.movePeon(Color.BLUE, "n");
            fail("did not threw the exception");
        }
        catch(QuoridorException e){
            try{
                quoridor.movePeon(Color.red, "s");
                fail("did not threw the exception");
            }
            catch (QuoridorException e2){
                assertEquals(QuoridorException.PLAYER_NOT_TURN, e2.getMessage());
            }
        }
    }

    // -------------------------------------     TEST FOR MACHINE MOVEMENTS      -----------------------------------------------------------
    @Test
    public void shouldCalculateTheShortestPathOfAPeon1() {
        try{
            quoridor = new Quoridor("4", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.shortestPahtPeon(1);
            String[] shortestPath = quoridor.reconstructShortestPath(1).toArray(new String[0]);

        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldCalculateTheShortestPathOfAPeon2() {
        try{
            quoridor = new Quoridor("9", "2", "0", "1", "1", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.shortestPahtPeon(1);
            String[] shortestPath = quoridor.reconstructShortestPath(1).toArray(new String[0]);

        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldCalculateTheShortestPathOfAPeon3() {
        try{
            quoridor = new Quoridor("9", "5", "0", "4", "0", "0",
                    "0", "0", false, "Player1", Color.BLUE, "Player2", Color.red,
                    "NORMAL", 30,  "beginner");
            quoridor.shortestPahtPeon(1);
            quoridor.addBarrier(Color.blue, 0,1,false,"n");
            quoridor.addBarrier(Color.red, 5, 6, true, "l");
            quoridor.addBarrier(Color.blue, 0, 7, false, "n");
            quoridor.addBarrier(Color.red, 12, 5, false, "l");
            quoridor.addBarrier(Color.blue, 9, 2, true, "n");
            quoridor.addBarrier(Color.red, 13, 8, true, "l");
            quoridor.addBarrier(Color.blue, 3, 10, true, "n");
            String[] shortestPath = quoridor.reconstructShortestPath(1).toArray(new String[0]);
            quoridor.printBoard();

        }
        catch(Exception e){
            fail("threw the exception: " + e.getMessage());
        }
    }
}
