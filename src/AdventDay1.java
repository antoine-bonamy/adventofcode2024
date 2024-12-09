import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * <a href="https://adventofcode.com/2024/day/1">Advent of Code day one</a>
 */
public class AdventDay1 {

    private static final File input = new File("resources/input_1");

    public static int part1() {
        try (Scanner scanner = new Scanner(input)) {
            PriorityQueue<Integer> pq1 = new PriorityQueue<>();
            PriorityQueue<Integer> pq2 = new PriorityQueue<>();
            while (scanner.hasNextInt()) {
                if (!scanner.hasNextInt()) {
                    throw new IllegalArgumentException("Le fichier doit contenir un nombre pair d'entiers !");
                }
                pq1.add(scanner.nextInt());
                pq2.add(scanner.nextInt());
            }
            if (pq1.size() != pq2.size() || pq1.isEmpty()) {
                throw new IllegalArgumentException("Les listes sont invalides !");
            }
            int distance = 0;
            while (!pq1.isEmpty()) {
                distance += Math.abs(pq1.poll() - pq2.poll());
            }
            return distance;
        } catch (FileNotFoundException e) {
            System.err.println("Erreur : Fichier non trouvé. Assurez-vous que le fichier 'resources/input_7' existe " +
                    "et est accessible.");
            return -1;
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur dans le fichier : " + e.getMessage());
            return -1;
        }
    }

    public static long part2() {
        try (Scanner scanner = new Scanner(input)) {
            Map<Integer, Integer> rightCountMap = new HashMap<>();
            List<Integer> leftList = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int left = scanner.nextInt();
                if (!scanner.hasNextInt()) {
                    throw new IllegalArgumentException("Le fichier doit contenir un nombre pair d'entiers !");
                }
                int right = scanner.nextInt();
                leftList.add(left);
                rightCountMap.put(right, rightCountMap.getOrDefault(right, 0) + 1);
            }
            long similarityScore = 0;
            for (int value : leftList) {
                int countInRight = rightCountMap.getOrDefault(value, 0);
                similarityScore += (long) value * countInRight;
            }
            return similarityScore;
        } catch (FileNotFoundException e) {
            System.err.println("Erreur : Fichier non trouvé. Assurez-vous que le fichier 'resources/input_7' existe " +
                    "et est accessible.");
            return -1;
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur dans le fichier : " + e.getMessage());
            return -1;
        }
    }

}
