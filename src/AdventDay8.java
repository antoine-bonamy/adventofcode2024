import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * <a href="https://adventofcode.com/2024/day/8">Advent of Code day eight</a>
 */
public class AdventDay8 {

    public record Vector2(int x, int y) {
        public Vector2 distance(Vector2 other) {
            return new Vector2(other.x - x, other.y - y);
        }
    }

    private static final String INPUT_FILE = "resources/input_8";
    private static final File input = new File(INPUT_FILE);

    public static int part1() {
        Map<Character, Set<Vector2>> antennas = getAntennas();
        Set<Vector2> antinodes = new HashSet<>();
        char[][] grid = read();
        for (Set<Vector2> points : antennas.values()) {
            for (Vector2 point1 : points) {
                for (Vector2 point2 : points) {
                    if (!point1.equals(point2) && isInGrid(grid, antinodeProjection(point1, point2))) {
                        antinodes.add(antinodeProjection(point1, point2));
                    }
                }
            }
        }
        return antinodes.size();
    }

    public static int part2() {
        Map<Character, Set<Vector2>> antennas = getAntennas();
        Set<Vector2> antinodes = new HashSet<>();
        char[][] grid = read();
        for (Set<Vector2> points : antennas.values()) {
            for (Vector2 point1 : points) {
                for (Vector2 point2 : points) {
                    if (!point1.equals(point2)) {
                        int factor = 0;
                        Vector2 antinode = antinodeProjection(point1, point2, factor);
                        while (isInGrid(grid, antinode)) {
                            grid[antinode.x][antinode.y] = '#';
                            antinodes.add(antinode);
                            factor++;
                            antinode = antinodeProjection(point1, point2, factor);
                        }
                    }
                }
            }
        }
        printGrid(grid);
        return antinodes.size();
    }

    private static Vector2 antinodeProjection(Vector2 p1, Vector2 p2) {
        return new Vector2(2 * p2.x - p1.x, 2 * p2.y - p1.y);
    }

    private static Vector2 antinodeProjection(Vector2 p1, Vector2 p2, int factor) {
        Vector2 dist = p1.distance(p2);
        return new Vector2(p2.x - factor * dist.x, p2.y - factor * dist.y);
    }

    public static Map<Character, Set<Vector2>> getAntennas() {
        char[][] grid = read();
        Map<Character, Set<Vector2>> antennas = new HashMap<>();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                char cell = grid[x][y];
                if (Character.isDigit(cell) || Character.isAlphabetic(cell)) {
                    Vector2 point = new Vector2(x, y);
                    antennas.compute(cell, (_, points) -> {
                        if (points == null) {
                            points = new HashSet<>();
                        }
                        points.add(point);
                        return points;
                    });
                }
            }
        }
        return antennas;

    }

    public static boolean isInGrid(char[][] grid, Vector2 vector) {
        return vector.x >= 0 && vector.x < grid.length && vector.y >= 0 && vector.y < grid[vector.x].length;
    }

    public static char[][] read() {
        List<char[]> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().toCharArray());
            }
            return lines.toArray(new char[0][]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading input file: " + INPUT_FILE, e);
        }
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
