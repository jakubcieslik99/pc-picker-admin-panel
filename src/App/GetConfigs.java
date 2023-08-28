package App;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class GetConfigs {
  Statement statement = null;

  public GetConfigs(Statement statement) {
    this.statement = statement;
  }

  public DefaultTableModel getConfigs() throws SQLException {
    ResultSet query = this.statement.executeQuery("SELECT ANY_VALUE(setups.id), ANY_VALUE(users.nick), COUNT(*), ANY_VALUE(setups.composedTime) FROM setups_components INNER JOIN setups ON setups_components.setup_id=setups.id INNER JOIN users ON setups.composedBy=users.id GROUP BY setups_components.setup_id;");
    return buildTable(query);
  }

  private DefaultTableModel buildTable(ResultSet query) throws SQLException {
    ResultSetMetaData metaData = (ResultSetMetaData) query.getMetaData();

    //nazwy kolumn tabeli
    Vector<String> columnNames = new Vector<String>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
      //System.out.print(column + ": " + metaData.getColumnName(column) + "\n");
      if (column == 1) columnNames.add("Numer konfiguracji");
      else if (column == 2) columnNames.add("Utworzył");
      else if (column == 3) columnNames.add("Ilość komponentów");
      else columnNames.add("Data utworzenia");
    }

    //dane tabeli
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    while (query.next()) {
      Vector<Object> vector = new Vector<Object>();
      for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
        vector.add(query.getObject(columnIndex));
      }
      data.add(vector);
    }

    return new DefaultTableModel(data, columnNames);
  }
}
