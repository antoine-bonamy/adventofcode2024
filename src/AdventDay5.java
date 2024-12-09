import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

/**
 * <a href="https://adventofcode.com/2024/day/5">Advent of Code day five</a>
 */
public class AdventDay5 {

    private static final String INPUT = "resources/input_5";

    private static final List<List<Integer>> updates = getUpdates();
    private static final Map<Integer, Set<Integer>> rules = getPageRules();

    public static int part1() {
        return updates.stream().filter(AdventDay5::isValidUpdate)
                .mapToInt(update -> update.get(update.size() / 2))
                .sum();
    }

    public static int part2() {
        return updates.stream().filter(update -> !isValidUpdate(update))
                .map(update -> update.stream().sorted(ruleBasedComparator()).toList())
                .mapToInt(update -> update.get(update.size() / 2))
                .sum();
    }

    private static boolean isValidUpdate(List<Integer> update) {
        return IntStream.range(0, update.size() - 1)
                .allMatch(i -> rules.getOrDefault(update.get(i), Collections.emptySet()).contains(update.get(i + 1)));
    }

    private static Comparator<Integer> ruleBasedComparator() {
        return (page1, page2) ->
                rules.getOrDefault(page1, Collections.emptySet()).contains(page2) ? 1 : -1;
    }

    private static String read() {
        try {
            return Files.readString(Path.of(INPUT));
        } catch (IOException e) {
            throw new RuntimeException("Error reading input file: " + INPUT, e);
        }
    }

    private static List<List<Integer>> getUpdates() {
        String str = read().split("\\n\\n")[1];
        List<List<Integer>> updates = new ArrayList<>();
        for (String update : Arrays.stream(str.split("\\n")).toList()) {
            updates.add(Arrays.stream(update.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
        }
        return updates;
    }

    private static Map<Integer, Set<Integer>> getPageRules() {
        Map<Integer, Set<Integer>> rules = new HashMap<>();
        String[] lines = read().split("\\n\\n")[0].split("\r\n");
        Pattern p = Pattern.compile("(\\d+)\\|(\\d+)");
        for (String line : lines) {
            Matcher m = p.matcher(line);
            while (m.find()) {
                int key = parseInt(m.group(1));
                int value = parseInt(m.group(2));
                rules.putIfAbsent(key, new HashSet<>());
                rules.get(key).add(value);
            }
        }
        return rules;
    }

}
