import java.util.Arrays;

public class Competitor {
    public int compNum;
    private String compName;
    private int compAge;
    private String compEmail;
    private int[] scores;
    private double overallScore;
    private String level;

    // Constructor
    public Competitor(int compNum, String compName, int compAge, String compEmail, String level) {
        this.compNum = compNum;
        this.compName = compName;
        this.compAge = compAge;
        this.compEmail = compEmail;
        this.scores = new int[5]; 
        this.overallScore = 0.0;
        this.level = level;
    }

    // Second Constructor
    public Competitor(int compNum, String compName, int compAge, String compEmail, String level, int[] scores, double overallScore) {
        this.compNum = compNum;
        this.compName = compName;
        this.compAge = compAge;
        this.compEmail = compEmail;
        this.scores = scores; 
        this.overallScore = overallScore;
        this.level = level;
    }

    // Get and Set methods
    public int getCompNum() {
        int num = compNum;
        return num;
    }
    public void setCompNum(int compNum) {
        this.compNum = compNum;
    }

    public String getCompName() {
        String name = compName;
        return name;
    }
    public void setCompName(String compName) {
        this.compName = compName;
    }

    public int getCompAge() {
        int age = compAge;
        return age;
    }
    public void setCompAge(int compAge) {
        this.compAge = compAge;
    }

    public String getCompEmail() {
        String email = compEmail;
        return email;
    }
    public void setCompEmail(String compEmail) {
        this.compEmail = compEmail;
    }

    public String getCompLevel() {
        String lvl = level;
        return lvl;
    }
    public void setCompLevel(String level) {
        this.level = level;
    }

    public String getScoreArray() {
        String array = "" + scores[0];
        for (int i = 1; i < scores.length; i++){
            array += ", " + scores[i];
        }
        return array;
    }
    public void setScoreArray(int[] scores) {
        this.scores = scores;
        setOverallScore();
    }

    public double getOverallScore() {
        double overall = overallScore;
        return overall;
    }
    // Overall Score will be the average of scores minus the highest and lowest values
    public void setOverallScore() {
        int[] scores = this.scores;
        Arrays.sort(scores);
        this.overallScore = (double)(scores[1] + scores[2] + scores[3]) / 3;
    }

    public String getFullDetails() {
        String[] name = (this.compName).split(" ");
        String fullReport = "Competitor Number " + getCompNum() + " is named " + getCompName() + ". " 
        + name[0] + "'s level is " + getCompLevel() + " and they received these scores: " + getScoreArray() + ". This gives them an overall score of " + getOverallScore() + ".";
        return fullReport;
    }

    public String getShortDetails() {
        String initials = "";
        String[] name = (this.compName).split(" ");
        char initial;
        for (int i = 0; i < name.length; i++){
            initial = name[i].charAt(0);
            initials += initial;                  
        }
        String shortReport = "CN " + getCompNum() + " (" + initials + ") has an overall score of " + getOverallScore() + ".";
        return shortReport;
    }
}
