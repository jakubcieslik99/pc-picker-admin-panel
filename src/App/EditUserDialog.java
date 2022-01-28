package App;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class EditUserDialog extends JDialog {
    private JPanel dialogWindow;
    private JTextField nickTextField, emailTextField;
    private JCheckBox confirmedBox, isAdminBox;
    private JButton saveButton, cancelButton;

    Statement statement = null;
    private GetUsers getUsers;
    private JTable usersTable;
    private int currentId;

    public EditUserDialog(Statement statement) {
        this.setTitle("Edytuj użytkownika");
        this.setContentPane(this.dialogWindow);
        this.setModal(true);
        this.getRootPane().setDefaultButton(saveButton);
        this.setSize(400, 210);

        this.statement = statement;

        saveButton.addActionListener(e -> {
            try {
                String confirmed = confirmedBox.isSelected() ? "1" : "0";
                String isAdmin = isAdminBox.isSelected() ? "1" : "0";

                String queryParams = "nick='" + nickTextField.getText() + "', email='" + emailTextField.getText() + "', confirmed='" + confirmed + "', isAdmin='" + isAdmin + "'";
                this.statement.executeUpdate("UPDATE users SET " + queryParams + " WHERE id=" + this.currentId);

                this.usersTable.setModel(getUsers.getUsers());
                //hide id column
                this.usersTable.getColumnModel().getColumn(0).setMinWidth(0);
                this.usersTable.getColumnModel().getColumn(0).setMaxWidth(0);
                this.usersTable.getColumnModel().getColumn(0).setWidth(0);

                this.dispose();
            }
            catch(SQLException exception) { exception.printStackTrace(); }
        });

        cancelButton.addActionListener(e -> onCancel());

        //Call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { onCancel(); }
        });

        //Call onCancel() on escape
        dialogWindow.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void fillInputs(GetUsers getUsers, JTable usersTable, String id, String nick, String email, String confirmed, String isAdmin) {
        this.getUsers = getUsers;
        this.usersTable = usersTable;
        this.currentId = Integer.parseInt(id);

        nickTextField.setText(nick);
        emailTextField.setText(email);

        if(confirmed=="true") confirmedBox.setSelected(true);
        else confirmedBox.setSelected(false);

        if(isAdmin=="true") isAdminBox.setSelected(true);
        else isAdminBox.setSelected(false);
    }

    private void onCancel() { dispose(); }

    public void showDialog() {
        this.pack();
        this.setVisible(true);
    }
}
