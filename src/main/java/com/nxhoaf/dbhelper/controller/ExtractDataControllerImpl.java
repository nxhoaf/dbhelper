package com.nxhoaf.dbhelper.controller;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class ExtractDataControllerImpl implements ExtractDataController {

    public static void main(String[] args) throws Exception {

        // database connection
        Class driverClass = Class.forName("com.mysql.jdbc.Driver");

        Connection jdbcConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employees", "root", "");

        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("employees", "SELECT * FROM employees");
        
        System.out.println("All tables: ");
        for (String table : partialDataSet.getTableNames()) {
            System.out.println(table);
        }

//        partialDataSet.addTable("recipe_ext_xref");
//        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial-dataset.xml"));

        // full database export
        IDataSet fullDataSet = connection.createDataSet();

//        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full-dataset.xml"));

    }

    public void extractPartialData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void extractAllData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
