import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static util.ArrayUtil.copyGrid;
import static util.ArrayUtil.isInGrid;
import static util.FileIO.readGrid;

/**
 * <a href="https://adventofcode.com/2024/day/6">Advent of Code day six</a>
 */
public class AdventDay6 {

    private static final String INPUT_FILE = "resources/input_6";
    private static final int[][] directions = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1},
    };

    private static final char GUARD = '^';
    private static final char OBSTACLE = '#';
    private static final char PATH = 'X';
    private static final char NEW_OBSTACLE = 'O';

    public static int part1() {
        char[][] grid = readGrid(INPUT_FILE);
        int[] guardPosition = findGuard(grid);
        int currentX = guardPosition[0];
        int currentY = guardPosition[1];
        int currentDirection = 0;

        // Estimate the guardian path
        int pathCount = 0;
        while (isInGrid(grid, currentX, currentY)) {
            char cell = grid[currentX][currentY];
            if (cell == OBSTACLE) {
                currentX -= directions[currentDirection][0];
                currentY -= directions[currentDirection][1];
                currentDirection = turnRight(currentDirection);
            } else {
                if (cell != PATH) {
                    grid[currentX][currentY] = PATH;
                    pathCount++;
                }
            }
            currentX += directions[currentDirection][0];
            currentY += directions[currentDirection][1];
        }
        return pathCount;
    }


    public static int part2() {
        char[][] originalGrid = readGrid(INPUT_FILE);
        int[] guardPosition = findGuard(originalGrid);
        int startX = guardPosition[0];
        int startY = guardPosition[1];
        int loopCount = 0;
        Set<List<Integer>> neighbours = getNeighbour(originalGrid, startX, startY);
        for (int x = 0; x < originalGrid.length; x++) {
            for (int y = 0; y < originalGrid[x].length; y++) {
                if (originalGrid[x][y] != '.' || (x == startX && y == startY)) {
                    continue;
                }
                if (!neighbours.contains(Arrays.asList(x, y))) {
                    continue;
                }
                originalGrid[x][y] = NEW_OBSTACLE;
                if (isGuardStuck(originalGrid, startX, startY)) {
                    loopCount++;
                }
                originalGrid[x][y] = '.';
            }
        }
        return loopCount;
    }

    private static Set<List<Integer>> getNeighbour(final char[][] grid, int startX, int startY) {
        char[][] pathGrid = copyGrid(grid);
        int currentX = startX;
        int currentY = startY;
        int currentDirection = 0;
        Set<List<Integer>> neighbour = new HashSet<>();
        while (isInGrid(pathGrid, currentX, currentY)) {
            char cell = pathGrid[currentX][currentY];
            if (cell == OBSTACLE) {
                currentX -= directions[currentDirection][0];
                currentY -= directions[currentDirection][1];
                currentDirection = turnRight(currentDirection);
            } else {
                if (cell != PATH) {
                    pathGrid[currentX][currentY] = PATH;
                    for (int[] direction : directions) {
                        neighbour.add(Arrays.asList(currentX + direction[0], currentY + direction[1]));
                    }
                }
            }
            currentX += directions[currentDirection][0];
            currentY += directions[currentDirection][1];
        }
        return neighbour;
    }

    //TODO: Méthode mal optimisée, ou trop d'appel effectué. Utiliser algo du lièvre et de la tortue
    private static boolean isGuardStuck(char[][] grid, int startX, int startY) {
        int x = startX, y = startY, direction = 0;
        Set<String> visited = new HashSet<>();
        while (isInGrid(grid, x, y)) {
            String state = x + "," + y + "," + direction;
            if (visited.contains(state)) {
                return true;
            }
            visited.add(state);
            if (grid[x][y] == OBSTACLE || grid[x][y] == NEW_OBSTACLE) {
                x -= directions[direction][0];
                y -= directions[direction][1];
                direction = (direction + 1) % directions.length;
            } else {
                x += directions[direction][0];
                y += directions[direction][1];
            }
        }
        return false;
    }

    private static int[] findGuard(char[][] grid) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] == GUARD) {
                    return new int[]{x, y};
                }
            }
        }
        throw new IllegalStateException("Le garde n'a pas été trouvé !");
    }

    private static int turnRight(int direction) {
        return (direction + 1) % directions.length;
    }


}
