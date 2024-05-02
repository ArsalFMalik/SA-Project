public class Competitor {
    public int compID;
    private String compName;
    private int compAge;
    private String compEmail;
    private float overallScore;
    private String level;

    public Competitor(int compID, String compName, int compAge, String compEmail, String level){
        this.compID = compID;
        this.compName = compName;
        this.compAge = compAge;
        this.compEmail = compEmail;
        this.overallScore = 0;
        this.level = level;
    }

     //Competitor number 100, name Keith John Talbot.
    // Keith is a Novice and received these scores : 5,4,5,4,3
    // This gives him an overall score of 4.2.
    public String printSummaryReport(){
        String[] name = (this.compName).split(" ");
        String report = "Competitor number " + this.compID + ", name " + this.compName + ". " 
        + name[0] + " is a " + this.level + " and received these scores : " + " This gives him an overall score of " + this.overallScore + ".";
        return report;
    }
}
