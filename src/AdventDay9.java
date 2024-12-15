import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.FileIO.readString;

/**
 * <a href="https://adventofcode.com/2024/day/9">Advent of Code day nine</a>
 */
public class AdventDay9 {

    private static final String INPUT_FILE = "resources/input_9";

    private static final String POINT = ".";

    public static BigInteger part1() {
        String[] disk = readString(INPUT_FILE).split("");
        List<String> filesystem = getFilesystem(disk);
        List<String> condensed = getCondensed(filesystem);
        return computeChecksum(condensed);
    }

    public static BigInteger part2() {
        String[] disk = readString(INPUT_FILE).split("");
        Map<Integer, Integer> idSize = getIdSize(disk);
        List<String> filesystem = new ArrayList<>(getFilesystem(disk));
        List<String> sortedDisk = new ArrayList<>(filesystem);
        for (int i = sortedDisk.size()-1; i > 0; i--) {
            int id = sortedDisk.get(i).equals(POINT) ? -1 : Integer.parseInt(sortedDisk.get(i));
            if (id != -1) {
                int idLength = idSize.get(id);
                int count = 0;
                for (int j = sortedDisk.indexOf(POINT); j < i; j++) {
                    ++count;
                    if (!sortedDisk.get(j).equals(POINT)) {
                        count = 0;
                    }
                    if (count == idLength) {
                        for (int k = 0; k < idLength; k++) {
                            sortedDisk.set(j-k, String.valueOf(id));
                            sortedDisk.set(i-k, POINT);
                        }
                        i = i - idLength + 1;
                        break;
                    }
                }
            }
        }
        return computeChecksum(sortedDisk);
    }

    private static Map<Integer, Integer> getIdSize(String[] disk) {
        Map<Integer, Integer> idSize = new HashMap<>();
        int id = 0;
        for (int k = 0; k < disk.length; k++) {
            if (k % 2 == 0) {
                idSize.put(id, Integer.parseInt(disk[k]));
                id++;
            }
        }
        return idSize;
    }


    private static List<String> getFilesystem(String[] disk) {
        List<String> filesystem = new ArrayList<>();
        int fileId = 0;
        for (int k = 0; k < disk.length; k++) {
            if (k % 2 == 0) {
                for (int j = 0; j < Integer.parseInt(disk[k]); j++) {
                    filesystem.add(String.valueOf(fileId));
                }
                fileId++;
            } else {
                for (int j = 0; j < Integer.parseInt(disk[k]); j++) {
                    filesystem.add(".");
                }
            }
        }
        return filesystem;
    }

    private static List<String> getCondensed(List<String> filesystem) {
        List<String> condensed = new ArrayList<>();
        for (int k = 0, r = filesystem.size() - 1; k - 1 < r; k++) {
            if (filesystem.get(k).equals(POINT)) {
                while (filesystem.get(r).equals(POINT)) {
                    r--;
                }
                condensed.add(filesystem.get(r));
                r--;
            } else {
                condensed.add(filesystem.get(k));
            }
        }
        for (int k = condensed.size(); k < filesystem.size(); k++) {
            condensed.add(POINT);
        }
        return condensed;
    }

    private static BigInteger computeChecksum(List<String> condensedFilesystem) {
        BigInteger checksum = BigInteger.ZERO;
        for (int k = 0; k < condensedFilesystem.size(); k++) {
            if (!condensedFilesystem.get(k).equals(POINT)) {
                checksum = checksum.add(BigInteger.valueOf((long) Integer.parseInt(condensedFilesystem.get(k)) * k));
            }
        }
        return checksum;
    }

}