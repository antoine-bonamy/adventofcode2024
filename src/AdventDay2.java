import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * <a href="https://adventofcode.com/2024/day/2">Advent of Code day two</a>
 */
public class AdventDay2 {

    private static final File input = new File("resources/input_2");

    public static int part1And2(int tolerance) {
        try (Scanner scanner = new Scanner(input)) {
            int nbSafeReports = 0;
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" ");
                if (isReportSafe(parts, tolerance)) {
                    nbSafeReports++;
                }
            }
            return nbSafeReports;
        } catch (FileNotFoundException e) {
            System.err.println("Erreur : Fichier non trouvé. Assurez-vous que le fichier 'resources/input_7' existe " +
                    "et est accessible.");
            return -1;
        }
    }

    private static boolean isReportSafe(String[] levels, int tolerance) {
        int errorCount = 0;
        int previous = Integer.parseInt(levels[0]);
        int direction = Integer.signum(Integer.parseInt(levels[1]) - previous);
        for (int i = 1; i < levels.length; i++) {
            int current = Integer.parseInt(levels[i]);
            int diff = current - previous;
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3 || Integer.signum(diff) != direction) {
                errorCount++;
                if (errorCount > tolerance) {
                    return false;
                }
            } else {
                direction = Integer.signum(diff);
            }
            previous = current;
        }
        return true;
    }


}
