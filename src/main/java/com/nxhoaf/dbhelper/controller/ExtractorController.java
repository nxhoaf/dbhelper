/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nxhoaf.dbhelper.controller;

import com.nxhoaf.dbhelper.domain.ExtractorInfo;
import javafx.beans.Observable;

/**
 *
 * @author nxhoaf
 */
public interface ExtractorController extends ExtractorObservable {
    public void dbToXmlPartial(ExtractorInfo extractorInfo);
    public void dbToXmlFull(ExtractorInfo extractorInfo);
    public void xmlToDB(ExtractorInfo extractorInfo);
    public ExtractorInfo getDefaultExtractInfo();
}
