package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIUtils {
    public static JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(12, 7, 36));
        panel.setPreferredSize(new Dimension(200, 0));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }

    public static JLabel createInfoLabel(String text) {
        JLabel label = new JLabel("<html><div style='color: white;'>" + text + "</div></html>");
        label.setForeground(new Color(178, 102, 255));
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    public static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(32, 31, 58));
        panel.setPreferredSize(new Dimension(200, 0));
        return panel;
    }

    public static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setBackground(new Color(142, 68, 173));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(150, 40));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        return button;
    }

    public static JLabel createLogoLabel(ImageIcon icon) {
        JLabel label = new JLabel(icon);
        label.setBorder(new EmptyBorder(20, 20, 20, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
