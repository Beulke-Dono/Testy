package tests;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.DefaultListModel;

public class KeyboardTest implements KeyListener {
    private DefaultListModel<String> listModel;

    public KeyboardTest(DefaultListModel<String> listModel) {
        this.listModel = listModel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDefined(keyChar) && !Character.isISOControl(keyChar)) {
            listModel.addElement(String.valueOf(keyChar));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Não é necessário implementar para este exemplo
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Não é necessário implementar para este exemplo
    }
}
