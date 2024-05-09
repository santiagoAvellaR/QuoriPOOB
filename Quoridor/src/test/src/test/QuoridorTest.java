
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

    @Test
    public void shouldCreateQuoridorBoardAndInitializeThePeonsOddSize() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            assertEquals("Peon", quoridor.getTypeOfField(0, 4));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldCreateQuoridorBoardAndInitializeThePeonsEvenSize() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            assertEquals("Peon", quoridor.getTypeOfField(0, 2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldReturnTheValidMovementsOfThePeon1InTheInitialPosition() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            String[]expectedResult = {"u", "l", "r"};
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
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            String[]expectedResult = {"d", "l", "r"};
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
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.movePeon(Color.BLUE, "u");
            assertEquals("Peon", quoridor.getTypeOfField(4, 2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveUpThePeon1AndGetTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.movePeon(Color.BLUE, "u");
            String[]expectedResult = {"u", "d", "l", "r"};
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
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.movePeon(Color.BLUE, "r");
            quoridor.movePeon(Color.red, "d");
            assertEquals("Peon", quoridor.getTypeOfField(2, 2));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldMoveDownThePeon2AndGetTheNewValidMovements() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.movePeon(Color.BLUE, "l");
            quoridor.movePeon(Color.red, "d");
            String[]expectedResult = {"u", "d", "l", "r"};
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
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.movePeon(Color.BLUE, "l");
            quoridor.movePeon(Color.red, "r");
            quoridor.movePeon(Color.BLUE, "l");
            String[]expectedResultPeon1 = {"u", "r"};
            assertArrayEquals(expectedResultPeon1, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
            quoridor.movePeon(Color.red, "r");
            String[]expectedResultPeon2 = {"d", "l"};
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
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.movePeon(Color.BLUE, "u");
            quoridor.movePeon(Color.red, "d");
            quoridor.movePeon(Color.BLUE, "u");
            String[]expectedResultPeon2 = {"u", "jd", "l", "r"};
            assertArrayEquals(expectedResultPeon2, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));

        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon2ShouldJumpUpThePeon1() {
        try{
            quoridor = new Quoridor("5", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.movePeon(Color.BLUE, "u");
            quoridor.movePeon(Color.red, "d");
            quoridor.movePeon(Color.BLUE, "u");
            quoridor.movePeon(Color.red, "jd");
            assertEquals("Peon", quoridor.getTypeOfField(6, 4));

        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void peon1ShouldRetunrTheValidMovementOfTheJumpUpThePeon2() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.movePeon(Color.BLUE, "u");
            quoridor.movePeon(Color.red, "d");
            String[]expectedResult = {"ju", "d", "l", "r"};
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
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.movePeon(Color.BLUE, "u");
            quoridor.movePeon(Color.red, "d");
            String[]expectedResult = {"ju", "d", "l", "r"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.blue).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAddANormalVerticalBarrier() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.addBarrier(Color.blue, 0, 1,  false,  'n');
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
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.addBarrier(Color.blue, 0, 1, false, 'n');
            String[]expectedResult = {"d", "r"};
            assertArrayEquals(expectedResult, quoridor.getPeonValidMovements(Color.red).toArray(new String[0]));
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

    @Test
    public void shouldAddANormalHorizontalBarrier() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.addBarrier(Color.blue, 1, 0, true,  'n');
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
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            quoridor.addBarrier(Color.blue, 1, 0,true,  'n');
            String[]expectedResult = {"l", "r"};
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
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            assertTrue(quoridor.peonsHasAnExit());
        }
        catch(QuoridorException e){
            fail("threw the exception: " + e.getMessage());
        }
    }

}
