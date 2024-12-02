import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AdventDay1 {

    private static final File input = new File("resources/input");

    public static int distance() throws FileNotFoundException {
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
        }
    }

    public static long similarity() throws FileNotFoundException {
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
        }
    }

}
