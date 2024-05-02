public class App {
    public static void main(String[] args) throws Exception {
        Competitor test = new Competitor(1, "Joe Bolton", 20, "testemail", "Novice");
        System.out.println(test.printSummaryReport());
    }
}
