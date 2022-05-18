package App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {
    public MenuBar(JTabbedPane windowsPanel, AboutDialog aboutDialog) {
        //Configure shortcut keys
        Action exitAction = new AbstractAction("Zamknij") {
            @Override
            public void actionPerformed(ActionEvent e) {}
        };
        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));

        Action usersAction = new AbstractAction("Użytkownicy") {
            @Override
            public void actionPerformed(ActionEvent e) {}
        };
        usersAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));

        Action componentsAction = new AbstractAction("Komponenty") {
            @Override
            public void actionPerformed(ActionEvent e) {}
        };
        componentsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_K, KeyEvent.CTRL_DOWN_MASK));

        Action configsAction = new AbstractAction("Konfiguracje") {
            @Override
            public void actionPerformed(ActionEvent e) {}
        };
        configsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));

        Action aboutAction = new AbstractAction("O aplikacji") {
            @Override
            public void actionPerformed(ActionEvent e) {}
        };
        aboutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));

        //Configure "Panel" menu bar option
        JMenu menuBarPanel = new JMenu("Panel");
        menuBarPanel.setMnemonic(KeyEvent.VK_P);

        JMenuItem exitOption = new JMenuItem("Zamknij");
        exitOption.addActionListener((event) -> System.exit(0));
        exitOption.setAction(exitAction);
        exitOption.setMnemonic(KeyEvent.VK_Z);

        menuBarPanel.add(exitOption);

        //Configure "Menu" menu bar option
        JMenu menuBarMenu = new JMenu("Menu");
        menuBarMenu.setMnemonic(KeyEvent.VK_M);

        JMenuItem usersOption = new JMenuItem("Użytkownicy");
        usersOption.addActionListener((event) -> windowsPanel.setSelectedIndex(0));
        usersOption.setAction(usersAction);
        usersOption.setMnemonic(KeyEvent.VK_U);

        JMenuItem componentsOption = new JMenuItem("Komponenty");
        componentsOption.addActionListener((event) -> windowsPanel.setSelectedIndex(1));
        componentsOption.setAction(componentsAction);
        componentsOption.setMnemonic(KeyEvent.VK_K);

        JMenuItem configsOption = new JMenuItem("Konfiguracje");
        configsOption.addActionListener((event) -> windowsPanel.setSelectedIndex(2));
        configsOption.setAction(configsAction);
        configsOption.setMnemonic(KeyEvent.VK_O);

        menuBarMenu.add(usersOption);
        menuBarMenu.add(componentsOption);
        menuBarMenu.add(configsOption);

        //Configure "Pomoc" menu bar option
        JMenu menuBarHelp = new JMenu("Pomoc");
        menuBarHelp.setMnemonic(KeyEvent.VK_O);

        JMenuItem aboutOption = new JMenuItem("O aplikacji");
        aboutOption.addActionListener((event) -> aboutDialog.showDialog());
        aboutOption.setAction(aboutAction);
        aboutOption.setMnemonic(KeyEvent.VK_O);

        menuBarHelp.add(aboutOption);

        //Add options to menu bar
        this.add(menuBarPanel);
        this.add(menuBarMenu);
        this.add(menuBarHelp);
    }
}
