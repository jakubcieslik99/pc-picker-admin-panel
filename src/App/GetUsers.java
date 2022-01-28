package App;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;

public class GetUsers {
    Statement statement = null;

    public GetUsers(Statement statement) {
        this.statement = statement;
    }

    public DefaultTableModel getUsers() throws SQLException {
        ResultSet query = this.statement.executeQuery("SELECT id, nick, email, confirmed, isAdmin, registeredTime FROM users");
        return buildTable(query);
    }

    private DefaultTableModel buildTable(ResultSet query) throws SQLException {
        ResultSetMetaData metaData = (ResultSetMetaData) query.getMetaData();

        //nazwy kolumn tabeli
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for(int column=1; column<=columnCount; column++) {
            //System.out.print(column + ": " + metaData.getColumnName(column) + "\n");
            if(column==1) columnNames.add("Id");
            else if(column==2) columnNames.add("Nick");
            else if(column==3) columnNames.add("Email");
            else if(column==4) columnNames.add("Potwierdzone");
            else if(column==5) columnNames.add("Administrator");
            else columnNames.add("Data rejestracji");
        }

        //dane tabeli
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while(query.next()) {
            Vector<Object> vector = new Vector<Object>();
            for(int columnIndex=1; columnIndex<=columnCount; columnIndex++) {
                vector.add(query.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
