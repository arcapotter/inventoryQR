package inventoryqr;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class vt {

    private static final HikariDataSource DATASOURCE;
    
    /*public static Connection myCon() {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://54.93.35.165:3306/mysql", "root", "armurat77,");
            System.out.print(con);

        } catch (SQLException ex) {
            Logger.getLogger(vt.class.getName()).log(Level.SEVERE, null, ex);
        }

        return con;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }
    
*/
    
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://54.93.35.165:3306/mysql");
        config.setUsername("root");
        config.setPassword("armurat77,");
        config.setMaximumPoolSize(10);
        
        DATASOURCE = new HikariDataSource(config);
    }
    
    public static HikariDataSource getDataSource(){
        return DATASOURCE;
    }
    
    
}
