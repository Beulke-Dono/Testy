package ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UIKeyboard {
    private JFrame frame;
    private Map<Character, JButton> keyButtons;

    public UIKeyboard() {
        frame = new JFrame("Teste de Teclado");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());

        keyButtons = new HashMap<>();
        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridLayout(5, 15, 5, 5));

        // Criando botões de teclado (expandir conforme necessário)
        String keys = "1234567890qwertyuiopasdfghjklzxcvbnm";
        for (char key : keys.toCharArray()) {
            JButton button = new JButton(String.valueOf(key).toUpperCase());
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setBackground(Color.LIGHT_GRAY);
            button.setEnabled(false); // Tornar o botão não clicável
            keyButtons.put(key, button);
            keyboardPanel.add(button);
        }

        frame.add(keyboardPanel, BorderLayout.CENTER);
    }

    public void showUI() {
        frame.setVisible(true);
    }

    public void updateKeyState(char key, boolean pressed) {
        JButton button = keyButtons.get(Character.toLowerCase(key));
        if (button != null) {
            button.setBackground(pressed ? Color.PINK : Color.LIGHT_GRAY);
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}
