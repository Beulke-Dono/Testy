package ui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UISpeaker {
    private JFrame frame;
    private JButton speakerButton;
    private JLabel soundPlayingLabel;

    public UISpeaker() {
        frame = new JFrame("Teste de Caixa de Som");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(32, 31, 58));

        speakerButton = UIUtils.createButton("Testar Caixa de Som");

        speakerButton.addActionListener(e -> playSound());

        soundPlayingLabel = new JLabel("Som tocando...", SwingConstants.CENTER);
        soundPlayingLabel.setForeground(Color.WHITE);
        soundPlayingLabel.setVisible(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1, 10, 10));
        buttonPanel.setBackground(new Color(32, 31, 58));
        buttonPanel.add(speakerButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(buttonPanel, gbc);

        gbc.gridy = 1;
        frame.add(soundPlayingLabel, gbc);
    }

    public void showUI() {
        frame.setVisible(true);
    }

    private void playSound() {
        disableButtons();
        soundPlayingLabel.setText("Som tocando...");
        soundPlayingLabel.setVisible(true);

        File soundFile = new File("sounds/dogbass-undertale.wav");

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    soundPlayingLabel.setVisible(false);
                    enableButtons(); // Reabilitar os botões após o som
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            soundPlayingLabel.setVisible(false);
            enableButtons(); // Reabilitar os botões em caso de erro
        }
    }

    private void disableButtons() {
        speakerButton.setEnabled(false);
    }

    private void enableButtons() {
        speakerButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UISpeaker uiSpeakerTest = new UISpeaker();
            uiSpeakerTest.showUI();
        });
    }
}
