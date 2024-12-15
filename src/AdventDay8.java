import util.Vector2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static util.ArrayUtil.isInGrid;
import static util.FileIO.readGrid;

/**
 * <a href="https://adventofcode.com/2024/day/8">Advent of Code day eight</a>
 */
public class AdventDay8 {

    private static final String INPUT_FILE = "resources/input_8";

    public static int part1() {
        Map<Character, Set<Vector2>> antennas = getAntennas();
        Set<Vector2> antinodes = new HashSet<>();
        char[][] grid = readGrid(INPUT_FILE);
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
        char[][] grid = readGrid(INPUT_FILE);
        for (Set<Vector2> points : antennas.values()) {
            for (Vector2 point1 : points) {
                for (Vector2 point2 : points) {
                    if (!point1.equals(point2)) {
                        int factor = 0;
                        Vector2 antinode = antinodeProjection(point1, point2, factor);
                        while (isInGrid(grid, antinode)) {
                            grid[(int) antinode.getX()][(int) antinode.getY()] = '#';
                            antinodes.add(antinode);
                            factor++;
                            antinode = antinodeProjection(point1, point2, factor);
                        }
                    }
                }
            }
        }
        return antinodes.size();
    }

    private static Vector2 antinodeProjection(Vector2 p1, Vector2 p2) {
        return new Vector2(2 * p2.getX() - p1.getX(), 2 * p2.getY() - p1.getY());
    }

    private static Vector2 antinodeProjection(Vector2 p1, Vector2 p2, int factor) {
        Vector2 dist = p1.distance(p2);
        return new Vector2(p2.getX() - factor * dist.getX(), p2.getY() - factor * dist.getY());
    }

    public static Map<Character, Set<Vector2>> getAntennas() {
        char[][] grid = readGrid(INPUT_FILE);
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

}
