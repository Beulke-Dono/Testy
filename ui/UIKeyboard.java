package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class UIKeyboard {
    private JFrame frame;
    private JPanel keyboardPanel;
    private Map<String, JLabel> keyLabels;

    public UIKeyboard() {
        frame = new JFrame("Teste de Teclado");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1200, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridBagLayout());
        keyboardPanel.setBackground(new Color(32, 31, 58));

        keyLabels = new HashMap<>();
        createKeyboardLayout();

        frame.add(keyboardPanel, BorderLayout.CENTER);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String key = getKeyText(e);
                if (keyLabels.containsKey(key)) {
                    keyLabels.get(key).setBackground(Color.GREEN);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String key = getKeyText(e);
                if (keyLabels.containsKey(key)) {
                    keyLabels.get(key).setBackground(Color.LIGHT_GRAY);
                }
            }
        });
    }

    private void createKeyboardLayout() {
        String[][] keys = {
                {"ESC", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "PRTSC", "SCRLK", "PAUSE"},
                {"`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "BACKSPACE", "INS", "HOME", "PGUP"},
                {"TAB", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\", "DEL", "END", "PGDN"},
                {"CAPS", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'", "ENTER"},
                {"SHIFT", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/", "SHIFT", "UP"},
                {"CTRL", "WIN", "ALT", "SPACE", "ALT", "WIN", "MENU", "CTRL", "LEFT", "DOWN", "RIGHT"}
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        for (int row = 0; row < keys.length; row++) {
            for (int col = 0; col < keys[row].length; col++) {
                String key = keys[row][col];
                JLabel keyLabel = createKeyLabel(key);
                gbc.gridx = col;
                gbc.gridy = row;
                gbc.gridwidth = getGridWidth(key);
                keyLabels.put(key, keyLabel);
                keyboardPanel.add(keyLabel, gbc);
            }
        }
    }

    private JLabel createKeyLabel(String key) {
        JLabel label = new JLabel(key, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(50, 50));
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }

    private int getGridWidth(String key) {
        switch (key) {
            case "SPACE":
                return 6;
            case "BACKSPACE":
            case "CAPS":
            case "ENTER":
            case "SHIFT":
                return 2;
            case "TAB":
                return 1;
            default:
                return 1;
        }
    }

    private String getKeyText(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                return "ESC";
            case KeyEvent.VK_BACK_SPACE:
                return "BACKSPACE";
            case KeyEvent.VK_TAB:
                return "TAB";
            case KeyEvent.VK_CAPS_LOCK:
                return "CAPS";
            case KeyEvent.VK_ENTER:
                return "ENTER";
            case KeyEvent.VK_SHIFT:
                return "SHIFT";
            case KeyEvent.VK_CONTROL:
                return "CTRL";
            case KeyEvent.VK_ALT:
                return "ALT";
            case KeyEvent.VK_SPACE:
                return "SPACE";
            case KeyEvent.VK_WINDOWS:
                return "WIN";
            case KeyEvent.VK_CONTEXT_MENU:
                return "MENU";
            case KeyEvent.VK_UP:
                return "UP";
            case KeyEvent.VK_DOWN:
                return "DOWN";
            case KeyEvent.VK_LEFT:
                return "LEFT";
            case KeyEvent.VK_RIGHT:
                return "RIGHT";
            case KeyEvent.VK_INSERT:
                return "INS";
            case KeyEvent.VK_DELETE:
                return "DEL";
            case KeyEvent.VK_HOME:
                return "HOME";
            case KeyEvent.VK_END:
                return "END";
            case KeyEvent.VK_PAGE_UP:
                return "PGUP";
            case KeyEvent.VK_PAGE_DOWN:
                return "PGDN";
            case KeyEvent.VK_PRINTSCREEN:
                return "PRTSC";
            case KeyEvent.VK_SCROLL_LOCK:
                return "SCRLK";
            case KeyEvent.VK_PAUSE:
                return "PAUSE";
            default:
                return KeyEvent.getKeyText(keyCode).toUpperCase();
        }
    }

    public void showUI() {
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }
}
