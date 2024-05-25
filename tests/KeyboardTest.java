package tests;

import ui.UIKeyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class KeyboardTest {
    private UIKeyboard uiKeyboard;

    public KeyboardTest() {
        uiKeyboard = new UIKeyboard();
    }

    public void start() {
        uiKeyboard.showUI();
        JFrame frame = uiKeyboard.getFrame();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                uiKeyboard.updateKeyState(e.getKeyChar(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                uiKeyboard.updateKeyState(e.getKeyChar(), false);
            }
        });
    }
}
