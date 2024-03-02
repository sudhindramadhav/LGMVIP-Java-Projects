import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class texteditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu;
    private JMenuItem openItem, saveItem, closeItem, printItem, cutItem, copyItem, pasteItem;
    private JButton saveAndSubmitButton;

    public texteditor() {
        setTitle("Text Editor");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");

        openItem = new JMenuItem("Open");
        openItem.addActionListener(this);
        fileMenu.add(openItem);

        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);

        closeItem = new JMenuItem("Close");
        closeItem.addActionListener(this);
        fileMenu.add(closeItem);

        printItem = new JMenuItem("Print");
        printItem.addActionListener(this);
        fileMenu.add(printItem);

        cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(this);
        editMenu.add(cutItem);

        copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(this);
        editMenu.add(copyItem);

        pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(this);
        editMenu.add(pasteItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        saveAndSubmitButton = new JButton("Save and Submit");
        saveAndSubmitButton.addActionListener(this);
        add(saveAndSubmitButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openItem) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        textArea.append(line + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == saveItem) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(textArea.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == closeItem) {
            textArea.setText("");
        } else if (e.getSource() == printItem) {
            try {
                textArea.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cutItem) {
            textArea.cut();
        } else if (e.getSource() == copyItem) {
            textArea.copy();
        } else if (e.getSource() == pasteItem) {
            textArea.paste();
        } else if (e.getSource() == saveAndSubmitButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(textArea.getText());
                    writer.close();
                    textArea.setText("");
                    JOptionPane.showMessageDialog(this, "File saved successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            texteditor textEditor = new texteditor();
            textEditor.setVisible(true);
        });
    }
}
