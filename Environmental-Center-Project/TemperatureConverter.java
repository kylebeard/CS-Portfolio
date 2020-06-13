import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TemperatureConverter extends JFrame {

    private JTextField fahrenheitBox = new JTextField(8);
    private JButton convertButton = new JButton("Convert");
    private JLabel displayLabel = new JLabel();

    // Constructor - setup the application
    public TemperatureConverter() {
        super("Temperature Converter");
        
        setupLayout();
        addListeners();
        
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Or:
        /*
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        */
    }
    
    private void setupLayout() {
        // A border layout has five regions — north, south, east, west, and center; the center gets all the space left over from the other four
        // new BorderLayout(5, 5) specifies horizontal and vertical gaps of 5 pixels between the regions
        setLayout(new BorderLayout(5, 5));
        
        // JPanels hold other components but are themselves invisible
        JPanel north = new JPanel();
        north.add(new JLabel("Fahrenheit:"));
        north.add(fahrenheitBox);
        north.add(convertButton);
        add(north, BorderLayout.NORTH);
        
        displayLabel.setFont(new Font("Quercus", Font.PLAIN, 24));
        displayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(displayLabel, BorderLayout.CENTER);
    }
    
    private void addListeners() {
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doCompute();
            }
        });
        
        // Or, using a λ (Java 8+):
        // convertButton.addActionListener(e -> doCompute());
    }
    
    private void doCompute() {
        // Convert °F → K
        double fahrenheit = Double.parseDouble(fahrenheitBox.getText());
        double kelvins = 5.0 / 9 * (fahrenheit - 32) + 273.15;
        displayLabel.setText(String.format("%.2f Kelvins", kelvins));
    }

    public static void main(String... args) {
        TemperatureConverter tc = new TemperatureConverter();
        tc.setVisible(true);
    }
}
