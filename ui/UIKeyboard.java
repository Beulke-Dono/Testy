package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import tests.KeyboardTest;

public class UIKeyboard extends JFrame {
    private JList<String> keyList;
    private DefaultListModel<String> listModel;

    public UIKeyboard() {
        super("Key Press Tracker");

        listModel = new DefaultListModel<>();
        keyList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(keyList);

        add(scrollPane);

        // Cria um painel de informações usando UIUtils
        JPanel infoPanel = UIUtils.createInfoPanel();
        JLabel infoLabel = UIUtils.createInfoLabel("Pressione uma tecla para começar.");
        infoPanel.add(infoLabel);
        add(infoPanel, BorderLayout.WEST);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fechar apenas a janela do teclado
        setSize(700, 500);
        setLocationRelativeTo(null); // Centraliza a janela na tela
    }

    public void setKeyListener(KeyListener listener) {
        keyList.addKeyListener(listener);
    }

    public void showUI() {
        setVisible(true);
    }

    // Método para adicionar teclas pressionadas ao modelo da lista
    public void addKeyPressed(String keyText) {
        listModel.addElement(keyText);
    }
}
