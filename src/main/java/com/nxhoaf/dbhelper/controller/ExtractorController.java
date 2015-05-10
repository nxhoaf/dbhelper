/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nxhoaf.dbhelper.controller;

import com.nxhoaf.dbhelper.domain.ExtractorInfo;

/**
 *
 * @author nxhoaf
 */
public interface ExtractorController {
    public void extractPartialData(ExtractorInfo extractorInfo);
    public void extractAllData(ExtractorInfo extractorInfo);
    public ExtractorInfo getDefaultExtractInfo();
    public void xmlToDB(ExtractorInfo extractorInfo);
}
