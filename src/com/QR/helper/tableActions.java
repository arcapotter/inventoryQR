/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.QR.helper;

import com.QR.connection.vt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arca
 */
public class tableActions {
    
        
    public void tb_load(JTable table) {
        try (Connection con = vt.getDataSource().getConnection();
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM urunler")){
            // Clear the existing rows in the table
            DefaultTableModel dt = (DefaultTableModel) table.getModel();
            dt.setRowCount(0);
            
            // Iterate through the result set and add rows to the table model
            while (rs.next()) {
                // Create a Vector to store the data for a single row
                Vector v = new Vector();
                
                if(table.getColumnCount() == 9){
                    if(isAllColumnsNonNull(rs)){
                        v.add("Satıldı");
                    }
                    else{
                        v.add("");
                    }                    
                }

                // Add each column value to the Vector
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(7));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getString(8));
                v.add(rs.getString(6));

                // Add the Vector (representing a row) to the table model
                dt.addRow(v);
            }
        
        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.out.println(e);
        }
    }
    
        
    public void tbLineLoad(String searchID, JTable table){
        try(Connection con = vt.getDataSource().getConnection();
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM urunler WHERE barcode = '"+searchID+"'")){
            
            DefaultTableModel dt = (DefaultTableModel) table.getModel();
            dt.setRowCount(0);
            
            while(rs.next()){               
                Vector v = new Vector();
                
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(7));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getString(8));
                v.add(rs.getString(6));
                
                dt.addRow(v);        
            }
        } catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public void tb_loadFinal(JTable table) {
        try (Connection con = vt.getDataSource().getConnection();
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM urunler")){
            DefaultTableModel cikisTable = (DefaultTableModel) table.getModel();
            
            cikisTable.setRowCount(0);

            // Iterate through the result set and add rows to the table model
            while (rs.next()) {
                
                if(isAllColumnsNonNull(rs)){
                    // Create a Vector to store the data for a single row
                    Vector v = new Vector();


                    v.add(rs.getString(1));
                    v.add(rs.getString(2));
                    v.add(rs.getString(7));
                    v.add(rs.getString(3));
                    v.add(rs.getString(4));
                    v.add(rs.getString(5));
                    v.add(rs.getString(8));
                    v.add(rs.getString(6));
                    v.add(rs.getString(9));
                    v.add(rs.getString(10));

                    // Add the Vector (representing a row) to the table model
                    cikisTable.addRow(v);
                }
                
            }

        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.out.println(e);
        }
    }
    
    public void tbFilterLoad(String searchID, JComboBox<String> p_kategori, JComboBox<String> p_materyal,
            JComboBox<String> p_kalinlik, JComboBox<String> p_uzunluk, JComboBox<String> p_renk,
            JTable table) {

        String kalinlik = (p_kalinlik.getSelectedItem() != "") ? p_kalinlik.getSelectedItem().toString() : null;
        String uzunluk = (p_uzunluk.getSelectedItem() != " ") ? p_uzunluk.getSelectedItem().toString() : null;
        String renk = (p_renk.getSelectedItem() != " ") ? p_renk.getSelectedItem().toString() : null;
        String kategori = (p_kategori.getSelectedItem() != " ") ? p_kategori.getSelectedItem().toString() : null;
        String materyal = (p_materyal.getSelectedItem() != "") ? p_materyal.getSelectedItem().toString() : null;

        String query = "SELECT * FROM urunler WHERE ";

        if(kalinlik != null && query == "SELECT * FROM urunler WHERE "){
            query += "kalinlik = '"+kalinlik+"'";
        }
        else if(kalinlik != null){
            query += " AND kalinlik = '"+kalinlik+"'";
        }

        if(uzunluk != null && query == "SELECT * FROM urunler WHERE "){
            query += "uzunluk = '"+uzunluk+"'";
        }
        else if(uzunluk != null){
            query += " AND uzunluk = '"+uzunluk+"'";
        }

        if(renk != null && query == "SELECT * FROM urunler WHERE "){
            query += "renk = '"+renk+"'";
        }
        else if(renk != null){
            query += " AND renk = '"+renk+"'";
        }

        if(kategori != null && query == "SELECT * FROM urunler WHERE "){
            query += "kategori = '"+kategori+"'";
        }
        else if(kategori != null){
            query += " AND kategori = '"+kategori+"'";
        }

        if(materyal != null && query == "SELECT * FROM urunler WHERE "){
            query += "materyal = '"+materyal+"'";
        }
        else if(materyal != null){
            query += " AND materyal = '"+materyal+"'";
        }
        
        try (Connection con = vt.getDataSource().getConnection();
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(query)){
            
            DefaultTableModel dt = (DefaultTableModel) table.getModel();
            dt.setRowCount(0);
            
            while(rs.next()){
                Vector<Object> v = new Vector<>();

                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(7));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getString(8));
                v.add(rs.getString(6));

                dt.addRow(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    
    //HELPER HELPER HELPER HELPER  
    private boolean isAllColumnsNonNull(ResultSet rs) throws SQLException {
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            if (rs.getString(i) == null) {
                return false;
            }
        }
        return true;
    }
    
    
    
    
}
