/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.QR.helper;

import com.QR.connection.vt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arca
 */
public class test {
    
    
        public void tbFilterLoad(String searchID, JComboBox<String> p_kategori, JComboBox<String> p_materyal,
            JComboBox<String> p_kalinlik, JComboBox<String> p_uzunluk, JComboBox<String> p_renk,
            JTextField p_kilogram, JTable table) {

        String kalinlik = (p_kalinlik.getSelectedItem() != null) ? p_kalinlik.getSelectedItem().toString() : null;
        String uzunluk = (p_uzunluk.getSelectedItem() != null) ? p_uzunluk.getSelectedItem().toString() : null;
        String renk = (p_renk.getSelectedItem() != null) ? p_renk.getSelectedItem().toString() : null;
        String kategori = (p_kategori.getSelectedItem() != null) ? p_kategori.getSelectedItem().toString() : null;
        String materyal = (p_materyal.getSelectedItem() != null) ? p_materyal.getSelectedItem().toString() : null;

        String query = "SELECT * FROM urunler WHERE (kategori = ? OR kategori IS NULL) AND (kalinlik = ? OR kalinlik IS NULL) AND (materyal = ? OR materyal IS NULL) AND (uzunluk = ? OR uzunluk IS NULL) AND (renk = ? OR renk IS NULL)";

        try (Connection con = vt.getDataSource().getConnection();
                PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, searchID);
            pstmt.setString(2, kategori);
            pstmt.setString(3, kalinlik);
            pstmt.setString(4, materyal);
            pstmt.setString(5, uzunluk);
            pstmt.setString(6, renk);

            try (ResultSet rs = pstmt.executeQuery()) {
                DefaultTableModel dt = (DefaultTableModel) table.getModel();
                dt.setRowCount(0);

                while (rs.next()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
