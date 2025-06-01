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

    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}
    };

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
        int[] profit = calculateProfitForLowestPositions(player.getDisc(), board, lowestPositions);
        return chooseMostProfitableColumn(profit);
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
//        System.out.println("Arrays.toString(lowestPositions) = " + Arrays.toString(lowestPositions));
        return lowestPositions;
    }

    private int[] calculateProfitForLowestPositions(Disc disc, Board board, int[] lowestPositions) {
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

//        System.out.println("frequencyMatrix before");
//        printMatrix(frequencyMatrix);
//        System.out.println();

        int[] profit = new int[width];
        for (int column = 0; column < width; column++) {
            int row = lowestPositions[column];
            if (row < 0) {
                continue;
            }
//            System.out.println();
//            System.out.println("---------------------------------------------------------------");
//            System.out.println("row-column= " + row + ":" + column);

            for (int[] direction : DIRECTIONS) {

//                System.out.print("Arrays.toString(direction) = " + Arrays.toString(direction) + " : ");

                CheckDirectionResult result = checkProfitForDirection(row, column, direction, frequencyMatrix);
                if (result.length() < 4) {
//                    System.out.println(false);
                    continue;
                }
//                System.out.println(true);
                profit[column] += result.profit();

//                System.out.println("profit = " + result.profit());
//                System.out.println();
            }
        }

//        System.out.println();
//        System.out.println("Arrays.toString(profit) = " + Arrays.toString(profit));
//        System.out.println();

        return profit;
    }

    private CheckDirectionResult checkProfitForDirection(int row, int column, int[] direction, int[][] frequencyMatrix) {
        int currentRow = row;
        int currentColumn = column;
        int leftProfit = 0;
        int leftLength = 0;
        while ((currentRow != row - direction[0] * 4 || currentColumn != column - direction[1] * 4) && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
            if (frequencyMatrix[currentRow][currentColumn] < 0) {
                break;
            }
            if (currentRow != row || currentColumn != column) {
                leftProfit += frequencyMatrix[currentRow][currentColumn];
                leftLength++;
            }
            currentRow -= direction[0];
            currentColumn -= direction[1];
        }

        currentRow = row;
        currentColumn = column;
        int rightProfit = 0;
        int rightLength = 0;
        while ((currentRow != row + direction[0] * 4 || currentColumn != column + direction[1] * 4) && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
            if (frequencyMatrix[currentRow][currentColumn] < 0) {
                break;
            }
            if (currentRow != row || currentColumn != column) {
                rightProfit += frequencyMatrix[currentRow][currentColumn];
                rightLength++;
            }
            currentRow += direction[0];
            currentColumn += direction[1];
        }

        int totalLength = leftLength + rightLength + 1;
        int totalProfit = leftProfit + rightProfit + frequencyMatrix[row][column];
        if (totalLength > 4) {
            totalProfit += frequencyMatrix[row][column];
        }
        return new CheckDirectionResult(totalLength, totalProfit);
    }

    private boolean isValid(int row, int column, int height, int width) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    private int chooseMostProfitableColumn(int[] profit) {
        int maxProfit = Arrays.stream(profit).max().getAsInt();
        for (int column = 0; column < profit.length; column++) {
            if (profit[column] == maxProfit) {
                return column;
            }
        }
        throw new IllegalStateException("Not a single column is chosen.");
    }

    private record CheckDirectionResult(
            int length,
            int profit
    ) {
    }
}
