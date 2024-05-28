package ui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UIHeadset {
    private JFrame frame;
    private JButton leftEarButton;
    private JButton rightEarButton;
    private JButton bothEarsButton;
    private JButton micButton;
    private JLabel soundPlayingLabel;
    private JLabel volumeLabel;
    private JLabel timerLabel;
    private int recordingDuration = 10; // Duração do teste de microfone em segundos
    private Timer micTestTimer;
    private TargetDataLine micLine;
    private boolean running;

    public UIHeadset() {
        frame = new JFrame("Teste de Fones de Ouvido");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(32, 31, 58));

        leftEarButton = UIUtils.createButton("Testar Fone Esquerdo");
        rightEarButton = UIUtils.createButton("Testar Fone Direito");
        bothEarsButton = UIUtils.createButton("Testar Ambos os Fones");
        micButton = UIUtils.createButton("Testar Microfone");

        leftEarButton.addActionListener(e -> playSound("left"));
        rightEarButton.addActionListener(e -> playSound("right"));
        bothEarsButton.addActionListener(e -> playSound("both"));
        micButton.addActionListener(e -> testMicrophone());

        soundPlayingLabel = new JLabel("Som tocando...", SwingConstants.CENTER);
        soundPlayingLabel.setForeground(Color.WHITE);
        soundPlayingLabel.setVisible(false);

        volumeLabel = new JLabel("Volume: 0.0 dB", SwingConstants.CENTER);
        volumeLabel.setForeground(Color.WHITE);

        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(32, 31, 58));
        buttonPanel.add(leftEarButton);
        buttonPanel.add(rightEarButton);
        buttonPanel.add(bothEarsButton);
        buttonPanel.add(micButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(buttonPanel, gbc);

        gbc.gridy = 1;
        frame.add(timerLabel, gbc);

        gbc.gridy = 2;
        frame.add(volumeLabel, gbc);

        gbc.gridy = 3;
        frame.add(soundPlayingLabel, gbc);
    }

    public void showUI() {
        frame.setVisible(true);
    }

    private void playSound(String side) {
        disableButtons();
        soundPlayingLabel.setText("Som tocando...");
        soundPlayingLabel.setVisible(true);

        File soundFile;
        switch (side) {
            case "left":
                soundFile = new File("sounds/dogbass-undertale.wav");
                break;
            case "right":
                soundFile = new File("sounds/dogbass-undertale.wav");
                break;
            case "both":
                soundFile = new File("sounds/dogbass-undertale.wav");
                break;
            default:
                return;
        }

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            if ("left".equals(side)) {
                FloatControl panControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
                panControl.setValue(-1.0f); // Som à esquerda
            } else if ("right".equals(side)) {
                FloatControl panControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
                panControl.setValue(1.0f); // Som à direita
            }

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

    private void testMicrophone() {
        disableButtons();
        timerLabel.setText("Tempo restante: " + recordingDuration + " segundos");
        volumeLabel.setText("Volume: 0.0 dB");

        AudioFormat format = new AudioFormat(44100.0f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            JOptionPane.showMessageDialog(frame, "Microfone não suportado", "Erro", JOptionPane.ERROR_MESSAGE);
            enableButtons();
            return;
        }

        try {
            micLine = (TargetDataLine) AudioSystem.getLine(info);
            micLine.open(format);
            micLine.start();
            running = true;

            new SwingWorker<Void, Double>() {
                @Override
                protected Void doInBackground() {
                    byte[] buffer = new byte[1024];
                    while (running) {
                        int bytesRead = micLine.read(buffer, 0, buffer.length);
                        double rms = calculateRMSLevel(buffer, bytesRead);
                        publish(rms);
                    }
                    return null;
                }

                @Override
                protected void process(java.util.List<Double> chunks) {
                    if (!chunks.isEmpty()) {
                        double latestRMS = chunks.get(chunks.size() - 1);
                        volumeLabel.setText(String.format("Volume: %.2f dB", latestRMS));
                    }
                }

                @Override
                protected void done() {
                    micLine.stop();
                    micLine.close();
                    enableButtons();
                    timerLabel.setText("Teste de microfone concluído.");
                }
            }.execute();

            micTestTimer = new Timer(1000, e -> {
                recordingDuration--;
                timerLabel.setText("Tempo restante: " + recordingDuration + " segundos");
                if (recordingDuration <= 0) {
                    running = false;
                    micTestTimer.stop();
                    recordingDuration = 10; // Reset the duration for the next test
                }
            });
            micTestTimer.start();

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
            enableButtons();
        }
    }

    private double calculateRMSLevel(byte[] audioData, int bytesRead) {
        long lSum = 0;
        for (int i = 0; i < bytesRead; i += 2) {
            short sample = ByteBuffer.wrap(audioData, i, 2).order(ByteOrder.BIG_ENDIAN).getShort();
            lSum += sample * sample;
        }
        double mean = lSum / (bytesRead / 2.0);
        double rms = Math.sqrt(mean);
        return 20 * Math.log10(rms);
    }

    private void disableButtons() {
        leftEarButton.setEnabled(false);
        rightEarButton.setEnabled(false);
        bothEarsButton.setEnabled(false);
        micButton.setEnabled(false);
    }

    private void enableButtons() {
        leftEarButton.setEnabled(true);
        rightEarButton.setEnabled(true);
        bothEarsButton.setEnabled(true);
        micButton.setEnabled(true);
    }
}
