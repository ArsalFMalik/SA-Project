import java.awt.*;
import java.util.Comparator;
import javax.swing.*;

public class Manager {

    private CompetitorList competitorList;
    private JList<String> competitorJList;
    private DefaultListModel<String> listModel;
    private JTextField compNumField, compNameField, compAgeField, compEmailField, compLevelField, overallScoreField;
    private JTextField[] scoreFields;

    public Manager(CompetitorList competitorList) {
        this.competitorList = competitorList;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        
        JPanel viewPanel = createViewPanel();
        JPanel listPanel = createListPanel();
        JPanel detailPanel = createDetailPanel();

        
        frame.add(viewPanel, BorderLayout.WEST);
        frame.add(listPanel, BorderLayout.CENTER);
        frame.add(detailPanel, BorderLayout.EAST);

        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            competitorList.finalReport();
            frame.dispose();
        });

        frame.add(closeButton, BorderLayout.SOUTH);

        
        frame.setVisible(true);
    }

    private JPanel createViewPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        JButton viewTableButton = new JButton("View Table");
        viewTableButton.addActionListener(e -> {
            JTextArea textArea = new JTextArea(competitorList.printTable());
            JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Competitors Table", JOptionPane.INFORMATION_MESSAGE);
        });
    
        JButton sortByScoreButton = new JButton("Sort by Score");
        sortByScoreButton.addActionListener(e -> {
            competitorList.compList.sort(Comparator.comparingDouble(Competitor::getOverallScore).reversed());
            JTextArea textArea = new JTextArea(competitorList.printTable());
            JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Competitors Sorted by Score", JOptionPane.INFORMATION_MESSAGE);
        });
    
        JButton sortByNumberButton = new JButton("Sort by Number");
        sortByNumberButton.addActionListener(e -> {
            competitorList.compList.sort(Comparator.comparingInt(Competitor::getCompNum));
            JTextArea textArea = new JTextArea(competitorList.printTable());
            JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Competitors Sorted by Number", JOptionPane.INFORMATION_MESSAGE);
        });
    
        JButton sortByAgeButton = new JButton("Sort by Age");
        sortByAgeButton.addActionListener(e -> {
            competitorList.compList.sort(Comparator.comparingInt(Competitor::getCompAge));
            JTextArea textArea = new JTextArea(competitorList.printTable());
            JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Competitors Sorted by Age", JOptionPane.INFORMATION_MESSAGE);
        });
    
        JButton filterBeginnerButton = new JButton("Filter Beginner");
        filterBeginnerButton.addActionListener(e -> filterCompetitorsByLevel("Beginner"));
    
        JButton filterIntermediateButton = new JButton("Filter Intermediate");
        filterIntermediateButton.addActionListener(e -> filterCompetitorsByLevel("Intermediate"));
    
        JButton filterAdvancedButton = new JButton("Filter Advanced");
        filterAdvancedButton.addActionListener(e -> filterCompetitorsByLevel("Advanced"));
    
        JButton clearFilterButton = new JButton("Clear Filters");
        clearFilterButton.addActionListener(e -> {
            resetList();
        });
    
        panel.add(viewTableButton);
        panel.add(sortByNumberButton);
        panel.add(sortByScoreButton);
        panel.add(sortByAgeButton);
        panel.add(filterBeginnerButton);
        panel.add(filterIntermediateButton);
        panel.add(filterAdvancedButton);
        panel.add(clearFilterButton);

        return panel;
    }
    
    private void resetList() {
        listModel.clear();
        competitorList.compList.forEach(comp -> listModel.addElement(comp.getCompNum() + " - " + comp.getCompName()));
    }
    
    
    private void filterCompetitorsByLevel(String level) {
        listModel.clear();
        competitorList.compList.stream()
                .filter(comp -> comp.getCompLevel().equals(level))
                .forEach(comp -> listModel.addElement(comp.getCompNum() + " - " + comp.getCompName()));
    }
    

    private JPanel createListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        competitorList.compList.forEach(comp -> listModel.addElement(comp.getCompNum() + " - " + comp.getCompName()));

        competitorJList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(competitorJList);

        panel.add(new JLabel("Select a Competitor:"), BorderLayout.NORTH);
        panel.add(listScrollPane, BorderLayout.CENTER);

        competitorJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetailPanel(competitorJList.getSelectedValue());
            }
        });

        return panel;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
    
        compNumField = createLabelAndTextField(panel, "Competitor Number:");
        compNameField = createLabelAndTextField(panel, "Name:");
        compAgeField = createLabelAndTextField(panel, "Age:");
        compEmailField = createLabelAndTextField(panel, "Email:");
        compLevelField = createLabelAndTextField(panel, "Level:");
        overallScoreField = createLabelAndTextField(panel, "Overall Score:");
        overallScoreField.setEditable(false);
    
        scoreFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            scoreFields[i] = createLabelAndTextField(panel, "Score " + (i + 1) + ":");
        }

        JButton viewFullDetailsButton = new JButton("View Full Details");
        viewFullDetailsButton.addActionListener(e -> {
            try {
                int compNum = Integer.parseInt(compNumField.getText());
                Competitor comp = competitorList.compList.stream()
                        .filter(c -> c.getCompNum() == compNum)
                        .findFirst()
                        .orElse(null);

                if (comp != null) {
                    JTextArea textArea = new JTextArea(comp.getFullDetails());
                    JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Competitor Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Competitor not found");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });
        panel.add(viewFullDetailsButton);

        JButton viewShortDetailsButton = new JButton("View Short Details");
        viewShortDetailsButton.addActionListener(e -> {
            try {
                int compNum = Integer.parseInt(compNumField.getText());
                Competitor comp = competitorList.compList.stream()
                        .filter(c -> c.getCompNum() == compNum)
                        .findFirst()
                        .orElse(null);

                if (comp != null) {
                    JTextArea textArea = new JTextArea(comp.getShortDetails());
                    JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Competitor Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Competitor not found");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });
        panel.add(viewShortDetailsButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateCompetitorDetails());
        panel.add(updateButton);
    
        JButton removeButton = new JButton("Remove Competitor");
        removeButton.addActionListener(e -> {
            try {
                int compNum = Integer.parseInt(compNumField.getText());
                Competitor comp = competitorList.compList.stream()
                        .filter(c -> c.getCompNum() == compNum)
                        .findFirst()
                        .orElse(null);
    
                if (comp != null) {
                    competitorList.compList.remove(comp);
                    listModel.removeElement(compNum + " - " + comp.getCompName());
                    JOptionPane.showMessageDialog(null, "Competitor removed successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Competitor not found");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });
        panel.add(removeButton);
    
        return panel;
    }
    

    private JTextField createLabelAndTextField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        panel.add(label);
        panel.add(textField);
        return textField;
    }

    private void updateDetailPanel(String selectedValue) {
        if (selectedValue == null) return;
    
        int compNum = Integer.parseInt(selectedValue.split(" - ")[0]);
        Competitor comp = competitorList.compList.stream()
                .filter(c -> c.getCompNum() == compNum)
                .findFirst()
                .orElse(null);
    
        if (comp != null) {
            compNumField.setText(String.valueOf(comp.getCompNum()));
            compNameField.setText(comp.getCompName());
            compAgeField.setText(String.valueOf(comp.getCompAge()));
            compEmailField.setText(comp.getCompEmail());
            compLevelField.setText(comp.getCompLevel());
            
            // Update overall score field
            overallScoreField.setText(String.valueOf(comp.getOverallScore()));
    
            String[] scores = comp.getScoreArray().split(",\\s*");
            for (int i = 0; i < scores.length; i++) {
                scoreFields[i].setText(scores[i]);
            }
        }
    }
    

    private void updateCompetitorDetails() {
        try {
            int compNum = Integer.parseInt(compNumField.getText());
            Competitor comp = competitorList.compList.stream()
                    .filter(c -> c.getCompNum() == compNum)
                    .findFirst()
                    .orElse(null);
    
            if (comp != null) {
                comp.setCompName(compNameField.getText());
                comp.setCompAge(Integer.parseInt(compAgeField.getText()));
                comp.setCompEmail(compEmailField.getText());
                comp.setCompLevel(compLevelField.getText());
    
                int[] newScores = new int[5];
                for (int i = 0; i < 5; i++) {
                    newScores[i] = Integer.parseInt(scoreFields[i].getText());
                }
                comp.setScoreArray(newScores);
                overallScoreField.setText(String.valueOf(comp.getOverallScore()));
    
                JOptionPane.showMessageDialog(null, "Competitor details updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Competitor not found");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input");
        }
    }
}