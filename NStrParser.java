import java.io.*;
import java.util.regex.*;

public class NStrParser {
    public static void main(String[] args) {
        String inputFilePath = "ObjectModule.bsl";
        String outputFilePath = "ObjectModuleNStrExtract.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            int lineNumber = 0;
            Pattern pattern = Pattern.compile("NStr\\(\\s*\"([^\"]*)\"\\s*\\)");
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String nstrContent = matcher.group(1);
                    String[] languageParts = nstrContent.split("\\s*;\\s*");
                    for (String languagePart : languageParts) {
                        String[] languageCodeAndString = languagePart.split("\\s*=\\s*");
                        if (languageCodeAndString.length == 2) {
                            String languageCode = languageCodeAndString[0].trim();
                            String initialString = languageCodeAndString[1].trim().replace("'", "");
                            System.out.println("Извлечено: " + languageCode + ", " + initialString);
                            writer.write(lineNumber + ": " + languageCode + " : " + initialString);
                            writer.newLine();
                        }
                    }
                } else {
                    System.out.println("В строке " + lineNumber + " соответствий не найдено.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}