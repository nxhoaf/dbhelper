package com.nxhoaf.dbhelper.controller;

import com.nxhoaf.dbhelper.domain.ExtractorInfo;
import com.nxhoaf.dbhelper.domain.ConnectionInfo;
import com.nxhoaf.dbhelper.domain.PropertyReader;
import com.nxhoaf.dbhelper.domain.QueryInfo;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dbunit.DatabaseUnitException;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class ExtractorControllerImpl implements ExtractorController {
    private IDatabaseConnection getConnection(ConnectionInfo databaseInfo) throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class driverClass = Class.forName(databaseInfo.getDriverClass());
        Connection jdbcConnection = DriverManager.getConnection(databaseInfo.getConnectionUrl(), databaseInfo.getUsername(), databaseInfo.getPassword());
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        return connection;
    }

    public void extractPartialData(ExtractorInfo extractorInfo) throws ClassNotFoundException, SQLException, DatabaseUnitException, DataSetException {
        IDatabaseConnection connection = getConnection(extractorInfo.getConnectionInfo());

        QueryDataSet partialDataSet = new QueryDataSet(connection);
        QueryInfo query = extractorInfo.getQueryInfo();
        partialDataSet.addTable(query.getTableName(), query.getQuery());
        try {
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream(extractorInfo.getFileLocation()));
        } catch (IOException ex) {
            Logger.getLogger(ExtractorControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void extractAllData(ExtractorInfo extractorInfo) {
        IDatabaseConnection connection;
        try {
            connection = getConnection(extractorInfo.getConnectionInfo());
            IDataSet fullDataSet = connection.createDataSet();
            FlatXmlDataSet.write(fullDataSet, new FileOutputStream("output/full-dataset.xml"));
        } catch (IOException ex) {
            Logger.getLogger(ExtractorControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataSetException ex) {
            Logger.getLogger(ExtractorControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExtractorControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ExtractorControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseUnitException ex) {
            Logger.getLogger(ExtractorControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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

    public ExtractorInfo getDefaultExtractInfo() {
        ConnectionInfo connectionInfo = new ConnectionInfo();
        
        connectionInfo.setDriverClass(PropertyReader.getProperty(PropertyReader.CONNECTION_DRIVER));
        connectionInfo.setConnectionUrl(PropertyReader.getProperty(PropertyReader.CONNECTION_URL));
        connectionInfo.setUsername(PropertyReader.getProperty(PropertyReader.USER_NAME));
        connectionInfo.setPassword(PropertyReader.getProperty(PropertyReader.PASSWORD));
        
        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setTableName(PropertyReader.getProperty(PropertyReader.TABLE_NAME));
        queryInfo.setQuery(PropertyReader.getProperty(PropertyReader.SQL_QUERY));
        
        ExtractorInfo extractorInfo = new ExtractorInfo();
        extractorInfo.setConnectionInfo(connectionInfo);
        extractorInfo.setQueryInfo(queryInfo);
        extractorInfo.setFileLocation(PropertyReader.getProperty(PropertyReader.FILE_LOCATION));
        
        return extractorInfo;
    }
}
