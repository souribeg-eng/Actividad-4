package Archivos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;

public class VisorArchivo extends JFrame {
    private final JTextArea textArea = new JTextArea();

    public VisorArchivo() {
        super("Lector de Archivos de Texto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 480);
        setLocationRelativeTo(null);

        // Look and Feel del sistema (opcional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        initUI();
    }

    private void initUI() {
        // Área de texto: más legible
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Josefine Sans", Font.PLAIN, 14));
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        textArea.setBackground(new Color(0xFCFCFC));

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE0E0E0)),
                new EmptyBorder(6,6,6,6)
        ));
        getContentPane().add(scroll, BorderLayout.CENTER);

        // Barra superior con título pequeño (estético)
        JLabel header = new JLabel("Lector de Archivos de texto");
        header.setFont(new Font("Josefine Sans", Font.BOLD, 16));
        header.setBorder(new EmptyBorder(8,8,8,8));
        getContentPane().add(header, BorderLayout.NORTH);

        // Botón en la parte inferior centrado (cambia a NORTH/EAST/ WEST para mover)
        JButton btn = new JButton("\uD83D\uDCC2  Abrir archivo...");
        btn.setFont(new Font("Josefine Sans", Font.PLAIN, 14));
        btn.setForeground(Color.BLACK);
        btn.setBackground(new Color(219, 164, 203));
        //btn.setOpaque(true);
        //btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8,14,8,14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> abrir());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        bottom.setBorder(new EmptyBorder(6,6,6,6));
        bottom.add(btn);
        getContentPane().add(bottom, BorderLayout.SOUTH); // <-- aquí se coloca el botón (mover aquí)

        // Menú simple: Archivo -> Abrir / Salir
        JMenuBar mb = new JMenuBar();
        JMenu mFile = new JMenu("Archivo");
        JMenuItem miOpen = new JMenuItem("Abrir...");
        miOpen.addActionListener(e -> abrir());
        JMenuItem miExit = new JMenuItem("Salir");
        miExit.addActionListener(e -> dispose());
        mFile.add(miOpen);
        mFile.addSeparator();
        mFile.add(miExit);
        mb.add(mFile);
        setJMenuBar(mb);
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