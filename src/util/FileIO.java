package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {

    /**
     * Lit le fichier de chemin input et renvoi le contenu sous forme d'une seule chaïne de caractère.
     * @param input Le chemin du fichier
     * @return Une chaïne représentant le contenu du fichier.
     */
    public static String readString(String input) {
        try {
            return Files.readString(Path.of(input));
        } catch (IOException e) {
            throw new RuntimeException("Error reading input file: " + input, e);
        }
    }

    /**
     * Lis le fichier input et renvoi l'ensemble du contenu sour forme d'un tableau à deux dimensions (grille) de caractère.
     * @param input Le fichier à lire
     * @return Un tableau à deux dimensions contenant l'ensemble du fichier.
     */
    public static char[][] readGrid(String input) {
        List<char[]> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(input))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().toCharArray());
            }
            return lines.toArray(new char[0][]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading input file: " + input, e);
        }
    }

}
