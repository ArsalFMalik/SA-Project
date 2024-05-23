
public class Competitor {
    public int compNum;
    private String compName;
    private int compAge;
    private String compEmail;
    private int[] scores;
    private double overallScore;
    private String level;

    //constructor
    public Competitor(int compNum, String compName, int compAge, String compEmail, String level) {
        this.compNum = compNum;
        this.compName = compName;
        this.compAge = compAge;
        this.compEmail = compEmail;
        this.scores = new int[5]; 
        this.overallScore = 0.0;
        this.level = level;
    }

    //get and set methods
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

    public double getOverallScore(){
        return 5;
    }

    //Competitor number 100, name Keith John Talbot, country UK.
    //Keith is a Novice aged 21 and has an overall score of 5.
    public String getFullDetails(){
        String[] name = (this.compName).split(" ");
        String fullReport = "Competitor Number " + getCompNum() + " is named " + getCompName() + ". " 
        + name[0] + " is a " + getCompLevel() + " and received these scores: " + " This gives him an overall score of " + getOverallScore() + ".";
        return fullReport;
    }

    //CN 100 (KJT) has overall score 5.
    public String getShortDetails(){
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
