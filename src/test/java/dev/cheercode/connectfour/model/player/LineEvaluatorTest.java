package dev.cheercode.connectfour.model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LineEvaluatorTest {

    @Test
    public void test01() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {-1, -1, -1, -1, -1, -1, -1}
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int row = 0;
        int column = 3;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> lineEvaluator.evaluateLineScore(row, column, matrix, direction));
        assertTrue(exception.getMessage().contains("slot with negative score"));
    }

    @Test
    public void test02() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {-1, -1, -1, 0, -1, -1, -1}
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int row = 0;
        int column = 3;

        int expectedLength = 1;
        int expectedScore = 0;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
    }

    @Test
    public void test03() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {-1, -1, -8, 0, 8, -1, -1}
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int row = 0;
        int column = 3;

        int expectedLength = 2;
        int expectedScore = 80;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
    }

    @Test
    public void test04() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {-1, 8, 1, 0, 8, -1, -1}
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int row = 0;
        int column = 3;

        int expectedLength = 4;
        int expectedScore = 89;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
    }

    @Test
    public void test05() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {-1, 8, 1, 0, 8, 0, -1}
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int row = 0;
        int column = 3;

        int expectedLength = 5;
        int expectedScore = 89;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
    }

    @Test
    public void test06() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {-1, 8, 1, 1, 8, 0, -1}
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int row = 0;
        int column = 3;

        int expectedLength = 5;
        int expectedScore = 91;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
    }

    @Test
    public void test07() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {8, 8, 1, 1, 8, 0, -1}
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int row = 0;
        int column = 3;

        int expectedLength = 6;
        int expectedScore = 99;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
    }

    @Test
    public void test08() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {8, 8, 1, 1, 8, 0, 8}
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int row = 0;
        int column = 3;

        int expectedLength = 7;
        int expectedScore = 107;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
    }

    @Test
    public void test10() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {8, 8, 8, 1, 1, 1, 1},
                {1, 8, 8, 1, 8, 1, 1},
                {1, 1, 8, 1, 8, 8, 1},
                {1, 1, 1, 1, 8, 8, 8}
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int expectedLength = 7;
        int expectedScore = 245;

        for (int row = 0; row < 4; row++) {
            LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
            assertEquals(expectedLength, lineScore.length());
            assertEquals(expectedScore, lineScore.score());
        }
    }

    @Test
    public void test11() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {1, 1, 8, 1, 8, 8, 1},
                {8, 1, 1, 1, 1, 8, 8},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 7;
        int firstScore = 245;

        int secondLength = 7;
        int secondScore = 29;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());

        LineScore secondLineScore = lineEvaluator.evaluateLineScore(1, column, matrix, direction);
        assertEquals(secondLength, secondLineScore.length());
        assertEquals(secondScore, secondLineScore.score());

        assertEquals(firstLineScore.length(), secondLineScore.length());
        assertTrue(firstLineScore.score() > secondLineScore.score());
    }

    @Test
    public void test12() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {1, 1, 8, 1, 8, 8, 1},
                {8, 8, 1, 1, 1, 8, 1},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 7;
        int firstScore = 245;

        int secondLength = 7;
        int secondScore = 29;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());

        LineScore secondLineScore = lineEvaluator.evaluateLineScore(1, column, matrix, direction);
        assertEquals(secondLength, secondLineScore.length());
        assertEquals(secondScore, secondLineScore.score());

        assertEquals(firstLineScore.length(), secondLineScore.length());
        assertTrue(firstLineScore.score() > secondLineScore.score());
    }

    @Test
    public void test13() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {1, 1, 8, 1, 8, 8, 1},
                {8, 8, 1, 1, 1, 8, 8},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 7;
        int firstScore = 245;

        int secondLength = 7;
        int secondScore = 36;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());

        LineScore secondLineScore = lineEvaluator.evaluateLineScore(1, column, matrix, direction);
        assertEquals(secondLength, secondLineScore.length());
        assertEquals(secondScore, secondLineScore.score());

        assertEquals(firstLineScore.length(), secondLineScore.length());
        assertTrue(firstLineScore.score() > secondLineScore.score());
    }

    @Test
    public void test14() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {8, 8, -1, 1, 8, -1, 1},
                {8, 8, 1, 1, 1, 8, 8},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 2;
        int firstScore = 81;

        int secondLength = 7;
        int secondScore = 36;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());

        LineScore secondLineScore = lineEvaluator.evaluateLineScore(1, column, matrix, direction);
        assertEquals(secondLength, secondLineScore.length());
        assertEquals(secondScore, secondLineScore.score());

        assertTrue(firstLineScore.length() < secondLineScore.length());
        assertTrue(firstLineScore.score() > secondLineScore.score());
    }
}
