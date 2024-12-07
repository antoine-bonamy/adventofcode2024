import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AdventDay6 {

    private static final String INPUT = "resources/input_6";
    private static final File input = new File("resources/input_6");

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
        char[][] grid = read();
        int currentX = -1;
        int currentY = -1;
        int currentDirection = 0;
        // Find starting coord
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] == GUARD) {
                    currentX = x;
                    currentY = y;
                    break;
                }
            }
            if (currentX != -1) break;
        }
        // Estimate the guardian path
        int pathCount = 0;
        while (isInBounds(grid, currentX, currentY)) {
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

    //TODO: OPTIMISATION !!!!!
    public static int part2() {
        char[][] originalGrid = read();
        int[] guardPosition = findGuard(originalGrid);
        if (guardPosition == null) {
            throw new IllegalStateException("Guard not found in the grid!");
        }

        int startX = guardPosition[0];
        int startY = guardPosition[1];
        int loopCount = 0;

        // Parcourir chaque position pour placer une obstruction potentielle
        for (int x = 0; x < originalGrid.length; x++) {
            for (int y = 0; y < originalGrid[x].length; y++) {
                // Ignorer les positions déjà occupées ou la position de départ
                if (originalGrid[x][y] != '.' || (x == startX && y == startY)) {
                    continue;
                }

                // Simuler le comportement du garde avec une obstruction à (x, y)
                char[][] grid = copyGrid(originalGrid);
                grid[x][y] = OBSTACLE;

                if (isGuardStuck(grid, startX, startY)) {
                    loopCount++;
                }
            }
        }

        return loopCount;
    }

    private static boolean isGuardStuck(char[][] grid, int startX, int startY) {
        int x = startX, y = startY, direction = 0;

        // Utiliser un ensemble pour détecter les boucles
        Set<String> visited = new HashSet<>();

        while (isInBounds(grid, x, y)) {
            String state = x + "," + y + "," + direction;
            if (visited.contains(state)) {
                return true; // Le garde est bloqué dans une boucle
            }
            visited.add(state);

            if (grid[x][y] == OBSTACLE) {
                x -= directions[direction][0];
                y -= directions[direction][1];
                direction = (direction + 1) % directions.length;
            } else {
                x += directions[direction][0];
                y += directions[direction][1];
            }
        }

        return false; // Le garde a quitté la grille sans être bloqué
    }

    private static int[] findGuard(char[][] grid) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] == GUARD) {
                    return new int[]{x, y};
                }
            }
        }
        return null; // Guard introuvable
    }

    private static char[][] copyGrid(char[][] grid) {
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }

    private static boolean isInBounds(char[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[x].length;
    }

    private static int turnRight(int direction) {
        return (direction + 1) % directions.length;
    }

    public static char[][] read() {
        List<char[]> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().toCharArray());
            }
            return lines.toArray(new char[0][]);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading input file: " + INPUT, e);
        }
    }

}
