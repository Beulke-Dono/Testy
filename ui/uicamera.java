package ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UICamera {
    private JFrame frame;
    private JButton webcamButton;
    private JLabel webcamStatusLabel;

    public UICamera() {
        frame = new JFrame("Teste de Webcam");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(32, 31, 58));

        webcamButton = UIUtils.createButton("Testar Webcam");

        webcamButton.addActionListener(e -> testWebcam());

        webcamStatusLabel = new JLabel("Clique no botão para testar a webcam.", SwingConstants.CENTER);
        webcamStatusLabel.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1, 10, 10));
        buttonPanel.setBackground(new Color(32, 31, 58));
        buttonPanel.add(webcamButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(buttonPanel, gbc);

        gbc.gridy = 1;
        frame.add(webcamStatusLabel, gbc);
    }

    public void showUI() {
        frame.setVisible(true);
    }

    private void testWebcam() {
        disableButtons();

        // Tentar abrir o aplicativo de Câmera do Windows
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start microsoft.windows.camera:");
            processBuilder.start();
            webcamStatusLabel.setText("Aplicativo de câmera do Windows aberto.");
        } catch (IOException e) {
            e.printStackTrace();
            webcamStatusLabel.setText("Erro ao abrir o aplicativo de câmera do Windows.");
        }

        enableButtons();
    }

    private void disableButtons() {
        webcamButton.setEnabled(false);
    }

    private void enableButtons() {
        webcamButton.setEnabled(true);
    }
}

