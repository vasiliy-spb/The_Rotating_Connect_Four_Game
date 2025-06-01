package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.factory.FromFileBoardFactory;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BotFrequencyStrategy implements MoveStrategy {
    public static void main(String[] args) throws IOException {
//        randomTest();
        customTest();
    }

    private static void customTest() throws IOException {
        String maskNumber = "23";
        String path = "src/main/resources/board_masks/" + Board.Size.ROW7_COLUMN10.name().toLowerCase() + "/mask_" + maskNumber + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            StringBuilder input = new StringBuilder();
            while (reader.ready()) {
                input.append(reader.readLine().trim()).append("\n");
            }
            String[] lines = input.toString().trim().split("\n");
            int height = lines.length;
            int width = lines[0].length();
            boolean[][] mask = new boolean[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    char ch = lines[i].charAt(j);
                    if (ch == '+') {
                        mask[i][j] = true;
                    }
                }
            }
            Board board = new Board(new DefaultBoardState(Board.Size.ROW7_COLUMN10, mask));

            Player player = new Player(Disc.YELLOW, new HumanConsoleInputStrategy());

            board.drop(0, Disc.YELLOW);
            board.drop(1, Disc.WHITE);
            board.drop(4, Disc.YELLOW);
            board.drop(5, Disc.CYAN);
            board.drop(9, Disc.YELLOW);

            new RendererForIdea().show(board);
            int selectedColumn = new BotFrequencyStrategy().chooseColumn(player, board);
            System.out.println("selectedColumn = " + selectedColumn);
        }
    }

    private static void randomTest() {
        Board board = new FromFileBoardFactory().create(Board.Size.ROW7_COLUMN10);
        Player player = new Player(Disc.YELLOW, new HumanConsoleInputStrategy());
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int col = random.nextInt(board.getWidth());
            while (board.isColumnFilled(col)) {
                col = random.nextInt(board.getWidth());
            }
            if (i % 2 == 0) {
                board.drop(col, player.getDisc());
            } else {
                Disc disc = Disc.values()[random.nextInt(Disc.values().length)];
                board.drop(col, disc);
            }
        }
        new RendererForIdea().show(board);
        int selectedColumn = new BotFrequencyStrategy().chooseColumn(player, board);
        System.out.println("selectedColumn = " + selectedColumn);
    }

    @Override
    public int chooseColumn(Player player, Board board) {
        int[] lowestPositions = createLowestPositions(board);
        int[][] frequencyMatrix = createFrequencyMatrix(player.getDisc(), board, lowestPositions);
//        printMatrix(frequencyMatrix);
        return chooseMostProfitableColumn(frequencyMatrix, lowestPositions);
    }

    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private int[] createLowestPositions(Board board) {
        int width = board.getWidth();
        int[] lowestPositions = new int[width];
        for (int column = 0; column < width; column++) {
            while (lowestPositions[column] < board.getHeight() && !board.isOnField(lowestPositions[column], column)) {
                lowestPositions[column]++;
            }
        }
        for (int column = 0; column < width; column++) {
            while (lowestPositions[column] < board.getHeight() && board.isOnField(lowestPositions[column], column) && board.isEmptySlot(lowestPositions[column], column)) {
                lowestPositions[column]++;
            }
            lowestPositions[column]--;
        }
        System.out.println("Arrays.toString(lowestPositions) = " + Arrays.toString(lowestPositions));
        return lowestPositions;
    }

    private int[][] createFrequencyMatrix(Disc disc, Board board, int[] lowestPositions) {
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}
        };
        int height = board.getHeight();
        int width = board.getWidth();
        int[][] frequencyMatrix = new int[height][width];
        for (int row = 0; row < frequencyMatrix.length; row++) {
            for (int column = 0; column < frequencyMatrix[0].length; column++) {
                if (!board.isOnField(row, column)) {
                    frequencyMatrix[row][column] = -5;
                    continue;
                }
                if (board.isEmptySlot(row, column)) {
                    frequencyMatrix[row][column] = 1;
                    continue;
                }
                if (board.get(row, column) == disc) {
                    frequencyMatrix[row][column] = 8;
                } else {
                    frequencyMatrix[row][column] = -8;
                }
            }
        }
        System.out.println("frequencyMatrix before");
        printMatrix(frequencyMatrix);
        System.out.println();
        int[] profit = new int[width];
        for (int column = 0; column < width; column++) {
            int row = lowestPositions[column];
            System.out.println();
            System.out.println("row-column= " + row + ":" + column);
            for (int[] direction : directions) {
                System.out.print("Arrays.toString(direction) = " + Arrays.toString(direction) + " : ");
                if (!isValidDirection(row, column, direction, frequencyMatrix)) {
                    System.out.println(false);
                    continue;
                }
                System.out.println(true);
//                frequencyMatrix[row][column] = calculateProfitForDirection(row, column, direction, frequencyMatrix);
                int prof = calculateProfitForDirection(row, column, direction, frequencyMatrix);
                profit[column] += prof;
                System.out.println("prof = " + prof);
                System.out.println();
            }
        }

