package by.hackaton.bookcrossing.util;

import java.util.List;
import java.util.stream.Collectors;

public class TranscriptionUtil {
    private static List<Character> latinLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            .chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    private static List<Character> specialLetters = "CScs"
            .chars().mapToObj(e -> (char) e).collect(Collectors.toList());

    public static String transcripting(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length() - 1; i++) {
            builder.append(transcriptLetter(text.charAt(i), text.charAt(i + 1)));
        }
        return builder.toString();
    }

    private static char transcriptLetter(char i, char j) {
        if (latinLetters.contains(i)) {
            if (specialLetters.contains(i)) {
                if (i == 'C') {
                    if (j == 'h') {
                        return 'ч';
                    } else {
                        return 'ц';
                    }
                }
                if (i == 'S') {
                    if (j == 'h') {
                        return 'ш';
                    } else {
                        return 's';
                    }
                }
            } else {
                switch (i) {
                    case 'A':
                        return 'А';
                    case 'B':
                        return 'Б';
                    case 'D':
                        return 'Д';
                    case 'E':
                        return 'Е';
                    case 'F':
                        return 'Ф';
                    case 'G':
                        return 'Г';
                    case 'H':
                        return 'Х';
                    case 'I':
                        return 'І';
                    case 'J':
                        return '_';
                    case 'K':
                        return 'К';
                    case 'L':
                        return 'Л';
                    case 'M':
                        return 'М';
                    case 'N':
                        return 'Н';
                    case 'O':
                        return 'О';
                    case 'P':
                        return 'П';
                    case 'Q':
                        return '_';
                    case 'R':
                        return 'Р';
                    case 'T':
                        return 'Т';
                    case 'U':
                        return 'У';
                    case 'V':
                        return 'В';
                    case 'W':
                        return '_';
                    case 'X':
                        return '_';
                    case 'Y':
                        return '_';
                    case 'Z':
                        return 'З';

                    case 'a':
                        return 'а';
                    case 'b':
                        return 'б';
                    case 'd':
                        return 'д';
                    case 'e':
                        return 'е';
                    case 'f':
                        return 'ф';
                    case 'g':
                        return 'г';
                    case 'h':
                        return 'х';
                    case 'i':
                        return 'і';
                    case 'j':
                        return '_';
                    case 'k':
                        return 'к';
                    case 'l':
                        return 'л';
                    case 'm':
                        return 'м';
                    case 'n':
                        return 'н';
                    case 'o':
                        return 'о';
                    case 'p':
                        return 'п';
                    case 'q':
                        return '_';
                    case 'r':
                        return 'р';
                    case 't':
                        return 'т';
                    case 'u':
                        return 'у';
                    case 'v':
                        return 'в';
                    case 'w':
                        return '_';
                    case 'x':
                        return '_';
                    case 'y':
                        return '_';
                    case 'z':
                        return 'з';
                }
            }
        } else {
            return i;
        }
        return '_';
    }
}
