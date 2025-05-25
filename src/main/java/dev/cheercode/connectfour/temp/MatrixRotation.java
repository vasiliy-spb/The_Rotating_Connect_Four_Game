package dev.cheercode.connectfour.temp;

public class MatrixRotation {

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 0, 1, 2},
                {3, 4, 5, 6},
                {7, 8, 9, 0}
        };

        System.out.println("Исходная матрица:");
        printMatrix(matrix);

        System.out.println("\nПоворот влево:");
        int[][] rotatedLeft = rotateLeft(matrix);
        printMatrix(rotatedLeft);

        System.out.println("\nПоворот вправо:");
        int[][] rotatedRight = rotateRight(matrix);
        printMatrix(rotatedRight);

        System.out.println("\nПереворот вверх тормашками:");
        int[][] upsideDown = flipUpsideDown(matrix);
        printMatrix(upsideDown);
    }

    // 1) Поворот матрицы влево (против часовой стрелки на 90°)
    public static int[][] rotateLeft(int[][] matrix) {
        int width = matrix.length;
        int height = matrix[0].length;
        int[][] rotated = new int[height][width];

        for (int row = 0; row < width; row++) {
            for (int column = 0; column < height; column++) {
                rotated[column][row] = matrix[row][height - 1 - column];
            }
        }
        return rotated;
    }

    // 2) Поворот матрицы вправо (по часовой стрелке на 90°)
    public static int[][] rotateRight(int[][] matrix) {
        int width = matrix.length;
        int height = matrix[0].length;
        int[][] rotated = new int[height][width];

        for (int row = 0; row < width; row++) {
            for (int column = 0; column < height; column++) {
                rotated[column][row] = matrix[width - 1 - row][column];
            }
        }
        return rotated;
    }

    // 3) Переворот матрицы вверх тормашками (отражение по горизонтали)
    public static int[][] flipUpsideDown(int[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        int[][] flipped = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                flipped[row][column] = matrix[height - 1 - row][width - 1 - column];
            }
        }
        return flipped;
    }

    // Вспомогательный метод для вывода матрицы
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}


/*

Напиши на java 3 метода, которые поворачивают матрицу:
1 2 3 4
5 6 7 8
9 0 1 2
3 4 5 6
7 8 9 0

1) влево:
4 8 2 6 0
3 7 1 5 9
2 6 0 4 8
1 5 9 3 7

2) вправо:
7 3 9 5 1
8 4 0 6 2
9 5 1 7 3
0 6 2 8 4

3) вверх тормашками:
0 9 8 7
6 5 4 3
2 1 0 9
8 7 6 5
4 3 2 1

 */

/*

Исходная матрица:
1 2 3 4
5 6 7 8
9 0 1 2
3 4 5 6
7 8 9 0

Поворот влево:
4 8 2 6 0
3 7 1 5 9
2 6 0 4 8
1 5 9 3 7

Поворот вправо:
7 3 9 5 1
8 4 0 6 2
9 5 1 7 3
0 6 2 8 4

Переворот вверх тормашками:
0 9 8 7
6 5 4 3
2 1 0 9
8 7 6 5
4 3 2 1

 */