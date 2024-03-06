/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.QR.model;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Arca
 */
public class ParameterReport {

    /**
     * @return the qrcode
     */
    public InputStream getQrcode() {
        return qrcode;
    }

    /**
     * @param qrcode the qrcode to set
     */
    public void setQrcode(InputStream qrcode) {
        this.qrcode = qrcode;
    }

    /**
     * @return the fields
     */
    public List<FieldReport> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<FieldReport> fields) {
        this.fields = fields;
    }

    public ParameterReport(InputStream qrcode, List<FieldReport> fields) {
        this.qrcode = qrcode;
        this.fields = fields;
    }

    
    
    public ParameterReport() {
    }
    
    
    
    
    
    private InputStream qrcode;
    private List<FieldReport> fields;
    
}
