package com.nxhoaf.dbhelper.controller;

import com.nxhoaf.dbhelper.view.ExtractorObserver;
import com.nxhoaf.dbhelper.domain.ExtractorInfo;
import com.nxhoaf.dbhelper.domain.ConnectionInfo;
import com.nxhoaf.dbhelper.domain.PropertyReader;
import com.nxhoaf.dbhelper.domain.QueryInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dbunit.DatabaseUnitException;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

public class ExtractorControllerImpl implements ExtractorController {
    private final Set<ExtractorObserver> observers;
    
    public ExtractorControllerImpl() {
        observers = new HashSet<ExtractorObserver>();
    }

    private IDatabaseConnection getConnection(ConnectionInfo databaseInfo) throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class driverClass = Class.forName(databaseInfo.getDriverClass());
        Connection jdbcConnection = DriverManager.getConnection(databaseInfo.getConnectionUrl(), databaseInfo.getUsername(), databaseInfo.getPassword());
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        return connection;
    }

    public void dbToXmlPartial(ExtractorInfo extractorInfo) {
        IDatabaseConnection connection;
        try {
            connection = getConnection(extractorInfo.getConnectionInfo());
            QueryDataSet partialDataSet = new QueryDataSet(connection);
            QueryInfo query = extractorInfo.getQueryInfo();
            partialDataSet.addTable(query.getTableName(), query.getQuery());
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream(extractorInfo.getFileLocation()));
            notifyObservers("DB to XML completed!");
        } catch (Exception e) {
            notifyObservers(e.toString());
        }
    }

    public void dbToXmlFull(ExtractorInfo extractorInfo) {
        IDatabaseConnection connection;
        try {
            connection = getConnection(extractorInfo.getConnectionInfo());
            IDataSet fullDataSet = connection.createDataSet();
            FlatXmlDataSet.write(fullDataSet, new FileOutputStream(extractorInfo.getFileLocation()));
            notifyObservers("DB to XML completed!");
        } catch (Exception e) {
            notifyObservers(e.toString());
        }
    }

    public void xmlToDB(ExtractorInfo extractorInfo) {
        IDatabaseConnection connection;
        try {
            connection = getConnection(extractorInfo.getConnectionInfo());
            IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(extractorInfo.getFileLocation()));
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
            notifyObservers("XML to DB completed!");
        } catch (Exception e) {
            notifyObservers(e.toString());
        }
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

    public void addObserver(ExtractorObserver extractorObserver) {
        observers.add(extractorObserver);
    }

    public void removeObserver(ExtractorObserver extractorObserver) {
        observers.remove(extractorObserver);
                
    }

    public void notifyObservers(String message) {
        for (ExtractorObserver observer: observers) {
            observer.update(message);
        }
    }
}
