package basic.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCShowCase {
    private static Logger logger = LogManager.getLogger(JDBCShowCase.class);

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8888/mysql?useSSL=false&allowPublicKeyRetrieval=true", "china", "chinese");
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet dataBaseResultSet = databaseMetaData.getTables("mysql", "mysql", null, null);
            showResultSetInfo(dataBaseResultSet);
            while (dataBaseResultSet.next()) {
                logger.info("{}, {}, {}, {}",
                        dataBaseResultSet.getString("TABLE_TYPE"),
                        dataBaseResultSet.getString("TABLE_CAT"),
                        dataBaseResultSet.getString("TABLE_SCHEM"),
                        dataBaseResultSet.getString("TABLE_NAME")
                );
            }
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from general_log");
            showResultSetInfo(rs);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private static void showResultSetInfo(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        List<String> columnNameList = new ArrayList<>();
        for (int i=1; i <= resultSetMetaData.getColumnCount(); i++) {
            columnNameList.add(resultSetMetaData.getColumnName(i));
            logger.info("Column name: {}({}), type: {}, type_name: {}({}), class_name: {}",
                    resultSetMetaData.getColumnName(i),
                    resultSetMetaData.getColumnLabel(i),
                    resultSetMetaData.getColumnType(i),
                    resultSetMetaData.getColumnTypeName(i),
                    resultSetMetaData.getPrecision(i),
                    resultSetMetaData.getColumnClassName(i)
            );
        }
    }
}
