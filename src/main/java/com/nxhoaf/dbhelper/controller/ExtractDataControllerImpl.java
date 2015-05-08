package com.nxhoaf.dbhelper.controller;

import com.nxhoaf.dbhelper.domain.ConnectionInfo;
import com.nxhoaf.dbhelper.domain.Query;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import org.dbunit.DatabaseUnitException;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class ExtractDataControllerImpl implements ExtractDataController {
    
    

    public static void main(String[] args) throws Exception {

        // database connection
        Class driverClass = Class.forName("com.mysql.jdbc.Driver");

        Connection jdbcConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employees", "root", "");

        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // partial database export
//        QueryDataSet partialDataSet = new QueryDataSet(connection);
//        partialDataSet.addTable("employees", "SELECT * FROM employees");
//        
//        System.out.println("All tables: ");
//        for (String table : partialDataSet.getTableNames()) {
//            System.out.println(table);
//        }
        

//        partialDataSet.addTable("recipe_ext_xref");
//        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial-dataset.xml"));

        // full database export
        IDataSet fullDataSet = connection.createDataSet();
        System.out.println("All tables: " + Arrays.toString(fullDataSet.getTableNames()));

//        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full-dataset.xml"));

    }
    
    private IDatabaseConnection getConnection(ConnectionInfo connectionInfo) throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class driverClass = Class.forName(connectionInfo.getDriverClass());
        Connection jdbcConnection = DriverManager.getConnection( connectionInfo.getConnectionUrl(), connectionInfo.getUsername(), connectionInfo.getPassword());
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        return connection;
    }

    public void extractPartialData(ConnectionInfo connectionInfo, Query query) throws ClassNotFoundException, SQLException, DatabaseUnitException {
        IDatabaseConnection connection = getConnection(connectionInfo);
        
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable(query.getTableName(), query.getQuery());
    }

    public void extractAllData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
