import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

public class AdventDay7 {

    private static final String INPUT = "resources/input_7";
    private static final File input = new File(INPUT);

    private static final List<BiFunction<BigInteger, BigInteger, BigInteger>> operations = List.of(
            BigInteger::add,
            BigInteger::multiply,
            AdventDay7::concat
    );

    private static BigInteger concat(BigInteger a, BigInteger b) {
        return new BigInteger(a.toString() + b.toString());
    }

    public static BigInteger part12() {
        try (Scanner scanner = new Scanner(input)) {
            BigInteger sum = BigInteger.ZERO;
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(":");
                BigInteger targetResult = new BigInteger(tokens[0].trim());
                BigInteger[] operands = Arrays.stream(tokens[1].trim().split(" "))
                        .map(BigInteger::new)
                        .toArray(BigInteger[]::new);
                if (canAchieveTarget(operands, targetResult)) {
                    sum = sum.add(targetResult);
                }
            }
            return sum;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + INPUT, e);
        }
    }

    private static boolean canAchieveTarget(BigInteger[] operands, BigInteger targetResult) {
        return calculate(operands, 1, operands[0], targetResult);
    }

    private static boolean calculate(BigInteger[] operands, int index, BigInteger currentResult,
                                     BigInteger targetResult) {
        if (index == operands.length) {
            return currentResult.equals(targetResult);
        }
        for (BiFunction<BigInteger, BigInteger, BigInteger> operation : operations) {
            BigInteger newResult = operation.apply(currentResult, operands[index]);
            if (calculate(operands, index + 1, newResult, targetResult)) {
                return true;
            }
        }
        return false;
    }

}
