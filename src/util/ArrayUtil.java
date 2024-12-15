package util;

public class ArrayUtil {

    public static boolean isInGrid(char[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[x].length;
    }

    public static boolean isInGrid(char[][] grid, Vector2 point) {
        return isInGrid(grid, (int) point.getX(), (int) point.getY());
    }

    public static char[][] copyGrid(char[][] grid) {
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }

    public static void printGrid(char[][] grid) {
        System.out.println("height=" + grid.length + " width=" + grid[0].length);
        for (char[] lines : grid) {
            for (char c : lines) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

}
