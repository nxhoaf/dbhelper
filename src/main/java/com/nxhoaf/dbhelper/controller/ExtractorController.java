/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nxhoaf.dbhelper.controller;

import com.nxhoaf.dbhelper.domain.ExtractorInfo;
import com.nxhoaf.dbhelper.domain.QueryInfo;
import java.sql.SQLException;
import java.util.Observer;
import javafx.beans.Observable;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;

/**
 *
 * @author nxhoaf
 */
public interface ExtractorController {
    public void extractPartialData(ExtractorInfo connectionInfo) throws ClassNotFoundException, SQLException, DatabaseUnitException, DataSetException;
    public void extractAllData(ExtractorInfo connectionInfo);
    public ExtractorInfo getDefaultExtractInfo();
}
