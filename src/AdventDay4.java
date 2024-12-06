import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdventDay4 {

    static int[][] directions = {
            {-1, 0},
            {-1, 1},
            {0, 1},
            {1, 1},
            {1, 0},
            {1, -1},
            {0, -1},
            {-1, -1}
    };

    static char[] xmas = { 'X', 'M', 'A', 'S' };


    public static void main(String[] args) throws FileNotFoundException {
        char[][] grid = readGrid("resources/input_4");
        int result = countXMASPatterns_2(grid);
        System.out.println("Number of X-MAS patterns: " + result);
    }

    public static char[][] readGrid(String filename) throws FileNotFoundException {
        List<char[]> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().toCharArray());
            }
        }
        return lines.toArray(new char[0][]);
    }

    public static int countXMASPatterns_1(char[][] grid) {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != 'X') continue;
                for (int[] direction : directions) {
                    int newRow = r + direction[0];
                    int newCol = c + direction[1];
                    int i = 1;
                    while (inGrid(grid, newRow, newCol) && i < xmas.length && grid[newRow][newCol] == xmas[i]) {
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

    public static int countXMASPatterns_2(char[][] grid) {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != 'A' || onEdge(grid, r, c)) continue;
                count += (grid[r - 1][c - 1] == 'M' && grid[r + 1][c + 1] == 'S' || grid[r - 1][c - 1] == 'S' && grid[r + 1][c + 1] == 'M') &&
                        (grid[r - 1][c + 1] == 'M' && grid[r + 1][c - 1] == 'S' || grid[r - 1][c + 1] == 'S' && grid[r + 1][c - 1] == 'M')
                        ? 1 : 0;
            }
        }
        return count;
    }

    private static boolean inGrid(char[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static boolean onEdge(char[][] grid, int row, int col) {
        return row == 0 || col == 0 || row == grid.length - 1 || col == grid[0].length - 1;
    }

}