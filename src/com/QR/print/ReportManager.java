package com.QR.print;

import com.QR.model.ParameterReport;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * ReportManager class to manage JasperReports functionality.
 */
public class ReportManager {

    private static ReportManager instance;
    private JasperReport report;

    /**
     * Get the singleton instance of ReportManager.
     *
     * @return The singleton instance.
     */
    public static ReportManager getInstance() {
        if (instance == null) {
            instance = new ReportManager();
        }
        return instance;
    }

    private ReportManager() {
}

    /**
     * Compile the JasperReport from the JRXML file.
     *
     * @throws JRException If an error occurs during compilation.
     */
    public void compileReport() throws JRException {
        try (InputStream stream = getClass().getResourceAsStream("/com/QR/print/report.jrxml")) {
            if (stream == null) {
                throw new JRException("JRXML file not found");
            }
            report = JasperCompileManager.compileReport(stream);
        } catch (IOException e) {
            throw new JRException("Error reading JRXML file", e);
        }
    }

    /**
     * Print the JasperReport using the provided data.
     *
     * @param data The data for the report.
     * @throws JRException If an error occurs during report printing.
     */
    public void printReport(ParameterReport data) throws JRException {
        if (report == null) {
            throw new JRException("Report not compiled. Call compileReport() first.");
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("qrcode", data.getQrcode());
        

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());

        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
        view(print);
    }

    /**
     * Display the JasperPrint using JasperViewer.
     *
     * @param print The JasperPrint to be viewed.
     * @throws JRException If an error occurs during viewing.
     */
    private void view(JasperPrint print) throws JRException {
        JasperViewer.viewReport(print, false);
    }
}
