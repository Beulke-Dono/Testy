package ui;

import javax.swing.*;
import java.awt.*;

public class UIKeyboard {
    public void showUI() {
        JFrame frame = new JFrame("Teste de Teclado");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel instructionLabel = new JLabel("Pressione as teclas para testá-las:");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(instructionLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                char keyChar = evt.getKeyChar();
                textArea.append("Tecla pressionada: " + keyChar + " (Código: " + evt.getKeyCode() + ")\n");
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
