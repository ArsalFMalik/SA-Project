public class App {
    public static void main(String[] args) throws Exception {
        CompetitorList competitorList = new CompetitorList(".\\src\\data.json");
        competitorList.finalReport();
    } 
}