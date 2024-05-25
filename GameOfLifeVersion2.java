import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import org.javatuples;

public class GameOfLifeVersion2 {
    public static void main(String[] args) throws InterruptedException, IOException {

        String file = new Scanner(
                new File("C:\\Users\\alfer\\College\\Java\\game-of-life\\oscillators\\blinker.txt"))
                .useDelimiter("\\Z").next();

        System.out.println(file);

        // int[][] prev_state = inputToMatrix(file);
        // while (true) {
        // int[][] next_state = nextState(prev_state);
        // System.out.println(matrixToOutput(next_state));
        // Thread.sleep(250);
        // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        // prev_state = next_state;

        List<Pair> livingCells = getLivingCells(file);
        for (Tuple cell : livingCells) {
            int[] coordinates = new int[] { cell.x, cell.y };
            System.out.println(Arrays.toString(coordinates));
        }
        // print --------
        System.out.println("----------------------------------------------------");
        List<Tuple> nextLivingCells = getNextLivingCells(livingCells);
        for (Tuple cell : nextLivingCells) {
            int[] coordinates = new int[] { cell.x, cell.y };
            System.out.println(Arrays.toString(coordinates));
        }
    }

    public static List<Tuple> getLivingCells(String file) {
        int num_lines = file.split("\r\n|\r|\n").length;
        List<Tuple> livingCells = new ArrayList<>();

        Scanner scanner = new Scanner(file);
        for (int i = 0; i < num_lines; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '*') {
                    livingCells.add(new Tuple(j, i));
                }
            }
        }
        scanner.close();
        return livingCells;
    }

    public static List<Tuple> getVicinityCoordinates(Tuple livingCell) {
        int[] xSteps = new int[] { -1, 0, 1 };
        int[] ySteps = new int[] { -1, 0, 1 };
        List<Tuple> vicinityCoordinates = new ArrayList<>();

        for (int xStep : xSteps) {
            for (int yStep : ySteps) {
                if (xStep == 0 & yStep == 0) {
                    continue;
                }
                vicinityCoordinates.add(new Tuple(xStep + livingCell.x, yStep + livingCell.y));
            }
        }
        return vicinityCoordinates;
    }

    public static List<Tuple> getNextLivingCells(List<Tuple> currentLivingCells) {
        // Create a map of where key, value = vicinity coordinate, count
        Map<Tuple, Integer> tracedCells = new HashMap<>();
        List<Tuple> nextLivingCells = new ArrayList<>();

        // loop each current living cell
        for (Tuple livingCell : currentLivingCells) {
            int count = 0;
            List<Tuple> vicinityCells = getVicinityCoordinates(livingCell);
            for (Tuple vicinityCell : vicinityCells) {
                if (currentLivingCells.contains(vicinityCell)) {
                    count += 1;
                }

                if (tracedCells.containsKey(vicinityCell)) {
                    tracedCells.put(vicinityCell, tracedCells.get(vicinityCell) + 1);
                } else {
                    tracedCells.put(vicinityCell, 1);
                }
            }
            if (count >= 2 | count <= 3) {
                nextLivingCells.add(livingCell);
            }
        }

        for (Map.Entry<Tuple, Integer> tracedCell : tracedCells.entrySet()) {
            Tuple coordinate = tracedCell.getKey();
            Integer count = tracedCell.getValue();

            if (count == 3) {
                nextLivingCells.add(coordinate);
            }
        }
        return nextLivingCells;
    }

}

// public static int[][] permutation(int[] xs, int[] ys) {
// int length = xs.length * ys.length - 1;
// int[][] permute = new int[length][];
// int count = 0;

// for (int i = 0; i < xs.length; i++) {
// for (int j = 0; j < ys.length; j++) {
// if (xs[i] == 0 & ys[j] == 0) {
// continue;
// }

// int[] p = { xs[i], ys[j] };
// permute[count] = p;
// count += 1;
// }
// }
// return permute;
// }

// public static int[][] nextState(int[][] prev_state) {
// int[][] next_state = new int[prev_state.length][];

// for (int i = 0; i < prev_state.length; i++) {
// int[] new_row = new int[prev_state[i].length];
// for (int j = 0; j < prev_state[i].length; j++) {
// int count = 0;
// int[] xs;
// int[] ys;

// if (i == 0) {
// xs = new int[] { 0, 1, prev_state.length - 1 };
// } else if (i == (prev_state.length - 1)) {
// xs = new int[] { 0, -1, -prev_state.length + 1 };
// } else {
// xs = new int[] { -1, 0, 1 };
// }

// if (j == 0) {
// ys = new int[] { 0, 1, prev_state[i].length - 1 };
// } else if (j == (prev_state[i].length - 1)) {
// ys = new int[] { 0, -1, -prev_state[i].length + 1 };
// } else {
// ys = new int[] { -1, 0, 1 };
// }

// int[][] adj_coors = GameOfLife.permutation(xs, ys);

// for (int k = 0; k < adj_coors.length; k++) {
// int x = j + adj_coors[k][1];
// int y = i + adj_coors[k][0];
// // System.out.println("x: " + x + ", y: " + y + ", i: " + i + ", j: " + j);
// if (prev_state[y][x] == 1) {
// count += 1;
// }
// }

// if (count < 2 || count > 3) {
// new_row[j] = 0;
// } else if (count == 3) {
// new_row[j] = 1;
// } else {
// new_row[j] = prev_state[i][j];
// }
// }
// next_state[i] = new_row;
// }
// return next_state;
// }

// public static String matrixToOutput(int[][] matrix) {
// String output = new String("");
// for (int i = 0; i < matrix.length; i++) {
// for (int j = 0; j < matrix[i].length; j++) {
// if (matrix[i][j] == 0) {
// output = output + ".";
// } else {
// output = output + "*";
// }
// }
// output = output + "\n";
// }

// return output;
// }
// }
