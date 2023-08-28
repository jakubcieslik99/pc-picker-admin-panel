package App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnect {
  String[] connectionData = new String[3];
  Statement statement = null;

  public String[] getPropertiesValues() {
    try {
      File file = new File("config.properties");
      Properties properties = new Properties();

      if (file.createNewFile()) {
        FileWriter writeDefault = new FileWriter("config.properties");
        writeDefault.write("url=jdbc:mysql://localhost:3306/programowanie-aplikacji-biznesowych\nuser=root\npassword=");
        writeDefault.close();
        System.out.println("\nBrak pliku konfiguracyjnego, stworzono domyślny plik konfiguracyjny...");
      } else System.out.println("\nOtwieranie istniejącego pliku konfiguracyjnego...");

      properties.load(new FileInputStream("config.properties"));

      connectionData[0] = properties.getProperty("url");
      connectionData[1] = properties.getProperty("user");
      connectionData[2] = properties.getProperty("password");
    } catch (IOException error) {
      System.out.println("\nBłąd pliku konfiguracyjnego: " + error);
      System.exit(0);
    }
    return connectionData;
  }

  public Statement connectToDatabase() {
    try {
      connectionData = getPropertiesValues();
      Connection connection = DriverManager.getConnection(connectionData[0], connectionData[1], connectionData[2]);
      statement = connection.createStatement();
    } catch (SQLException error) {
      System.out.println("\nBłąd łączenia z bazą danych: " + error);
      System.exit(0);
    }
    return statement;
  }
}
