import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GameOfLife {
    public static void main(String[] args) throws InterruptedException, IOException {

        String file = new Scanner(new File("C:\\Users\\alfer\\College\\Java\\game-of-life\\spaceships\\LWSS.txt"))
                .useDelimiter("\\Z").next();

        System.out.println(file);

        int[][] prev_state = inputToMatrix(file);
        while (true) {
            int[][] next_state = nextState(prev_state);
            System.out.println(matrixToOutput(next_state));
            System.out.println("----------------------------");
            prev_state = next_state;
            Thread.sleep(250);
        }

    }

    public static int[][] inputToMatrix(String file) {
        int num_lines = file.split("\r\n|\r|\n").length;
        int[][] matrix = new int[num_lines][];

        Scanner scanner = new Scanner(file);
        for (int i = 0; i < num_lines; i++) {
            String line = scanner.nextLine();
            int num_elements = line.length();
            int[] row = new int[num_elements];

            for (int j = 0; j < num_elements; j++) {
                char element = line.charAt(j);
                if (element == '*') {
                    row[j] = 1;
                } else if (element == '.') {
                    row[j] = 0;
                } else {
                    continue;
                }
            }
            matrix[i] = row;
        }
        scanner.close();
        return matrix;
    }

    public static int[][] permutation(int[] xs, int[] ys) {
        int length = xs.length * ys.length - 1;
        int[][] permute = new int[length][];
        int count = 0;

        for (int i = 0; i < xs.length; i++) {
            for (int j = 0; j < ys.length; j++) {
                if (xs[i] == 0 & ys[j] == 0) {
                    continue;
                }

                int[] p = { xs[i], ys[j] };
                permute[count] = p;
                count += 1;
            }
        }
        return permute;
    }

    public static int[][] nextState(int[][] prev_state) {
        int[][] next_state = new int[prev_state.length][];

        for (int i = 0; i < prev_state.length; i++) {
            int[] new_row = new int[prev_state[i].length];
            for (int j = 0; j < prev_state[i].length; j++) {
                int count = 0;
                int[] xs;
                int[] ys;

                if (i == 0) {
                    xs = new int[] { 0, 1, prev_state.length - 1 };
                } else if (i == (prev_state.length - 1)) {
                    xs = new int[] { 0, -1, -prev_state.length + 1 };
                } else {
                    xs = new int[] { -1, 0, 1 };
                }

                if (j == 0) {
                    ys = new int[] { 0, 1, prev_state[i].length - 1 };
                } else if (j == (prev_state[i].length - 1)) {
                    ys = new int[] { 0, -1, -prev_state[i].length + 1 };
                } else {
                    ys = new int[] { -1, 0, 1 };
                }

                int[][] adj_coors = GameOfLife.permutation(xs, ys);

                for (int k = 0; k < adj_coors.length; k++) {
                    int x = j + adj_coors[k][1];
                    int y = i + adj_coors[k][0];
                    // System.out.println("x: " + x + ", y: " + y + ", i: " + i + ", j: " + j);
                    if (prev_state[y][x] == 1) {
                        count += 1;
                    }
                }

                if (count < 2 || count > 3) {
                    new_row[j] = 0;
                } else if (count == 3) {
                    new_row[j] = 1;
                } else {
                    new_row[j] = prev_state[i][j];
                }
            }
            next_state[i] = new_row;
        }
        return next_state;
    }

    public static String matrixToOutput(int[][] matrix) {
        String output = new String("");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    output = output + ".";
                } else {
                    output = output + "*";
                }
            }
            output = output + "\n";
        }

        return output;
    }
}
