import static util.ArrayUtil.isInGrid;
import static util.FileIO.readGrid;

/**
 * <a href="https://adventofcode.com/2024/day/4">Advent of Code day four</a>
 */
public class AdventDay4 {

    private static final String INPUT_FILE = "resources/input_4";

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
    static final char[][] grid = readGrid(INPUT_FILE);

    public static int part1() {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != 'X') continue;
                for (int[] direction : directions) {
                    int newRow = r + direction[0];
                    int newCol = c + direction[1];
                    int i = 1;
                    while (isInGrid(grid, newRow, newCol) && i < xmas.length && grid[newRow][newCol] == xmas[i]) {
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


    private static boolean onEdge(int row, int col) {
        return row == 0 || col == 0 || row == grid.length - 1 || col == grid[0].length - 1;
    }

}