import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdventDay3 {

    private static final File input = new File("resources/input_3");
    private static final String DO_INST = "do()";
    private static final String DONT_INST = "don't()";
    private static final String MUL_INST = "mul";


    public static int process_simple_instructions() throws FileNotFoundException {
        Pattern p = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        try (Scanner scanner = new Scanner(input)) {
            int sum = 0;
            while (scanner.hasNextLine()) {
                Matcher m = p.matcher(scanner.nextLine());
                while (m.find()) {
                    sum += Integer.parseInt(m.group(1)) * Integer.parseInt(m.group(2));
                }
            }
            return sum;
        }
    }

    public static int process_complex_instructions() throws FileNotFoundException {
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");
        try (Scanner scanner = new Scanner(input)) {
            int sum = 0;
            boolean enable = true;
            while (scanner.hasNextLine()) {
                Matcher m = pattern.matcher(scanner.nextLine());
                while (m.find()) {
                    String match = m.group();
                    if (match.equals(DO_INST)) {
                        enable = true;
                    } else if (match.equals(DONT_INST)) {
                        enable = false;
                    } else if (match.startsWith(MUL_INST) && enable) {
                        sum += Integer.parseInt(m.group(1)) * Integer.parseInt(m.group(2));
                    }
                }
            }
            return sum;
        }
    }

}
