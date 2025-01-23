package org.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HangmanStateTest {

    private HangmanState hangmanState;

    @BeforeEach
    public void setUp() {
        hangmanState = new HangmanState(6);
    }

    @Test
    public void testInitialMistakesCount() {
        assertEquals(0, hangmanState.getMistakes(), "Initial mistakes count should be 0");
    }

    @Test
    public void testIncrementMistakes() {
        hangmanState.incrementMistakes();
        assertEquals(1, hangmanState.getMistakes(), "Mistakes count should be 1 after increment");
    }

    @Test
    public void testMaxMistakes() {
        for (int i = 0; i < 6; i++) {
            hangmanState.incrementMistakes();
        }
        assertEquals(6, hangmanState.getMistakes(), "Mistakes count should be 6 at max for hard level");
    }

    @Test
    public void testDrawHangmanHardStages() {
        System.out.println("testDrawHangmanHardStages");

        for (int i = 0; i < 6; i++) {
            hangmanState.drawHangman();
            hangmanState.incrementMistakes();
        }
    }

    @Test
    public void testIsGameOver() {
        assertFalse(hangmanState.isGameOver(), "Game should not be over initially");

        for (int i = 0; i <= 6; i++) {
            hangmanState.incrementMistakes();
        }
        assertTrue(hangmanState.isGameOver(), "Game should be over after reaching max mistakes");
    }

    @Test
    public void testDrawHangmanEasyStages() {
        hangmanState = new HangmanState(10);

        System.out.println("testDrawHangmanEasyStages");
        for (int i = 0; i <= 10; i++) {
            hangmanState.drawHangman();
            hangmanState.incrementMistakes();
        }

        assertEquals(10, hangmanState.getMistakes(), "Mistakes count should be 8 for Easy level");
    }

    @Test
    public void testDrawHangmanMediumStages() {
        hangmanState = new HangmanState(8);

        System.out.println("testDrawHangmanMediumStages");
        for (int i = 0; i <= 8; i++) {
            hangmanState.drawHangman();
            hangmanState.incrementMistakes();
        }

        assertEquals(8, hangmanState.getMistakes(), "Mistakes count should be 8 for Easy level");
    }

    @Test
    public void testDrawHangmanEasyStagesWith8Mistakes() {
        hangmanState = new HangmanState(10);

        for (int i = 0; i < 8; i++) {
            hangmanState.incrementMistakes();
        }

        System.out.println("testDrawHangmanEasyStagesWith8Mistakes");
        hangmanState.drawHangman();
        assertEquals(8, hangmanState.getMistakes(), "Mistakes count should be 8 for Easy level");
    }

    @Test
    public void testDrawHangmanMediumStagesWith3Mistakes() {
        hangmanState = new HangmanState(8);

        for (int i = 0; i < 3; i++) {
            hangmanState.incrementMistakes();
        }

        System.out.println("testDrawHangmanMediumStagesWith3Mistakes");
        hangmanState.drawHangman();
        assertEquals(3, hangmanState.getMistakes(), "Mistakes count should be 3 for Medium level");
    }

    @Test
    public void testDrawHangmanHardStagesWith1Mistake() {
        hangmanState = new HangmanState(6);

        hangmanState.incrementMistakes();

        System.out.println("testDrawHangmanHardStagesWith1Mistake");
        hangmanState.drawHangman();
        assertEquals(1, hangmanState.getMistakes(), "Mistakes count should be 1 for Hard level");
    }
}