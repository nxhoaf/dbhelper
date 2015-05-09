/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nxhoaf.dbhelper.controller;

import com.nxhoaf.dbhelper.domain.ConnectionInfo;
import com.nxhoaf.dbhelper.domain.Query;
import java.sql.SQLException;
import java.util.Observer;
import javafx.beans.Observable;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;

/**
 *
 * @author nxhoaf
 */
public interface ExtractDataController {
    public void extractPartialData(ConnectionInfo connectionInfo, Query query) throws ClassNotFoundException, SQLException, DatabaseUnitException, DataSetException;
    public void extractAllData(ConnectionInfo connectionInfo);
}
