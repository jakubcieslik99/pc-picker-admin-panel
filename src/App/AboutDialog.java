package App;

import javax.swing.*;
import java.awt.event.*;

public class AboutDialog extends JDialog {
    private JPanel dialogWindow;
    private JButton buttonOK;

    public AboutDialog() {
        this.setTitle("O aplikacji");
        this.setContentPane(this.dialogWindow);
        this.setModal(true);
        this.getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        //Call onOK() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { onOK(); }
        });

        //Call onOK() on escape
        dialogWindow.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() { dispose(); }

    public void showDialog() {
        this.pack();
        this.setVisible(true);
    }
}
