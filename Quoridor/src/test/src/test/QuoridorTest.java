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
            Field[][] expectedBoard = new Field[5*2 -1][5*2 - 1];
            expectedBoard[0][4] = quoridor.getPeon1();
            expectedBoard[8][4] = quoridor.getPeon2();
            assertEquals("Peon", quoridor.getTypeOfField(0, 4));
        }
        catch(QuoridorException e){
            fail("threw exception");
        }
    }

    @Test
    public void shouldCreateQuoridorBoardAndInitializeThePeonsEvenSize() {
        try{
            quoridor = new Quoridor("4", "0", "0", "1", "1", "0",
                    "4", "2", "Player1", Color.BLUE, "Player2", Color.red,
                    "Timed", "30", false, "beginner");
            Field[][] expectedBoard = new Field[4*2 -1][4*2 - 1];
            expectedBoard[0][2] = quoridor.getPeon1();
            expectedBoard[6][2] = quoridor.getPeon2();
            assertEquals("Peon", quoridor.getTypeOfField(0, 2));
        }
        catch(QuoridorException e){
            fail("threw exception");
        }
    }

    @Test
    void movePeon() {
    }

    @Test
    void addBarrier() {
    }

    @Test
    void getBoard() {
    }
}