//        System.out.println("frequencyMatrix after");
//        printMatrix(frequencyMatrix);
//        System.out.println();

        for (int column = 0; column < width; column++) {
            if (lowestPositions[column] >= 0) {
                frequencyMatrix[lowestPositions[column]][column] = profit[column];
            }
        }

        System.out.println("frequencyMatrix with profit");
        printMatrix(frequencyMatrix);
        System.out.println();
        return frequencyMatrix;
    }

    private boolean isValidDirection(int row, int column, int[] direction, int[][] frequencyMatrix) {
        int currentRow = row;
        int currentColumn = column;
        int left = 0;
        while (currentRow != row - direction[0] * 4 && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
            if (frequencyMatrix[currentRow][currentColumn] < 0) {
                break;
            }
            if (currentRow != row || currentColumn != column) {
                left++;
            }
            currentRow -= direction[0];
            currentColumn -= direction[1];
        }

        currentRow = row;
        currentColumn = column;
        int right = 0;
        while (currentRow != row + direction[0] * 4 && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
            if (frequencyMatrix[currentRow][currentColumn] < 0) {
                break;
            }
            if (currentRow != row || currentColumn != column) {
                right++;
            }
            currentRow += direction[0];
            currentColumn += direction[1];
        }

        return left + right + 1 >= 4;
    }

    private int calculateProfitForDirection(int row, int column, int[] direction, int[][] frequencyMatrix) {
        int currentRow = row;
        int currentColumn = column;
        int left = 0;
        while (currentRow != row - direction[0] * 4 && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
            if (frequencyMatrix[currentRow][currentColumn] < 0) {
                break;
            }
            if (currentRow != row || currentColumn != column) {
                left += frequencyMatrix[currentRow][currentColumn];
            }
            currentRow -= direction[0];
            currentColumn -= direction[1];
        }

        currentRow = row;
        currentColumn = column;
        int right = 0;
        while (currentRow != row + direction[0] * 4 && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
            if (frequencyMatrix[currentRow][currentColumn] < 0) {
                break;
            }
            if (currentRow != row || currentColumn != column) {
                right += frequencyMatrix[currentRow][currentColumn];
            }
            currentRow += direction[0];
            currentColumn += direction[1];
        }

        return left + right + frequencyMatrix[row][column];
//        return left + right;
    }

    private boolean isValid(int row, int column, int height, int width) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    private boolean isValidDirection(int row, int column, int[] direction, Board board, Disc disc) {
        int max = 0;
        int currentRow = row - direction[0] * 3;
        int currentColumn = column - direction[1] * 3;
        while (!board.isOnField(currentRow, currentColumn)) {
            currentRow += direction[0];
            currentColumn += direction[1];
        }
        while (currentRow != row + direction[0] * 4 && max < 4) {
            if (!board.isOnField(currentRow, currentColumn)) {
                break;
            }
            if (board.isEmptySlot(currentRow, currentColumn)) {
                max++;
                currentRow += direction[0];
                currentColumn += direction[1];
                continue;
            }
            Disc currentDisc = board.get(currentRow, currentColumn);
            if (currentDisc != disc) {
                max = 0;
            } else {
                max++;
            }
            currentRow += direction[0];
            currentColumn += direction[1];
        }
        return max >= 4;
    }

    private int calculateProfitForDirection(int row, int column, int[] direction, Board board, Disc disc) {
        System.out.println("-------calculateProfitForDirection-------");
        System.out.println("row = " + row);
        System.out.println("column = " + column);
        int similarDiscValue = 7;

        int currentRow = row;
        int currentColumn = column;
        int leftValue = 0;
        while (currentRow > row - direction[0] * 4 && isValid(currentRow, currentColumn, board.getHeight(), board.getWidth())) {
            if (!board.isOnField(currentRow, currentColumn)) {
                break;
            }
            if (board.isEmptySlot(currentRow, currentColumn)) {
                currentRow -= direction[0];
                currentColumn -= direction[1];
                continue;
            }
            Disc currentDisc = board.get(currentRow, currentColumn);
            if (currentDisc != disc) {
                break;
            } else {
                leftValue += similarDiscValue;
            }
            currentRow -= direction[0];
            currentColumn -= direction[1];
        }

        System.out.println("leftValue = " + leftValue);

        currentRow = row;
        currentColumn = column;
        int rightValue = 0;
        while (currentRow < row + direction[0] * 4) {
            if (!board.isOnField(currentRow, currentColumn)) {
                break;
            }
            if (board.isEmptySlot(currentRow, currentColumn)) {
                currentRow += direction[0];
                currentColumn += direction[1];
                continue;
            }
            Disc currentDisc = board.get(currentRow, currentColumn);
            if (currentDisc != disc) {
                break;
            } else {
                rightValue += similarDiscValue;
            }
            currentRow += direction[0];
            currentColumn += direction[1];
        }

        System.out.println("rightValue = " + rightValue);
        System.out.println();

        return leftValue + rightValue;
    }

    private int chooseMostProfitableColumn(int[][] frequencyMatrix, int[] lowestPositions) {
        int max = 0;
        for (int column = 0; column < lowestPositions.length; column++) {
            if (lowestPositions[column] < 0) {
                continue;
            }
            max = Math.max(max, frequencyMatrix[lowestPositions[column]][column]);
        }
        for (int column = 0; column < lowestPositions.length; column++) {
            if (lowestPositions[column] < 0) {
                continue;
            }
            if (frequencyMatrix[lowestPositions[column]][column] == max) {
                return column;
            }
        }
        throw new IllegalStateException("Not a single column is chosen.");
    }

    record CheckDirectionResult(
            int length,
            int profit
    ) {
    }
}

/*
-5	-5	-5	-5	1	1	-5	-5	-5	-5
-5	-5	-5	1	1	1	1	-5	-5	-5
-5	-5	1	1	1	1	1	1	-5	-5
-5	-5	1	1	1	1	1	1	-5	-5
-5	1	1	1	1	1	1	1	0	-5
-5	1	0	1	1	1	1	0	8	-5
8	0	8	0	0	0	0	8	-8	0

-5	-5	-5	-5	1	1	-5	-5	-5	-5
-5	-5	-5	1	1	1	1	-5	-5	-5
-5	-5	1	1	1	1	1	1	-5	-5
-5	-5	1	1	1	1	1	1	-5	-5
-5	1	1	1	1	1	1	1	0	-5
-5	1	0	1	1	1	1	0	8	-5
8	0	8	0	0	0	0	8	-8	0
selectedColumn = 1
 */
