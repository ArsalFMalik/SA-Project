import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) throws Exception {
        CompetitorList competitorList = new CompetitorList(".\\data.json");
        SwingUtilities.invokeLater(() -> new Manager(competitorList));
    } 
}