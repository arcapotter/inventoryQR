/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.QR.helper;

import com.QR.connection.vt;
import com.QR.model.FieldReport;
import com.QR.model.ParameterReport;
import com.QR.print.ReportManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Arca
 */
public class buttonActions {
    
    private Component parentComponent;
    
    public buttonActions(Component parentComponent){
        this.parentComponent = parentComponent;
    }
    
    public buttonActions(){

    }
    
    public void deleteAction (String searchID){
        try(Connection con = vt.getDataSource().getConnection();
                Statement s = con.createStatement()){
            s.executeUpdate("DELETE FROM urunler WHERE barcode = '"+searchID+"' ");
            JOptionPane.showMessageDialog(null, "Veri Silindi");
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    public void findAction (String searchID, JComboBox<String> p_kategori, JComboBox<String> p_materyal,
                           JComboBox<String> p_kalinlik, JComboBox<String> p_uzunluk, JComboBox<String> p_renk,
                           JTextField p_kilogram){
        try(Connection con = vt.getDataSource().getConnection();
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(" SELECT * FROM urunler WHERE barcode ='"+searchID+"' ")){

            if (rs.next()) {
                p_kategori.setSelectedItem(rs.getString("kategori"));
                p_materyal.setSelectedItem(rs.getString("materyal"));
                p_kalinlik.setSelectedItem(rs.getString("kalinlik"));
                p_uzunluk.setSelectedItem(rs.getString("uzunluk"));
                p_renk.setSelectedItem(rs.getString("renk"));
                p_kilogram.setText(rs.getString("kilogram"));
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    public void insertAction (JComboBox<String> p_kategori, JComboBox<String> p_materyal,
                           JComboBox<String> p_kalinlik, JComboBox<String> p_uzunluk, JComboBox<String> p_renk,
                           JTextField p_kilogram){
        
        String kalinlik = p_kalinlik.getSelectedItem().toString();
        String uzunluk = p_uzunluk.getSelectedItem().toString();
        String renk = p_renk.getSelectedItem().toString();
        
        String kilogramText = p_kilogram.getText();

        String kategori = p_kategori.getSelectedItem().toString();
        String materyal = p_materyal.getSelectedItem().toString();
        
            
        try{
            if(isValidInteger(kilogramText)){
                int kilogram = Integer.parseInt(kilogramText);
                
                try(Connection con = vt.getDataSource().getConnection();
                    Statement s = con.createStatement()){
                    s.executeUpdate("INSERT INTO urunler (kategori,materyal,kalinlik,uzunluk,kilogram,renk) VALUES ('"+kategori+"','"+materyal+"','"+kalinlik+"','"+uzunluk+"','"+kilogram+"','"+renk+"') ");      
                }
                JOptionPane.showMessageDialog(null,"Data Kaydedildi");
 
            } else {
                //Kilogram ve Miktar integer kontrolü
                JOptionPane.showMessageDialog(null,"Kilogram sayı olmalı!","Değer Hatası",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void updateAction (String searchID, JComboBox<String> p_kategori, JComboBox<String> p_materyal,
                           JComboBox<String> p_kalinlik, JComboBox<String> p_uzunluk, JComboBox<String> p_renk,
                           JTextField p_kilogram, JTable table){
        
        String kalinlik = p_kalinlik.getSelectedItem().toString();
        String uzunluk = p_uzunluk.getSelectedItem().toString();
        String renk = p_renk.getSelectedItem().toString();
        
        String kilogram = p_kilogram.getText();

        String kategori = p_kategori.getSelectedItem().toString();
        String materyal = p_materyal.getSelectedItem().toString();
        
        try (Connection con = vt.getDataSource().getConnection();
                Statement s = con.createStatement()){
            s.executeUpdate("UPDATE urunler SET kalinlik='"+kalinlik+"', uzunluk='"+uzunluk+"', renk='"+renk+"', kilogram='"+kilogram+"', kategori='"+kategori+"', materyal='"+materyal+"' WHERE barcode ='"+searchID+"' ");
            
            tableActions tableAction = new tableActions();
            tableAction.tbLineLoad(searchID, table);
            
            JOptionPane.showMessageDialog(null, "Veri Güncellendi!");
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        /*
        Tabloları kontrol et!!!
        */
        
        tableActions tableAct = new tableActions();
        tableAct.tb_load(table);
    }
    
    // Unhoard
    public void sellAction(String searchID, String musteri){
        try(Connection con = vt.getDataSource().getConnection();
                Statement s = con.createStatement()){
            
            if(!musteri.isEmpty()){
                s.executeUpdate("UPDATE urunler SET musteri='"+musteri+"', CikisTarihi= CURRENT_TIMESTAMP WHERE barcode = '"+searchID+"'");
                JOptionPane.showMessageDialog(null, "Stoktan Çıkıldı", "Mesaj",JOptionPane.INFORMATION_MESSAGE);
            } 
            else{
                JOptionPane.showMessageDialog(null, "Müşteri bölümü boş!");
            }           
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    public void unSellAction(String searchID, String musteri){
        try(Connection con = vt.getDataSource().getConnection();
                Statement s = con.createStatement()){
            
            if (!musteri.isEmpty()) {
                s.executeUpdate("UPDATE urunler SET musteri = NULL, cikisTarihi = NULL WHERE barcode = '"+searchID+"' ");
                JOptionPane.showMessageDialog(null, "Satış iptal edildi", "Mesaj", JOptionPane.INFORMATION_MESSAGE);
            } else{
                JOptionPane.showMessageDialog(null, "Böyle bir satış yok");
            }
        }catch(HeadlessException | SQLException e){
            System.out.println(e);
        }
        
    }
    
    public void printAction(String searchID){
        try {
            List<FieldReport> fields = new ArrayList<>();

            try (Connection con = vt.getDataSource().getConnection();
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery("SELECT * FROM urunler WHERE barcode = '"+searchID+"'")){

                while (rs.next()) {
                    fields.add(new FieldReport(rs.getString("kategori"), rs.getString("kalinlik"), rs.getString("kilogram"), rs.getString("uzunluk"), rs.getString("materyal")));
                }
            }

            ParameterReport dataprint = new ParameterReport(generateQrcode(searchID), fields);
            ReportManager.getInstance().printReport(dataprint);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
    
    public void exportAction(String tableName, JTable table){
        try {
            
            JFileChooser jFileChooser =  new JFileChooser();
            jFileChooser.showSaveDialog(parentComponent);
            File saveFile = jFileChooser.getSelectedFile();
            
            if(saveFile != null){
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Urunler");
                
                Row rowCol = sheet.createRow(0);
                for(int col = 0; col < table.getColumnCount(); col++){
                    Cell cell = rowCol.createCell(col);
                    cell.setCellValue(table.getColumnName(col));
                }
                
                for(int j = 0; j < table.getRowCount(); j++){
                    Row row = sheet.createRow(j+1);
                    for(int k = 0; k < table.getColumnCount(); k++){
                        Cell cell = row.createCell(k);
                        if(table.getValueAt(j, k)!= null){
                            cell.setCellValue(table.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                openFile(saveFile.toString());    
            }else{
                JOptionPane.showMessageDialog(null, "Dosya Kaydedilemedi","Mesaj",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
    }                                            


    
    

    
    //HELPER HELPER HELPER HELPER HELPER
    
    private boolean isValidInteger(String input){
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    
    private InputStream generateQrcode(String barcodeNum) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeNum, BarcodeFormat.QR_CODE, 54, 56, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    private void openFile(String file){
        try{
            File path = new File(file);
            Desktop.getDesktop().open(path);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
        
    }
    
    
    
    
}
