package com.nxhoaf.dbhelper;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;



import org.dbunit.database.DatabaseConnection;

import org.dbunit.database.IDatabaseConnection;

import org.dbunit.database.QueryDataSet;

import org.dbunit.dataset.IDataSet;

import org.dbunit.dataset.xml.FlatXmlDataSet;




public class ExtractTestDataSet {

    public static void main(String[] args) throws Exception {

        // database connection

        Class driverClass = Class.forName("com.mysql.jdbc.Driver");

        Connection jdbcConnection = DriverManager.getConnection(

                "jdbc:mysql://localhost:3306/employees", "root", "");

        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);



        // partial database export

        QueryDataSet partialDataSet = new QueryDataSet(connection);

        partialDataSet.addTable("employees", "SELECT * FROM employees");

//        partialDataSet.addTable("recipe_ext_xref");

        FlatXmlDataSet.write(partialDataSet,

                new FileOutputStream("partial-dataset.xml"));



        // full database export

        IDataSet fullDataSet = connection.createDataSet();

        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full-dataset.xml"));

    }

}
