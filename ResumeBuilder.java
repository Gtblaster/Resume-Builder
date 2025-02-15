import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ResumeBuilder {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResumeUI().setVisible(true));
    }
}

class ResumeUI extends JFrame {
    private JTextField nameField, emailField;
    private JTextArea resumeArea;
    private JButton generateButton, saveButton;
    
    public ResumeUI() {
        setTitle("Resume Builder");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);
        
        resumeArea = new JTextArea();
        generateButton = new JButton("Generate Resume");
        saveButton = new JButton("Save Template");
        
        generateButton.addActionListener(new GenerateAction());
        saveButton.addActionListener(new SaveAction());
        
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resumeArea), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class GenerateAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String email = emailField.getText();
            resumeArea.setText("Resume\n-------------\n" + "Name: " + name + "\nEmail: " + email);
        }
    }

    private class SaveAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JSONObject template = new JSONObject();
            template.put("name", nameField.getText());
            template.put("email", emailField.getText());
            
            try (FileWriter file = new FileWriter("resume_template.json")) {
                file.write(template.toJSONString());
                JOptionPane.showMessageDialog(null, "Template Saved Successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
