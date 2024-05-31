import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public String printTable() {
        StringBuilder table = new StringBuilder();
        String format = "%-10s %-15s %-5s %-25s %-12s %-15s %-12s%n";
        
        // Append the header
        table.append(String.format(format, "Number", "Name", "Age", "Email", "Level", "Scores", "Overall Score"));
        table.append("--------------------------------------------------------------------------------------------\n");
        
        // Append the details of each competitor
        for (Competitor competitor : compList) {
            table.append(String.format(format, 
                competitor.getCompNum(),
                competitor.getCompName(),
                competitor.getCompAge(),
                competitor.getCompEmail(),
                competitor.getCompLevel(),
                competitor.getScoreArray(),
                competitor.getOverallScore()));
        }
        
        return table.toString();
    }

    // Calculates frequency of each individual score
    public String freqCalc() {
        int[] result = new int[6];
        StringBuilder frequency = new StringBuilder();
        String freq = "";
        compList.forEach((comp) -> {
            String[] scores = comp.getScoreArray().split(",");
            for (int i = 0; i < 5; i++) {
                result[Integer.parseInt(scores[i].trim())]++;
            }
        });

        for (int i = 0; i < 6; i++) {
            freq = "No. of times a '" + i + "' was awarded: " + result[i];
            frequency.append(freq);
            frequency.append("\n");
        }

        return frequency.toString();
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

    public String highestOverall() {
        double[] highest = new double[1];
        StringBuilder details = new StringBuilder();
        details.append("Details of the competitor(s) with the highest overall score: ");
        details.append("\n");
        compList.forEach((comp) -> {
            if (comp.getOverallScore() > highest[0]) {
                highest[0] = comp.getOverallScore();
            }
        });
        compList.forEach((comp) -> {
            if (comp.getOverallScore() == highest[0]) {
                 details.append(comp.getFullDetails());
            }
        });
        return details.toString();
    }

    public int totalComps() {
        int[] count = new int[1];
        compList.forEach((comp) ->  {
            count[0]++;
        });
        return count[0];
    }

    public String totalString() {
        String total = "Total number of Competitors: " + totalComps();
        return total;
    }

    public String avgOverall() {
        int count = totalComps();
        double overall = 0.0;
        String avg = "";
        overall = overall / count;
        avg = "Average overall score: " + overall;
        return avg;
    }

    public String oldestComp() {
        int[] age = new int[1];
        String oldest = "";
        compList.forEach((comp) -> {
            if (comp.getCompAge() > age[0]) {
                age[0] = comp.getCompAge();
            }
        });
        oldest = "Age of oldest competitor: " + age[0];
        return oldest;
    }

    public String youngestComp() {
        int[] age = new int[1];
        age[0] = 999;
        String youngest = "";
        compList.forEach((comp) -> {
            if (comp.getCompAge() < age[0]) {
                age[0] = comp.getCompAge();
            }
        });
        youngest = "Age of youngest competitor: " + age[0];
        return youngest;
    }

    public void finalReport() {
        Path reportPath = Paths.get(".\\reports", "report.txt");
        String reportName = reportPath.toString();
        StringBuilder report = new StringBuilder();
        try {
            File reportFile =  new File(reportName);
            reportFile.createNewFile();
            FileWriter reportWriter = new FileWriter(reportName);
            report.append(printTable());
            report.append("\n");
            report.append("\n");
            report.append(highestOverall());
            report.append("\n");
            report.append("\n");
            report.append(avgOverall());
            report.append("\n");
            report.append("\n");
            report.append(totalString());
            report.append("\n");
            report.append("\n");
            report.append(oldestComp());
            report.append("\n");
            report.append("\n");
            report.append(youngestComp());
            report.append("\n");
            report.append("\n");
            report.append(freqCalc());
            reportWriter.write(report.toString());
            reportWriter.close();
        }
        catch (IOException e){
            System.out.println("An error occurred");
        }
    }

    public Competitor searchComp(int num) {
        Competitor[] result = new Competitor[1];

        compList.forEach((comp) -> {
            if (comp.getCompNum() == num) {
                result[0] = comp;
            }
        });
        
        return result[0];
    }
}