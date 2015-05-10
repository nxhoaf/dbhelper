/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nxhoaf.dbhelper.controller;

import com.nxhoaf.dbhelper.view.ExtractorObserver;

/**
 *
 * @author nxhoaf
 */
public interface ExtractorObservable {
    public void addObserver(ExtractorObserver extractorObserver);
    public void removeObserver(ExtractorObserver extractorObserver);
    public void notifyObservers(String message);
}
