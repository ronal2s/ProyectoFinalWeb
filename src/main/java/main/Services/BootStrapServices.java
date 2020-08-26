package main.Services;

import org.h2.tools.Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BootStrapServices {
    private static BootStrapServices instancia;
    private String URL = "jdbc:h2:tcp://localhost/~/Parcial2Web";
    private Server tcp;


    private BootStrapServices() {

    }

    public static BootStrapServices getInstancia() {
        if (instancia == null) {
            instancia = new BootStrapServices();
        }
        return instancia;
    }

    private void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void testConn() {
        try {
            getConn().close();
            System.out.println("Conexión exitosa!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startDB() throws SQLException {

        tcp = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();
    }

    public void stopDB() {
        tcp.stop();
    }
}
