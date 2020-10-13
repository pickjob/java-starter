package framework.jdbc;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger(JDBCShowCase.class);

    @Override
    public void showSomething() {
        logger.info("JDBC反射数据类表、字段类型");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:framework.mysql://framework.mysql/sample?useSSL=false", "china", "chinese");
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet dataBaseResultSet = databaseMetaData.getTables(null, null, null, null);
            showResultSetInfo(dataBaseResultSet);
            while (dataBaseResultSet.next()) {
                logger.info("TABLE_CAT: {}, TABLE_SCHEM: {}, TABLE_NAME: {}, TABLE_TYPE: {}, REMARKS: {}, TYPE_CAT: {}, TYPE_SCHEM: {}, TYPE_NAME: {}, SELF_REFERENCING_COL_NAME: {}, REF_GENERATION: {}",
                        dataBaseResultSet.getString("TABLE_CAT"),
                        dataBaseResultSet.getString("TABLE_SCHEM"),
                        dataBaseResultSet.getString("TABLE_NAME"),
                        dataBaseResultSet.getString("TABLE_TYPE"),
                        dataBaseResultSet.getString("REMARKS"),
                        dataBaseResultSet.getString("TYPE_CAT"),
                        dataBaseResultSet.getString("TYPE_SCHEM"),
                        dataBaseResultSet.getString("TYPE_NAME"),
                        dataBaseResultSet.getString("SELF_REFERENCING_COL_NAME"),
                        dataBaseResultSet.getString("REF_GENERATION"));
            }
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from offices");
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

//    @Override
//    public boolean isShow() {
//        return true;
//    }

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
