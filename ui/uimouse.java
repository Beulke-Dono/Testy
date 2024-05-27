package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class UIMouse {
    private JFrame frame;
    private MousePanel mousePanel;
    private JLabel mouseLabel;
    private JLabel scrollLabel;
    private Point lastMousePosition;

    public UIMouse() {
        frame = new JFrame("Teste de Mouse");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        
        mousePanel = new MousePanel();
        mousePanel.setBackground(new Color(30, 30, 60));
        mousePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int button = e.getButton();
                handleMouseClick(button);
            }
        });

        mousePanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleMouseWheel(e);
            }
        });

        mousePanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseMovement(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMovement(e);
            }
        });

        mouseLabel = new JLabel("Movimento do mouse: ");
        mouseLabel.setForeground(Color.WHITE);
        mouseLabel.setFont(new Font("Arial", Font.BOLD, 14));

        scrollLabel = new JLabel("Rolagem: ");
        scrollLabel.setForeground(Color.WHITE);
        scrollLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(mouseLabel);
        labelPanel.add(scrollLabel);
        labelPanel.setBackground(new Color(30, 30, 60));

        frame.add(mousePanel, BorderLayout.CENTER);
        frame.add(labelPanel, BorderLayout.SOUTH);
    }

    private void handleMouseClick(int button) {
        switch (button) {
            case MouseEvent.BUTTON1: // Botão esquerdo
                mousePanel.setLeftButtonPressed(true);
                break;
            case MouseEvent.BUTTON2: // Botão do meio
                mousePanel.setMiddleButtonPressed(true);
                break;
            case MouseEvent.BUTTON3: // Botão direito
                mousePanel.setRightButtonPressed(true);
                break;
        }
        mousePanel.repaint();
    }

    private void handleMouseWheel(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            mousePanel.setScrollDirection("UP");
        } else {
            mousePanel.setScrollDirection("DOWN");
        }
        updateScrollLabel();
    }

    private void handleMouseMovement(MouseEvent e) {
        if (lastMousePosition != null) {
            int dx = e.getX() - lastMousePosition.x;
            int dy = e.getY() - lastMousePosition.y;
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    mousePanel.setMouseDirection("RIGHT");
                } else {
                    mousePanel.setMouseDirection("LEFT");
                }
            } else {
                if (dy > 0) {
                    mousePanel.setMouseDirection("DOWN");
                } else {
                    mousePanel.setMouseDirection("UP");
                }
            }
        }
        lastMousePosition = e.getPoint();
        updateMouseLabel();
    }

    private void updateMouseLabel() {
        mouseLabel.setText(String.format("Movimento do mouse: %s", mousePanel.getMouseDirection()));
    }

    private void updateScrollLabel() {
        scrollLabel.setText(String.format("Rolagem: %s", mousePanel.getScrollDirection()));
    }

    public void showUI() {
        frame.setVisible(true);
    }

    class MousePanel extends JPanel {
        private boolean leftButtonPressed = false;
        private boolean middleButtonPressed = false;
        private boolean rightButtonPressed = false;
        private String scrollDirection = "";
        private String mouseDirection = "";

        public void setLeftButtonPressed(boolean pressed) {
            leftButtonPressed = pressed;
        }

        public void setMiddleButtonPressed(boolean pressed) {
            middleButtonPressed = pressed;
        }

        public void setRightButtonPressed(boolean pressed) {
            rightButtonPressed = pressed;
        }

        public void setScrollDirection(String direction) {
            scrollDirection = direction;
        }

        public String getScrollDirection() {
            return scrollDirection;
        }

        public void setMouseDirection(String direction) {
            mouseDirection = direction;
        }

        public String getMouseDirection() {
            return mouseDirection;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Desenho do corpo do mouse
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRoundRect(150, 150, 200, 300, 100, 150);

            // Desenho dos botões do mouse
            g2.setColor(leftButtonPressed ? Color.RED : Color.DARK_GRAY);
            g2.fillRoundRect(160, 160, 80, 140, 20, 20);  // Botão esquerdo

            g2.setColor(rightButtonPressed ? Color.BLUE : Color.DARK_GRAY);
            g2.fillRoundRect(260, 160, 80, 140, 20, 20);  // Botão direito

            // Desenho do scroll wheel
            g2.setColor(middleButtonPressed ? Color.GREEN : Color.BLACK);
            g2.fillRoundRect(235, 190, 30, 80, 10, 10);  // Scroll wheel

            // Reset the button states
            leftButtonPressed = false;
            middleButtonPressed = false;
            rightButtonPressed = false;
        }
    }
}
