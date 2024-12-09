import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <a href="https://adventofcode.com/2024/day/4">Advent of Code day four</a>
 */
public class AdventDay4 {

    private static final File input = new File("resources/input_4");

    static final int[][] directions = {
            {-1, 0},
            {-1, 1},
            {0, 1},
            {1, 1},
            {1, 0},
            {1, -1},
            {0, -1},
            {-1, -1}
    };
    static final char[] xmas = {'X', 'M', 'A', 'S'};
    static final char[][] grid = readGrid();

    public static char[][] readGrid() {
        List<char[]> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().toCharArray());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erreur : Fichier non trouvé. Assurez-vous que le fichier 'resources/input_7' existe " +
                    "et est accessible.");
            return new char[0][0];
        }
        return lines.toArray(new char[0][]);
    }

    public static int part1() {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != 'X') continue;
                for (int[] direction : directions) {
                    int newRow = r + direction[0];
                    int newCol = c + direction[1];
                    int i = 1;
                    while (inGrid(newRow, newCol) && i < xmas.length && grid[newRow][newCol] == xmas[i]) {
                        newRow += direction[0];
                        newCol += direction[1];
                        i++;
                    }
                    count += i == 4 ? 1 : 0;
                }
            }
        }
        return count;
    }

    public static int part2() {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != 'A' || onEdge(r, c)) continue;
                count += (grid[r - 1][c - 1] == 'M' && grid[r + 1][c + 1] == 'S' || grid[r - 1][c - 1] == 'S' && grid[r + 1][c + 1] == 'M') &&
                        (grid[r - 1][c + 1] == 'M' && grid[r + 1][c - 1] == 'S' || grid[r - 1][c + 1] == 'S' && grid[r + 1][c - 1] == 'M')
                        ? 1 : 0;
            }
        }
        return count;
    }

    private static boolean inGrid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static boolean onEdge(int row, int col) {
        return row == 0 || col == 0 || row == grid.length - 1 || col == grid[0].length - 1;
    }

}