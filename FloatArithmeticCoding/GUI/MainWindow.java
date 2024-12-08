import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Float Arithmetic Coding");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.decode("#91AC8F"));

        JButton compressButton = new JButton("Compression");
        compressButton.setFont(new Font("Arial", Font.BOLD, 25));
        compressButton.setBackground(Color.decode("#4B5945"));
        compressButton.setForeground(Color.white);

        JButton decompressButton = new JButton("Decompression");
        decompressButton.setFont(new Font("Arial", Font.BOLD, 25));
        decompressButton.setBackground(Color.decode("#4B5945"));
        decompressButton.setForeground(Color.white);

        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileSelectionWindow("Compression");
            }
        });

        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileSelectionWindow("Decompression");
            }
        });

        setLayout(new GridLayout(2, 1));
        add(compressButton);
        add(decompressButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
}

class FileSelectionWindow extends JFrame {
    public FileSelectionWindow(String action) {
        Compression compression = new Compression();
        Decompression decompression = new Decompression();
        ReadWriteFiles readWriteFiles = new ReadWriteFiles();
        setTitle(action);
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel inputFileLabel = new JLabel("Input File:");

        JTextField inputFileField = new JTextField(20);

        JButton inputBrowseButton = new JButton("Browse");
        inputBrowseButton.setBackground(Color.decode("#4B5945"));
        inputBrowseButton.setForeground(Color.white);

        JLabel outputFileLabel = new JLabel("Output File:");

        JTextField outputFileField = new JTextField(20);

        JButton outputBrowseButton = new JButton("Browse");
        outputBrowseButton.setBackground(Color.decode("#4B5945"));
        outputBrowseButton.setForeground(Color.white);

        JButton actionButton = new JButton(action);

        JFileChooser fileChooser = new JFileChooser();

        inputBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    inputFileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        outputBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    outputFileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputFileName = inputFileField.getText();
                String outputFileName = outputFileField.getText();
                if (!inputFileName.isEmpty() && !outputFileName.isEmpty()) {
                    System.out.println("Action: " + action);
                    System.out.println("Input File: " + inputFileName);
                    System.out.println("Output File: " + outputFileName);

                    if (action.equals("Compression")) {
                        readWriteFiles.writeStringToFile(compression.compress(readWriteFiles.readStringFromFile(inputFileName)), outputFileName);
                    } else {
                        readWriteFiles.writeStringToFile(decompression.decompress(readWriteFiles.readStringFromFile(inputFileName)), outputFileName);
                    }

                    JOptionPane.showMessageDialog(null, action + " completed successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select both input and output files.");
                }
            }
        });

        setLayout(new FlowLayout());
        add(inputFileLabel);
        add(inputFileField);
        add(inputBrowseButton);
        add(outputFileLabel);
        add(outputFileField);
        add(outputBrowseButton);
        add(actionButton);

        setVisible(true);
    }
}