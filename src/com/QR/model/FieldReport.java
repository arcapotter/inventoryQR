/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.QR.model;

/**
 *
 * @author Arca
 */
public class FieldReport {

    /**
     * @return the kategori
     */
    public String getKategori() {
        return kategori;
    }

    /**
     * @param kategori the kategori to set
     */
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    /**
     * @return the kalinlik
     */
    public String getKalinlik() {
        return kalinlik;
    }

    /**
     * @param kalinlik the kalinlik to set
     */
    public void setKalinlik(String kalinlik) {
        this.kalinlik = kalinlik;
    }

    /**
     * @return the kilogram
     */
    public String getKilogram() {
        return kilogram;
    }

    /**
     * @param kilogram the kilogram to set
     */
    public void setKilogram(String kilogram) {
        this.kilogram = kilogram;
    }

    /**
     * @return the uzunluk
     */
    public String getUzunluk() {
        return uzunluk;
    }

    /**
     * @param uzunluk the uzunluk to set
     */
    public void setUzunluk(String uzunluk) {
        this.uzunluk = uzunluk;
    }

    /**
     * @return the materyal
     */
    public String getMateryal() {
        return materyal;
    }

    /**
     * @param materyal the materyal to set
     */
    public void setMateryal(String materyal) {
        this.materyal = materyal;
    }

    public FieldReport(String kategori, String kalinlik, String kilogram, String uzunluk, String materyal) {
        this.kategori = kategori;
        this.kalinlik = kalinlik;
        this.kilogram = kilogram;
        this.uzunluk = uzunluk;
        this.materyal = materyal;
    }
    
    public FieldReport(){
        
    }
    
    private String kategori;
    private String kalinlik;
    private String kilogram;
    private String uzunluk;
    private String materyal;
    
    
}
