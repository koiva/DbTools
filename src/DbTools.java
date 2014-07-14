import oracle.jdbc.driver.OracleDriver;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class DbTools
{

    private static boolean defineTnsAdmin()
    {
        String path = System.getenv("TNS_ADMIN");

        if (path == null)
            return false;

        System.setProperty("oracle.net.tns_admin", path);
        return true;
    }


    public static void main(String[] args) throws Exception
    {
        System.out.println("Иструменты для базы данных Oracle.");

        if (!defineTnsAdmin())
        {
            System.out.println("Пожалуйста установите значение системной перменной TNS_ADMIN, которая указыват на каталог с файлом tnsnames.ora.");
            return;
        }


        String thinConn = "jdbc:oracle:thin:system/manager2@DB13";
        DriverManager.registerDriver(new OracleDriver());
        Connection conn = DriverManager.getConnection(thinConn);
        conn.setAutoCommit(false);

        try {
            Statement stmt = conn.createStatement();
            try {
                ResultSet rset = stmt.executeQuery("select BANNER from SYS.V_$VERSION");
                try {
                    while (rset.next())
                        System.out.println (rset.getString(1));   // Print col 1
                } finally {
                    try { rset.close(); } catch (Exception ignore) {}
                }
            } finally {
                try { stmt.close(); } catch (Exception ignore) {}
            }
        } finally {
            try { conn.close(); } catch (Exception ignore) {}
        }
    }
}


