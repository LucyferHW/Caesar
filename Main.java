import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Arbeitsverzeichnis: " + System.getProperty("user.dir"));
        Scanner scanner = new Scanner(System.in);
        boolean weitermachen;
        do {
            InputResult inputResult = getInputText(scanner);

            System.out.println("\nWas möchtest du damit machen?");
            int option = getOption(scanner);

            handleOption(option, inputResult, scanner);

            weitermachen = askRepeat(scanner);
        } while (weitermachen);

        scanner.close();
        System.out.println("Programm beendet.");
    }

    private static boolean askRepeat(Scanner scanner) {
        while (true) {
            System.out.print("\nMöchtest du noch eine Nachricht verarbeiten? (j/n): ");
            String antwort = scanner.nextLine().trim().toLowerCase();
            if (antwort.equals("j") || antwort.equals("ja")) {
                return true;
            } else if (antwort.equals("n") || antwort.equals("nein")) {
                return false;
            } else {
                System.out.println("Bitte gib 'j' (ja) oder 'n' (nein) ein.");
            }
        }
    }

    // Liefert jetzt auch den Dateipfad mit zurück (falls Datei)
    private static InputResult getInputText(Scanner scanner) {
        System.out.println("Möchtest du eine Nachricht aus der Konsole (1) oder aus einer Datei (2) laden?");
        int inputSource = askForInputSource(scanner);

        if (inputSource == 1) {
            return new InputResult(getConsoleText(scanner), null);
        } else {
            return getFileText(scanner);
        }
    }

    private static int askForInputSource(Scanner scanner) {
        int inputSource = -1;
        while (true) {
            System.out.print("Bitte wähle 1 (Konsole) oder 2 (Datei): ");
            if (scanner.hasNextInt()) {
                inputSource = scanner.nextInt();
                scanner.nextLine(); // puffer leeren
                if (inputSource == 1 || inputSource == 2) return inputSource;
            } else {
                scanner.next();
            }
            System.out.println("Bitte gib 1 oder 2 ein!");
        }
    }

    private static String getConsoleText(Scanner scanner) {
        System.out.print("\nBitte gib die Nachricht ein: ");
        return scanner.nextLine();
    }

    // Liefert hier nun Inhalt + Dateipfad zurück
    private static InputResult getFileText(Scanner scanner) {
        while (true) {
            System.out.print("Bitte gib den Dateipfad ein: ");
            String dateipfad = scanner.nextLine();
            String result = readFileAsString(dateipfad);
            if (result != null) {
                System.out.println("\nInhalt der Datei:\n" + result + "\n");
                return new InputResult(result, dateipfad);
            } else {
                System.out.println("Datei konnte nicht gelesen werden. Versuch es erneut.");
            }
        }
    }

    private static String readFileAsString(String dateipfad) {
        StringBuilder sb = new StringBuilder();
        try (Scanner fileScanner = new Scanner(new File(dateipfad))) {
            while (fileScanner.hasNextLine()) {
                sb.append(fileScanner.nextLine());
                sb.append(System.lineSeparator());
            }
            return sb.toString().trim();
        } catch (FileNotFoundException e) {
            System.out.println("Datei \"" + dateipfad + "\" nicht gefunden!");
            return null;
        }
    }

    private static int getOption(Scanner scanner) {
        int option = -1;
        while (true) {
            System.out.println("Bitte wähle eine Option durch Eingabe der Zahl:");
            System.out.println("1. Code");
            System.out.println("2. Decode");
            System.out.println("3. Table Code");
            System.out.println("4. Table Decode");
            System.out.print("Deine Auswahl: ");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                if (option >= 1 && option <= 4) {
                    scanner.nextLine(); // Rest der Zeile entfernen
                    return option;
                } else {
                    System.out.println("Ungültige Auswahl! Bitte gib 1, 2, 3 oder 4 ein.\n");
                }
            } else {
                System.out.println("Bitte gib eine gültige Zahl ein (1, 2, 3 oder 4).\n");
                scanner.next();
            }
        }
    }

    private static int getShift(Scanner scanner) {
        int shift;
        while (true) {
            System.out.print("\nBitte gib den shift ein: ");
            if (scanner.hasNextInt()) {
                shift = scanner.nextInt();
                scanner.nextLine(); // Eingabepuffer leeren
                return shift;
            } else {
                System.out.println("Bitte gib eine gültige Zahl ein.\n");
                scanner.next();
            }
        }
    }

    // Jetzt mit Output als Datei, wenn Eingabe aus Datei kam
    private static void handleOption(int option, InputResult inputResult, Scanner scanner) {
        String output = null;
        boolean hatOutput = false;
        switch (option) {
            case 1:
                int shiftCode = getShift(scanner);
                output = new Caesar(inputResult.text, null, shiftCode).code(shiftCode);
                System.out.println("\nCoded: " + output);
                hatOutput = true;
                break;
            case 2:
                int shiftDecode = getShift(scanner);
                output = new Caesar(null, inputResult.text, shiftDecode).decode(shiftDecode);
                System.out.println("\nDecoded: " + output);
                hatOutput = true;
                break;
            case 3:
                output = new Hack(new Caesar(inputResult.text, null, 0)).brutForce(CodeEnum.CODE).toString();
                System.out.println("\nCoded: " + output);
                break;
            case 4:
                output = new Hack(new Caesar(null, inputResult.text, 0)).brutForce(CodeEnum.DECODE).toString();
                System.out.println("\nCoded: " + output);
                break;
        }

        // Datei-Schreiblogik: Nur wenn Eingabe aus Datei und tatsächlich Output erzeugt wurde
        if (inputResult.sourceFilePath != null && hatOutput) {
            String zielPfad = createOutputFilePath(inputResult.sourceFilePath, option);
            boolean success = writeStringToFile(zielPfad, output);
            if (success) {
                System.out.println("Ergebnis wurde in die Datei \"" + zielPfad + "\" geschrieben.");
            } else {
                System.out.println("Fehler beim Schreiben in die Datei \"" + zielPfad + "\"!");
            }
        }
    }

    // Erzeuge neuen Dateipfad, z.B. aus "test.txt" -> "test_Coded.txt"
    private static String createOutputFilePath(String original, int option) {
        File file = new File(original);
        String name = file.getName();
        int dot = name.lastIndexOf('.');
        String base = (dot != -1) ? name.substring(0, dot) : name;
        String ext = (dot != -1) ? name.substring(dot) : "";
        String suffix = "";
        switch (option) {
            case 1:
                suffix = "_Coded";
                break;
            case 2:
                suffix = "_Decoded";
                break;
            case 3:
                suffix = "_TableCoded";
                break;
            case 4:
                suffix = "_TableDecoded";
                break;
            default:
                suffix = "_Output";
        }
        // Im selben Verzeichnis wie Ursprungsdatei:
        return file.getParent() != null ?
                file.getParent() + File.separator + base + suffix + ext :
                base + suffix + ext;
    }

    // Text in Zieldatei schreiben
    private static boolean writeStringToFile(String filePath, String content) {
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write(content);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Datenträgerklasse, damit du Inhalt und Dateiinfo gemischt zurückgeben kannst
    private static class InputResult {
        String text;
        String sourceFilePath; // null, falls aus Konsole

        InputResult(String text, String sourceFilePath) {
            this.text = text;
            this.sourceFilePath = sourceFilePath;
        }
    }
}