package com.adam.rec.jdbc;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author adam
 * 创建于 2018-03-10 14:17.
 */
@Component
public class JdbcUtil {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
    private final String DRIVER = resourceBundle.getString("driver");
    private final String URL = resourceBundle.getString("url");
    private final String USERNAME = resourceBundle.getString("user");
    private final String PASSWORD = resourceBundle.getString("passwd");

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private void init() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("数据库驱动已加载");//真正的初始化在静态语句块中完成
    }

    public void initAll() {
        init();
        getConnection();
        createStatement();
    }

    public void closeAll() {
        closeResultSet();
        closeStatements();
        closeConnection();
    }

    public void getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("获取数据库连接失败");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("关闭数据库连接时出现异常");
                e.printStackTrace();
            }
        }
    }

    public DatabaseMetaData getMetaDatabase() {
        DatabaseMetaData databaseMetaData = null;
        if(connection != null) {
            try {
                databaseMetaData = connection.getMetaData();
            } catch (SQLException e) {
                System.out.println("获取Connection的DatabaseMetaData出现异常");
                e.printStackTrace();
            }
        }
        return databaseMetaData;
    }

    public ParameterMetaData getMetaParameter() {
        ParameterMetaData parameterMetaData = null;
        if(preparedStatement != null) {
            try {
                parameterMetaData = preparedStatement.getParameterMetaData();
            } catch (SQLException e) {
                System.out.println("获取PreparedStatement的ParameterMetaData时出现异常");
                e.printStackTrace();
            }
        }
        return parameterMetaData;
    }

    public ResultSetMetaData getMetaResultSet() {
        ResultSetMetaData resultSetMetaData = null;
        if (resultSet != null) {
            try {
                resultSetMetaData = resultSet.getMetaData();
            } catch (SQLException e) {
                System.out.println("获取ResultSet的ResultSetMetaData时出现异常");
                e.printStackTrace();
            }
        }
        return resultSetMetaData;
    }

    public void createStatement() {
        if(connection != null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                System.out.println("创建Statement出现异常");
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建PreparedStatement对象。
     * @param sql 含有？的SQL执行语句
     * @return 成功创建时返回PreparedStatement对象引用，失败时返回null
     */
    public PreparedStatement createPreparedStatement(String sql) {
        preparedStatement = null;
        if(connection != null) {
            try {
                preparedStatement = connection.prepareStatement(sql);
            } catch (SQLException e) {
                System.out.println("创建PreparedStatement出现异常");
                e.printStackTrace();
            }
        }
        return preparedStatement;
    }

    public void closeStatements() {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("关闭Statement出现异常");
                e.printStackTrace();
            }
        }
        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("关闭PreparedStatement出现异常");
                e.printStackTrace();
            }
        }
    }

    public ResultSet executeQuery(String sql) {
        resultSet = null;
        if(statement != null) {
            try {
                resultSet = statement.executeQuery(sql);

            } catch (SQLException e) {
                System.out.println("使用Statement进行数据库查询时出现异常");
                e.printStackTrace();
            }
        }
        return resultSet;
    }

    /**
     * 查询前要先对PreparedStatement设置相应的参数，因为参数可能是各种类型，暂时没有为此封装一个方法。
     * @return 查询后的ResultSet结果集。
     */
    public ResultSet executeQueryPrepared() {
        resultSet = null;
        if(preparedStatement != null) {
            try {
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                System.out.println("使用PreparedStatement进行数据库查询时出现异常");
                e.printStackTrace();
            }
        }
        return resultSet;
    }

    /**
     * 使用Statement执行update操作。
     * 此方法可能未经过测试。
     * @param sql 要执行的更新语句。
     * @return 返回-1时表示出现异常或者尚未创建Statement；返回0时表示语句不影响到任何记录；返回n(n>1)时表示语句影响到的记录数。
     */
    public int executeUpdate(String sql) {
        int res = -1;
        if(statement != null) {
            try {
                res = statement.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("使用Statement进行数据库更新操作时出现异常");
                e.printStackTrace();
            }
        }
        return res;
    }

    public int executeUpdatePrepared() {
        int res = -1;
        if(preparedStatement != null) {
            try {
                res = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("使用PreparedStatement进行数据库更新操作时出现异常");
                e.printStackTrace();
            }
        }
        return res;
    }

    public void closeResultSet() {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("关闭ResultSet时出现异常");
                e.printStackTrace();
            }
        }
    }

    public void batchOperationAdd(String sql){
        if(statement != null) {
            try {
                statement.addBatch(sql);
            } catch (SQLException e) {
                System.out.println("使用Statement添加批处理语句出现异常");
                e.printStackTrace();
            }
        }
    }

    public void batchOperationsAddPrepared() {
        if(preparedStatement != null) {
            try {
                preparedStatement.addBatch();
            } catch (SQLException e) {
                System.out.println("使用PreparedStatement添加批处理语句时出现异常");
                e.printStackTrace();
            }
        }
    }

    /**
     * 针对Statement的批处理执行。
     * @return 每一条语句执行的成功更新计数组成的int数组。
     */
    public int[] batchOperationsExecute() {
        int[] res = {-1};
        if(statement != null) {
            try {
                res = statement.executeBatch();
            } catch (SQLException e) {
                System.out.println("使用Statement进行批处理操作时出现异常");
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 针对PreparedStatement的批处理执行。
     * @return 每一条语句执行的成功更新计数组成的int数组。
     */
    public int[] batchOperationsExecutePrepared() {
        int[] res = {-1};
        if(preparedStatement != null) {
            try {
                res = preparedStatement.executeBatch();
            } catch (SQLException e) {
                System.out.println("使用PreparedStatement进行批处理操作时出现异常");
                e.printStackTrace();
            }
        }
        return res;
    }

    public void batchOperationsClear() {
        if(statement != null) {
            try {
                statement.clearBatch();
            } catch (SQLException e) {
                System.out.println("Statement清理批处理语句时出现异常");
                e.printStackTrace();
            }
        }
    }

    public void batchOperationsClearPrepared() {
        if(preparedStatement != null) {
            try {
                preparedStatement.clearBatch();
            } catch (SQLException e) {
                System.out.println("PreparedStatement清理批处理语句时出现异常");
                e.printStackTrace();
            }
        }
    }

}
