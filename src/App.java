public class App {
    public static void main(String[] args) throws Exception {
        Competitor test = new Competitor(155, "Joe Kent Bolton", 20, "testemail", "Novice");
        
        System.out.println(test.getOverallScore());
        System.out.println(test.getFullDetails());
        System.out.println(test.getShortDetails());
    }
}
