package test.driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class TestDriver {
    private static PrintWriter writer;
    private static ArrayList<Shape> shapes = new ArrayList<>(); 

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Shape Sizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);

           
           Color backgroundColor = new Color(255, 192, 203); 
           Color panelColor = new Color(255, 182, 193);      
           Color buttonColor = new Color(192, 192, 192);     
           Color textColor = Color.BLACK;

            
            JPanel inputPanel = new JPanel(new GridLayout(4, 1));
            inputPanel.setBackground(panelColor);

            JLabel label = new JLabel("Enter shapes (e.g., 2 circle 5 cube 3):");
            label.setForeground(Color.BLACK);

            JTextField inputField = new JTextField();

            JButton submitButton = new JButton("Submit");
            submitButton.setBackground(buttonColor);
            submitButton.setForeground(textColor);
            submitButton.setFocusPainted(false);

            inputPanel.add(label);
            inputPanel.add(inputField);
            inputPanel.add(submitButton);

            
            JTextArea outputArea = new JTextArea();
            outputArea.setEditable(false);
            outputArea.setBackground(backgroundColor);
            outputArea.setForeground(Color.BLACK);
            outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

            JScrollPane scrollPane = new JScrollPane(outputArea);

            frame.setLayout(new BorderLayout());
            frame.add(inputPanel, BorderLayout.NORTH);
            frame.add(scrollPane, BorderLayout.CENTER);

            
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = inputField.getText().trim();
                    if (input.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter shapes input.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        String filePath = "output.txt";
                        writer = new PrintWriter(new FileWriter(filePath));
                        outputArea.setText(""); // Clear previous output
                        shapes.clear(); // Clear previous shapes

                        String[] argsInput = input.split(" ");
                        processShapes(argsInput, outputArea);
                        writer.close();
                        JOptionPane.showMessageDialog(frame, "Output saved to output.txt", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error writing to file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            frame.getContentPane().setBackground(backgroundColor);
            frame.setVisible(true);
        });
    }

    private static void processShapes(String[] args, JTextArea outputArea) {
        try {
            int n = Integer.parseInt(args[0]);
            if (n <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            appendOutput("Invalid input. First value must be a positive integer representing the number of shapes.\n", outputArea);
            return;
        }

        int n = Integer.parseInt(args[0]);

        if (args.length < 2 || (args.length - 1) % 2 != 0) {
            appendOutput("Invalid input. Example: 2 circle 22.5 cube 23.6\n", outputArea);
            return;
        }

        int index = 0;
        for (int i = 1; i < args.length; i += 2) {
            String shapeType = args[i];
            double dimension;

            try {
                dimension = Double.parseDouble(args[i + 1]);
                if (dimension <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                appendOutput("Invalid dimension for shape. Enter a positive number: " + shapeType + ". Skipping...\n", outputArea);
                continue;
            }

            Shape shape;
            if (shapeType.equalsIgnoreCase("circle")) {
                shape = new Circle("Red", dimension);
            } else if (shapeType.equalsIgnoreCase("cube")) {
                shape = new Cube("Blue", dimension);
            } else {
                appendOutput("Unknown shape: " + shapeType + ". Skipping...\n", outputArea);
                continue;
            }

            shapes.add(shape);
            index++;

            if (index == n) break;
        }

        if (index < n) {
            appendOutput("--------------------------------------------------\n", outputArea);
            appendOutput("Warning: Expected " + n + " shapes, but only " + index + " were created.\n", outputArea);
            appendOutput("--------------------------------------------------\n", outputArea);
        }

        displayShapes(outputArea);
    }

    private static void displayShapes(JTextArea outputArea) {
        double totalArea = 0;
        int shapeCount = 0;

        appendOutput("\n============== Shape Details ==============\n\n", outputArea);
        for (Shape shape : shapes) {
            shapeCount++;
            appendOutput("Shape " + shapeCount + ":\n", outputArea);
            appendOutput(shape + "\n", outputArea);
            appendOutput("Drawing Instructions: " + shape.howToDraw() + "\n", outputArea);
            appendOutput("--------------------------------------------------\n", outputArea);
            totalArea += shape.getArea();
        }
        appendOutput("\n============== Summary ==============\n\n", outputArea);
        appendOutput("Total Number of Shapes Created: " + shapeCount + "\n", outputArea);
        appendOutput("Total Area of all shapes: " + totalArea + "\n", outputArea);
        appendOutput("\n=====================================\n", outputArea);
    }

    private static void appendOutput(String text, JTextArea outputArea) {
        outputArea.append(text);
        writer.print(text);
    }
}
