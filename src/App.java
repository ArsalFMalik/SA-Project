public class App {
    public static void main(String[] args) throws Exception {
        CompetitorList competitorList = new CompetitorList(".\\src\\data.json");
        competitorList.printTable();
        int[] test = competitorList.freqCalc();
        for (int i = 0; i < 6; i++) {
            System.out.println(test[i]);
        }
        System.out.println(competitorList.findComp(5));
    } 
}