import java.io.*;
import java.util.*;

public class CompetitorList {
    public ArrayList<Competitor> compList;

    public CompetitorList(String path) {
        compList = new ArrayList<>();
        loadJson(path);
    }

    // Reads JSON file and saves it as a StringBuilder
    private void loadJson(String path) {
        StringBuilder jsonString = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        parseJson(jsonString.toString());
    }

    // Parses the converted JSON file and stores each competitor seperately in compList
    private void parseJson(String jsonString) {
        jsonString = jsonString.trim();
        if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
            jsonString = jsonString.substring(1, jsonString.length() - 1).trim();
        }

        String[] elements = jsonString.split("\\},\\s*\\{");
        for (String element : elements) {
            if (!element.startsWith("{")) {
                element = "{" + element;
            }
            if (!element.endsWith("}")) {
                element = element + "}";
            }

            Competitor competitor = parseCompetitor(element);
            compList.add(competitor);
        }
    }

    // Parses individual competitors from the JSON file, stores the appropriate information, and returns a new Competitor object
    private Competitor parseCompetitor(String jsonElement) {
        jsonElement = jsonElement.trim();
        if (jsonElement.startsWith("{") && jsonElement.endsWith("}")) {
            jsonElement = jsonElement.substring(1, jsonElement.length() - 1).trim();
        }

        int compNum = 0;
        String compName = "";
        int compAge = 0;
        String compEmail = "";
        String level = "";
        int[] scores = new int[5];
        double overallScore = 0.0;

        String[] fields = jsonElement.split(",\\s*\"");

        for (String field : fields) {
            String[] keyValue = field.split("\":");
            if (keyValue.length != 2) {
                continue;
            }

            String key = keyValue[0].replace("\"", "").trim();
            String value = keyValue[1].replace("\"", "").trim();

            switch (key) {
                case "compNum":
                    compNum = Integer.parseInt(value);
                    break;
                case "compName":
                    compName = value;
                    break;
                case "compAge":
                    compAge = Integer.parseInt(value);
                    break;
                case "compEmail":
                    compEmail = value;
                    break;
                case "level":
                    level = value;
                    break;
                case "scores":
                    value = value.substring(1, value.length() - 1);
                    String[] scoreStrings = value.split(",\\s*");
                    for (int i = 0; i < scoreStrings.length; i++) {
                        scores[i] = Integer.parseInt(scoreStrings[i]);
                    }
                    break;
                case "overallScore":
                    overallScore = Double.parseDouble(value);
                    break;
            }
        }
        return new Competitor(compNum, compName, compAge, compEmail, level, scores, overallScore);
    }

    // Prints a table of all competitors and their details
    public void printTable() {
        String format = "%-10s %-15s %-5s %-25s %-12s %-15s %-12s%n";
        System.out.printf(format, "Number", "Name", "Age", "Email", "Level", "Scores", "Overall Score");
        System.out.println("--------------------------------------------------------------------------------------------");
        for (Competitor competitor : compList) {
            System.out.printf(format, 
                competitor.getCompNum(),
                competitor.getCompName(),
                competitor.getCompAge(),
                competitor.getCompEmail(),
                competitor.getCompLevel(),
                competitor.getScoreArray(),
                competitor.getOverallScore());
        }
    }

    // Calculates frequency of each individual score
    public int[] freqCalc(){
        int[] result = new int[6];

        compList.forEach((comp) -> {
            String[] scores = comp.getScoreArray().split(",");
            for(int i = 0; i < 5; i++) {
                result[Integer.parseInt(scores[i].trim())]++;
            }
        });

        return result;
    }

    // Finds a valid Competitor Number within the ArrayList
    public String findComp(int num) {
        String[] result = {"Competitor Number not found"};

        compList.forEach((comp) -> {
            if(comp.getCompNum() == num) {
                result[0] = comp.getShortDetails();
            }
        });
        
        return result[0];
    }
}