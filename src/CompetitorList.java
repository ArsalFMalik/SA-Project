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
    public void freqCalc() {
        int[] result = new int[6];

        compList.forEach((comp) -> {
            String[] scores = comp.getScoreArray().split(",");
            for (int i = 0; i < 5; i++) {
                result[Integer.parseInt(scores[i].trim())]++;
            }
        });

        for (int i = 0; i < 6; i++) {
            System.out.println("No. of times a '" + i + "' was awarded: " + result[i]);
        }
    }

    // Finds a valid Competitor Number within the ArrayList
    public String findComp(int num) {
        String[] result = {"Competitor Number not found"};

        compList.forEach((comp) -> {
            if (comp.getCompNum() == num) {
                result[0] = comp.getShortDetails();
            }
        });
        
        return result[0];
    }

    public void highestOverall() {
        double[] highest = new double[1];
        compList.forEach((comp) -> {
            if (comp.getOverallScore() > highest[0]) {
                highest[0] = comp.getOverallScore();
            }
        });
        System.out.println("Details of the competitor(s) with the highest overall score: ");
        compList.forEach((comp) -> {
            if (comp.getOverallScore() == highest[0]) {
                System.out.println(comp.getFullDetails());
            }
        });
    }

    public void totalComps() {
        int[] count = new int[1];
        compList.forEach((comp) ->  {
            count[0]++;
        });
        System.out.println("Total number of competitors: " + count[0]);
    }

    public void avgOverall() {
        int[] count = new int[1];
        double[] overall = new double[1];
        compList.forEach((comp) -> {
            count[0]++;
            overall[0] += comp.getOverallScore();
        });
        overall[0] = overall[0] / count[0];
        System.out.println("Average overall score: " + overall[0]);
    }

    public void oldestComp() {
        int[] age = new int[1];
        compList.forEach((comp) -> {
            if (comp.getCompAge() > age[0]) {
                age[0] = comp.getCompAge();
            }
        });
        System.out.println("Age of oldest competitor: " + age[0]);
    }

    public void youngestComp() {
        int[] age = new int[1];
        age[0] = 999;
        compList.forEach((comp) -> {
            if (comp.getCompAge() < age[0]) {
                age[0] = comp.getCompAge();
            }
        });
        System.out.println("Age of youngest competitor: " + age[0]);
    }

    public void finalReport() {
        printTable();
        System.out.println("");
        highestOverall();
        System.out.println("");
        avgOverall();
        System.out.println("");
        totalComps();
        System.out.println("");
        oldestComp();
        System.out.println("");
        youngestComp();
        System.out.println("");
        freqCalc();
    }

    public void searchComp() {
        Scanner input = new Scanner(System.in);
        int[] num = new int[1];
        boolean[] flag = new boolean[1];
        try {
            System.out.println("Enter Competitor Number: ");
            num[0] = input.nextInt();
            compList.forEach((comp) -> {
                if (comp.getCompNum() == num[0]) {
                    System.out.println(comp.getShortDetails());
                    flag[0] = true;
                }
            });
            if (flag[0] == false) {
                throw new Exception("Invalid Competitor Number");
            }
        }
        catch (Exception e) {
            System.out.println("Invalid Competitor Number");
        }
    }
}