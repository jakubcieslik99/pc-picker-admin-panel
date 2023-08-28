package App;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class PopupMenus extends JPopupMenu {
  private final JPopupMenu popupMenuUsers, popupMenuComponents, popupMenuConfigs;
  private final JMenuItem editUserOption, deleteUserOption, editComponentOption, deleteComponentOption, deleteConfigOption;

  public PopupMenus(GUI gui) {
    //Configure users popup menu
    popupMenuUsers = new JPopupMenu();

    editUserOption = new JMenuItem("Edytuj");
    editUserOption.addActionListener(e -> gui.editUserPopup());

    deleteUserOption = new JMenuItem("Usuń");
    deleteUserOption.addActionListener(e -> gui.deleteUserFunction());

    popupMenuUsers.add(editUserOption);
    popupMenuUsers.add(deleteUserOption);

    //Configure components popup menu
    popupMenuComponents = new JPopupMenu();

    editComponentOption = new JMenuItem("Edytuj");
    deleteComponentOption = new JMenuItem("Usuń");

    popupMenuComponents.add(editComponentOption);
    popupMenuComponents.add(deleteComponentOption);

    //Configure configs popup menu
    popupMenuConfigs = new JPopupMenu();

    deleteConfigOption = new JMenuItem("Usuń");

    popupMenuConfigs.add(deleteConfigOption);
  }

  public void showPopupMenuUsers(JTable usersTable, MouseEvent e) {
    popupMenuUsers.show(usersTable, e.getX(), e.getY());
  }

  public void setPopupMenuUsers(Boolean set) {
    editUserOption.setEnabled(set);
    deleteUserOption.setEnabled(set);
  }

  public void showPopupMenuComponents(JTable componentsTable, MouseEvent e) {
    popupMenuComponents.show(componentsTable, e.getX(), e.getY());
  }

  public void setPopupMenuComponents(Boolean set) {
    editComponentOption.setEnabled(set);
    deleteComponentOption.setEnabled(set);
  }

  public void showPopupMenuConfigs(JTable configsTable, MouseEvent e) {
    popupMenuConfigs.show(configsTable, e.getX(), e.getY());
  }

  public void setPopupMenuConfigs(Boolean set) {
    deleteConfigOption.setEnabled(set);
  }
}
