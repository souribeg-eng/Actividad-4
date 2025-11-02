package Archivos;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class VisorArchivo extends JFrame {
    private final JTextArea textArea = new JTextArea();

    public VisorArchivo() {
        super("Lector de Archivos de Texto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton btn = new JButton("Abrir archivo...");
        btn.addActionListener(e -> abrir());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.add(btn);
        add(top, BorderLayout.NORTH);
    }

    private void abrir() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            mostrar(f);
        }
    }

    private void mostrar(File f) {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            textArea.setText("");
            String linea;
            while ((linea = br.readLine()) != null) {
                textArea.append(linea + System.lineSeparator());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo leer el archivo:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisorArchivo().setVisible(true));
    }
}