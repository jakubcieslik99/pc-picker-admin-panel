package App;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class GUI extends JFrame {
    private final Statement statement;

    private final GetUsers getUsers;
    private final GetComponents getComponents;
    private final GetConfigs getConfigs;

    private final EditUserDialog editUserDialog;
    private final AboutDialog aboutDialog;

    private JPanel statusBar;

    private JLabel statusLabel, dateLabel, timeLabel;

    private final PopupMenus popupMenus;
    private JPanel mainWindow, menuPanel;

    private JButton configsButton, componentsButton, usersButton, aboutButton, exitButton;
    private JLabel appTitle, appSubtitle, appCopyright;

    private JTabbedPane windowsPanel;
    private JPanel setupsPanel, componentsPanel, usersPanel;

    public JTable usersTable;
    private JButton editUserButton, deleteUserButton;

    public JTable componentsTable;
    private JButton editComponentButton, deleteComponentButton;

    private JTable configsTable;
    private JButton deleteConfigButton;

    public GUI() {
        //GUI setup
        super("PC Picker - Panel Administracyjny");
        this.setContentPane(this.mainWindow);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(768, 432);
        this.setLocationRelativeTo(null);

        //Database connection
        DatabaseConnect connection = new DatabaseConnect();
        statement = connection.connectToDatabase();

        //Data fetch
        getUsers = new GetUsers(statement);
        getComponents = new GetComponents(statement);
        getConfigs = new GetConfigs(statement);

        //GUI elements
        editUserDialog = new EditUserDialog(statement);
        aboutDialog = new AboutDialog();

        JMenuBar menuBar = new MenuBar(windowsPanel, aboutDialog);
        setJMenuBar(menuBar);

        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        Timer timer = new Timer(dateLabel, timeLabel);
        timer.start();

        popupMenus = new PopupMenus(this);

        usersButton.addActionListener(e -> windowsPanel.setSelectedIndex(0));
        componentsButton.addActionListener(e -> windowsPanel.setSelectedIndex(1));
        configsButton.addActionListener(e -> windowsPanel.setSelectedIndex(2));
        aboutButton.addActionListener(e -> aboutDialog.showDialog());
        exitButton.addActionListener(e -> {
            timer.setRunning(false);
            System.exit(0);
        });

        windowsPanel.addChangeListener(e -> {
            if(windowsPanel.getSelectedIndex()==0) {
                statusLabel.setText("Zakładka: Użytkownicy");
                usersCardSetup();
            }
            else if(windowsPanel.getSelectedIndex()==1) {
                statusLabel.setText("Zakładka: Komponenty");
                componentsCardSetup();
            }
            else if(windowsPanel.getSelectedIndex()==2) {
                statusLabel.setText("Zakładka: Konfiguracje");
                configsCardSetup();
            }
        });

        //Users card elements
        usersCardSetup();
        usersTable.setAutoCreateRowSorter(true);
        usersTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(usersTable.getSelectedRow()>=0) {
                    editUserButton.setEnabled(true);
                    deleteUserButton.setEnabled(true);
                    popupMenus.setPopupMenuUsers(true);
                }
                if(SwingUtilities.isRightMouseButton(e)) popupMenus.showPopupMenuUsers(usersTable, e);
            }
        });

        editUserButton.addActionListener(e -> editUserPopup());
        deleteUserButton.addActionListener(e -> deleteUserFunction());

        //Components card elements
        componentsTable.setAutoCreateRowSorter(true);
        componentsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(componentsTable.getSelectedRow()>=0) {
                    editComponentButton.setEnabled(true);
                    deleteComponentButton.setEnabled(true);
                    popupMenus.setPopupMenuComponents(true);
                }
                if(SwingUtilities.isRightMouseButton(e)) popupMenus.showPopupMenuComponents(componentsTable, e);
            }
        });

        //Configs card elements
        configsTable.setAutoCreateRowSorter(true);
        configsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(configsTable.getSelectedRow()>=0) {
                    deleteConfigButton.setEnabled(true);
                    popupMenus.setPopupMenuConfigs(true);
                }
                if(SwingUtilities.isRightMouseButton(e)) popupMenus.showPopupMenuConfigs(configsTable, e);
            }
        });
    }

    //Users card functions
    private void usersCardSetup() {
        try {
            editUserButton.setEnabled(false);
            deleteUserButton.setEnabled(false);
            popupMenus.setPopupMenuUsers(false);
            usersTable.setModel(getUsers.getUsers());
            //hide id column
            usersTable.getColumnModel().getColumn(0).setMinWidth(0);
            usersTable.getColumnModel().getColumn(0).setMaxWidth(0);
            usersTable.getColumnModel().getColumn(0).setWidth(0);
        }
        catch (SQLException exception) { exception.printStackTrace(); }
    }
    public void editUserPopup() {
        String id = usersTable.getValueAt(usersTable.getSelectedRow(), 0).toString();
        String nick = usersTable.getValueAt(usersTable.getSelectedRow(), 1).toString();
        String email = usersTable.getValueAt(usersTable.getSelectedRow(), 2).toString();
        String confirmed = usersTable.getValueAt(usersTable.getSelectedRow(), 3).toString();
        String isAdmin = usersTable.getValueAt(usersTable.getSelectedRow(), 4).toString();

        editUserDialog.fillInputs(getUsers, usersTable, id, nick, email, confirmed, isAdmin);
        editUserDialog.setVisible(true);
    }
    public void deleteUserFunction() {
        String id = usersTable.getModel().getValueAt(usersTable.getSelectedRow(), 0).toString();
        try {
            statement.executeUpdate("DELETE FROM setups, setups_components USING setups INNER JOIN setups_components WHERE setups.composedBy=" + id);
            statement.executeUpdate("DELETE FROM users WHERE users.id=" + id);
            usersTable.setModel(getUsers.getUsers());
            //hide id column
            usersTable.getColumnModel().getColumn(0).setMinWidth(0);
            usersTable.getColumnModel().getColumn(0).setMaxWidth(0);
            usersTable.getColumnModel().getColumn(0).setWidth(0);
        }
        catch (SQLException exception) { exception.printStackTrace(); }
    }

    //Components card functions
    public void componentsCardSetup() {
        try {
            editComponentButton.setEnabled(false);
            deleteComponentButton.setEnabled(false);
            popupMenus.setPopupMenuComponents(false);
            componentsTable.setModel(getComponents.getComponents());
            //hide id column
            componentsTable.getColumnModel().getColumn(0).setMinWidth(0);
            componentsTable.getColumnModel().getColumn(0).setMaxWidth(0);
            componentsTable.getColumnModel().getColumn(0).setWidth(0);
        }
        catch (SQLException exception) { exception.printStackTrace(); }
    }

    //Configs card functions
    public void configsCardSetup() {
        try {
            deleteConfigButton.setEnabled(false);
            popupMenus.setPopupMenuConfigs(false);
            configsTable.setModel(getConfigs.getConfigs());
        }
        catch (SQLException exception) { exception.printStackTrace(); }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }
}
