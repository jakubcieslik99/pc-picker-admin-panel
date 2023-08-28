package App;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class GetComponents {
  Statement statement = null;

  public GetComponents(Statement statement) {
    this.statement = statement;
  }

  public DefaultTableModel getComponents() throws SQLException {
    ResultSet query = this.statement.executeQuery("SELECT components.id, components.name, components.type, components.link, components.caseCompatibility, components.cpuCompatibility, components.ramCompatibility, users.nick FROM components INNER JOIN users ON components.addedBy=users.id;");
    return buildTable(query);
  }

  private DefaultTableModel buildTable(ResultSet query) throws SQLException {
    ResultSetMetaData metaData = (ResultSetMetaData) query.getMetaData();

    //nazwy kolumn tabeli
    Vector<String> columnNames = new Vector<String>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
      //System.out.print(column + ": " + metaData.getColumnName(column) + "\n");
      if (column == 1) columnNames.add("Id");
      else if (column == 2) columnNames.add("Nazwa");
      else if (column == 3) columnNames.add("Typ");
      else if (column == 4) columnNames.add("Link");
      else if (column == 5) columnNames.add("Kompat. obudowy");
      else if (column == 6) columnNames.add("Kompat. CPU");
      else if (column == 7) columnNames.add("Kompat. RAM");
      else columnNames.add("Dodał");
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
