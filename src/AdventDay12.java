import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdventDay12 {

    private static final String INPUT_FILE = "resources/input_12";
    private static final File input = new File(INPUT_FILE);

    public static int part1() {
        char[][] garden = read(input);
        boolean[][] visited = new boolean[garden.length][garden[0].length];
        int cost = 0;
        for (int x = 0; x < garden.length; x++) {
            for (int y = 0; y < garden[x].length; y++) {
                if (!visited[x][y]) {
                    char type = garden[x][y];
                    Region region = dfs(garden, visited, x, y, type);
                    cost += region.computePrice(false);
                }
            }
        }
        return cost;
    }

    public static int part2() {
        char[][] garden = read(input);
        boolean[][] visited = new boolean[garden.length][garden[0].length];
        int cost = 0;
        for (int x = 0; x < garden.length; x++) {
            for (int y = 0; y < garden[x].length; y++) {
                if (!visited[x][y]) {
                    Region region = dfs(garden, visited, x, y, garden[x][y]);
                    cost += region.computePrice(true);
                }
            }
        }
        return cost;
    }

    private static Region dfs(char[][] garden, boolean[][] visited, int x, int y, char type) {
        if (!isInGrid(garden, x, y)) {
            return new Region(1, 0, 0, type);
        }
        if (garden[x][y] != type) {
            return new Region(1, 0, 0, type);
        }
        if (visited[x][y]) {
            return new Region(0, 0, 0, type);
        }
        visited[x][y] = true;
        Region region = new Region(0, countCorners(garden, x, y, type), 1, type);
        region.extend(dfs(garden, visited, x + 1, y, type));
        region.extend(dfs(garden, visited, x - 1, y, type));
        region.extend(dfs(garden, visited, x, y + 1, type));
        region.extend(dfs(garden, visited, x, y - 1, type));
        return region;
    }


    private static int countCorners(char[][] garden, int x, int y, char type) {
        int corners = 0;
        corners += checkCorner(garden, x - 1, y - 1, x - 1, y, x, y - 1, type); // Coin haut gauche
        corners += checkCorner(garden, x + 1, y - 1, x + 1, y, x, y - 1, type); // Coin bas gauche
        corners += checkCorner(garden, x - 1, y + 1, x - 1, y, x, y + 1, type); // Coin haut droit
        corners += checkCorner(garden, x + 1, y + 1, x + 1, y, x, y + 1, type); // Coin bas droit
        return corners;
    }

    private static int checkCorner(char[][] garden, int diagX, int diagY, int adjX1, int adjY1, int adjX2, int adjY2, char type) {
        boolean diagExists = isInGrid(garden, diagX, diagY);
        boolean adj1Exists = isInGrid(garden, adjX1, adjY1);
        boolean adj2Exists = isInGrid(garden, adjX2, adjY2);
        char adj1Type = adj1Exists ? garden[adjX1][adjY1] : '\0';
        char adj2Type = adj2Exists ? garden[adjX2][adjY2] : '\0';
        if ((!adj1Exists && !adj2Exists)
                || (!adj1Exists && adj2Type != type)
                || (!adj2Exists && adj1Type != type)
                || (adj1Exists && adj1Type != type && adj2Exists && adj2Type != type)) {
            return 1;
        }
        if (adj1Exists && adj2Exists && adj1Type == type && adj2Type == type
                && (!diagExists || garden[diagX][diagY] != type)) {
            return 1;
        }
        return 0;
    }

    private static boolean isInGrid(char[][] garden, int x, int y) {
        return x >= 0 && x < garden.length && y >= 0 && y < garden[x].length;
    }

    private static char[][] read(File input) {
        List<char[]> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().toCharArray());
            }
            return lines.toArray(new char[0][]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading input file: " + input.getPath(), e);
        }
    }

    public static class Region {

        int perimeter;
        int numberOfSide;
        int area;
        char type;

        public Region(int perimeter, int numberOfSide, int area, char type) {
            this.perimeter = perimeter;
            this.numberOfSide = numberOfSide;
            this.area = area;
            this.type = type;
        }

        public int computePrice(boolean bulk) {
            return bulk ?numberOfSide * area : area * perimeter;
        }

        public void extend(Region r) {
            this.perimeter += r.perimeter;
            this.area += r.area;
            this.numberOfSide += r.numberOfSide;
        }

    }

}
