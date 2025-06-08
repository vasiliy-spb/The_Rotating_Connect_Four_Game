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
        int expectedAdjacentCount = 0;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
        assertEquals(expectedAdjacentCount, lineScore.adjacentCount());
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
        int expectedScore = 808;
        int expectedAdjacentCount = 1;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);

        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
        assertEquals(expectedAdjacentCount, lineScore.adjacentCount());
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
        int expectedScore = 817;
        int expectedAdjacentCount = 1;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
        assertEquals(expectedAdjacentCount, lineScore.adjacentCount());
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
        int expectedScore = 817;
        int expectedAdjacentCount = 1;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
        assertEquals(expectedAdjacentCount, lineScore.adjacentCount());
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
        int expectedScore = 819;
        int expectedAdjacentCount = 1;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
        assertEquals(expectedAdjacentCount, lineScore.adjacentCount());
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
        int expectedScore = 827;
        int expectedAdjacentCount = 1;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
        assertEquals(expectedAdjacentCount, lineScore.adjacentCount());
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
        int expectedScore = 835;
        int expectedAdjacentCount = 1;

        LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
        assertEquals(expectedLength, lineScore.length());
        assertEquals(expectedScore, lineScore.score());
        assertEquals(expectedAdjacentCount, lineScore.adjacentCount());
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
        int expectedScore = 88829;
        int expectedAdjacentCount = 3;

        for (int row = 0; row < matrix.length; row++) {
            LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
            assertEquals(expectedLength, lineScore.length());
            assertEquals(expectedScore, lineScore.score());
            assertEquals(expectedAdjacentCount, lineScore.adjacentCount());
        }
    }

    @Test
    public void test11() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {1, 8, 8, 1, 1, 1, 1},
                {1, 1, 8, 1, 8, 1, 1},
                {1, 1, 1, 1, 8, 8, 1},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int expectedLength = 7;
        int expectedScore = 8822;
        int expectedAdjacentCount = 2;

        for (int row = 0; row < matrix.length; row++) {
            LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, matrix, direction);
            assertEquals(expectedLength, lineScore.length());
            assertEquals(expectedScore, lineScore.score());
            assertEquals(expectedAdjacentCount, lineScore.adjacentCount());
        }
    }

    @Test
    public void test12() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {1, 1, 8, 1, 8, 8, 1},
                {8, 1, 1, 1, 1, 8, 8},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 7;
        int firstScore = 88829;
        int firstAdjacentCount = 3;

        int secondLength = 7;
        int secondScore = 29;
        int secondAdjacentCount = 0;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());
        assertEquals(firstAdjacentCount, firstLineScore.adjacentCount());

        LineScore secondLineScore = lineEvaluator.evaluateLineScore(1, column, matrix, direction);
        assertEquals(secondLength, secondLineScore.length());
        assertEquals(secondScore, secondLineScore.score());
        assertEquals(secondAdjacentCount, secondLineScore.adjacentCount());

        assertEquals(firstLineScore.length(), secondLineScore.length());
        assertTrue(firstLineScore.score() > secondLineScore.score());
    }

    @Test
    public void test13() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {1, 1, 8, 1, 8, 8, 1},
                {8, 8, 1, 1, 1, 8, 1},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 7;
        int firstScore = 88829;
        int firstAdjacentCount = 3;

        int secondLength = 7;
        int secondScore = 29;
        int secondAdjacentCount = 0;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());
        assertEquals(firstAdjacentCount, firstLineScore.adjacentCount());

        LineScore secondLineScore = lineEvaluator.evaluateLineScore(1, column, matrix, direction);
        assertEquals(secondLength, secondLineScore.length());
        assertEquals(secondScore, secondLineScore.score());
        assertEquals(secondAdjacentCount, secondLineScore.adjacentCount());

        assertEquals(firstLineScore.length(), secondLineScore.length());
        assertTrue(firstLineScore.score() > secondLineScore.score());
    }

    @Test
    public void test14() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {1, 1, 8, 1, 8, 8, 1},
                {8, 8, 1, 1, 1, 8, 8},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 7;
        int firstScore = 88829;
        int firstAdjacentCount = 3;

        int secondLength = 7;
        int secondScore = 36;
        int secondAdjacentCount = 0;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());
        assertEquals(firstAdjacentCount, firstLineScore.adjacentCount());

        LineScore secondLineScore = lineEvaluator.evaluateLineScore(1, column, matrix, direction);
        assertEquals(secondLength, secondLineScore.length());
        assertEquals(secondScore, secondLineScore.score());
        assertEquals(secondAdjacentCount, secondLineScore.adjacentCount());

        assertEquals(firstLineScore.length(), secondLineScore.length());
        assertTrue(firstLineScore.score() > secondLineScore.score());
    }

    @Test
    public void test15() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {8, 8, -1, 1, 8, -1, 1},
                {8, 8, 1, 1, 1, 8, 8},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 2;
        int firstScore = 809;
        int firstAdjacentCount = 1;

        int secondLength = 7;
        int secondScore = 36;
        int secondAdjacentCount = 0;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());
        assertEquals(firstAdjacentCount, firstLineScore.adjacentCount());

        LineScore secondLineScore = lineEvaluator.evaluateLineScore(1, column, matrix, direction);
        assertEquals(secondLength, secondLineScore.length());
        assertEquals(secondScore, secondLineScore.score());
        assertEquals(secondAdjacentCount, secondLineScore.adjacentCount());

        assertTrue(firstLineScore.length() < secondLineScore.length());
        assertTrue(firstLineScore.score() > secondLineScore.score());
    }

    @Test
    public void test16() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {-1, 8, 8, 1, 8, -1, 1},

                {1, 8, 8, 1, 1, 1, 1},
                {1, 1, 8, 1, 8, 1, 1},
                {1, 1, 1, 1, 8, 8, 1},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 4;
        int firstScore = 88825;
        int firstAdjacentCount = 3;

        int otherLength = 7;
        int otherScore = 8822;
        int otherAdjacentCount = 2;

        int totalOtherScore = 0;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());
        assertEquals(firstAdjacentCount, firstLineScore.adjacentCount());


        for (int i = 1; i < matrix.length; i++) {
            LineScore otherLineScore = lineEvaluator.evaluateLineScore(i, column, matrix, direction);
            totalOtherScore += otherLineScore.score();
            assertEquals(otherLength, otherLineScore.length());
            assertEquals(otherScore, otherLineScore.score());
            assertEquals(otherAdjacentCount, otherLineScore.adjacentCount());
        }

        assertTrue(firstLineScore.score() > totalOtherScore);
    }

    @Test
    public void test17() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {-1, 1, 8, 1, 8, -1, 1},

                {1, 1, 8, 1, 1, 1, 1},
                {1, 1, 1, 1, 8, 1, 1},
                {1, 1, 8, 1, 1, 1, 1},
                {1, 1, 1, 1, 8, 1, 1},
                {1, 1, 8, 1, 1, 1, 1},
                {1, 1, 1, 1, 8, 1, 1},
                {1, 1, 8, 1, 1, 1, 1},
                {1, 1, 1, 1, 8, 1, 1},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 4;
        int firstScore = 8818;
        int firstAdjacentCount = 2;

        int otherLength = 7;
        int otherScore = 815;
        int otherAdjacentCount = 1;

        int totalOtherScore = 0;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());
        assertEquals(firstAdjacentCount, firstLineScore.adjacentCount());


        for (int i = 1; i < matrix.length; i++) {
            LineScore otherLineScore = lineEvaluator.evaluateLineScore(i, column, matrix, direction);
            totalOtherScore += otherLineScore.score();
            assertEquals(otherLength, otherLineScore.length());
            assertEquals(otherScore, otherLineScore.score());
            assertEquals(otherAdjacentCount, otherLineScore.adjacentCount());
        }

        assertTrue(firstLineScore.score() > totalOtherScore);
    }

    @Test
    public void test18() {
        int[] direction = {0, -1};
        int[][] matrix = {
                {-1, 1, 1, 1, 8, -1, 1},

                {8, 8, 1, 1, 1, 8, 8},
                {8, 8, 1, 1, 1, 8, 8},
                {8, 8, 1, 1, 1, 8, 8},
                {8, 8, 1, 1, 1, 8, 8},
                {8, 8, 1, 1, 1, 8, 8},
                {8, 8, 1, 1, 1, 8, 8},
                {8, 8, 1, 1, 1, 8, 8},
                {8, 8, 1, 1, 1, 8, 8},
        };

        LineEvaluator lineEvaluator = new LineEvaluator();
        int column = 3;

        int firstLength = 4;
        int firstScore = 811;
        int firstAdjacentCount = 1;

        int otherLength = 7;
        int otherScore = 36;
        int otherAdjacentCount = 0;

        int totalOtherScore = 0;

        LineScore firstLineScore = lineEvaluator.evaluateLineScore(0, column, matrix, direction);
        assertEquals(firstLength, firstLineScore.length());
        assertEquals(firstScore, firstLineScore.score());
        assertEquals(firstAdjacentCount, firstLineScore.adjacentCount());


        for (int i = 1; i < matrix.length; i++) {
            LineScore otherLineScore = lineEvaluator.evaluateLineScore(i, column, matrix, direction);
            totalOtherScore += otherLineScore.score();
            assertEquals(otherLength, otherLineScore.length());
            assertEquals(otherScore, otherLineScore.score());
            assertEquals(otherAdjacentCount, otherLineScore.adjacentCount());
        }

        assertTrue(firstLineScore.score() > totalOtherScore);
    }
}
