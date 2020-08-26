package main;

import main.Controller.FormController;
import main.Controller.LoginController;
import main.Services.BootStrapServices;
import main.Services.FormularioServices;
import main.Services.UserServices;
import main.Entidades.Formulario;
import main.Entidades.Usuario;
import io.javalin.*;
import io.javalin.core.util.RouteOverviewPlugin;
import main.Soap.SoapController;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        BootStrapServices.getInstancia().startDB();
        BootStrapServices.getInstancia().testConn();

        Usuario user = new Usuario("admin", "admin", "admin");
        UserServices.getInstance().crear(user);

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.addStaticFiles("/public");
            javalinConfig.registerPlugin(new RouteOverviewPlugin("rutas"));
            javalinConfig.wsFactoryConfig(ws -> { ws.getPolicy().setMaxTextMessageSize(5000000); });
        });


        new SoapController(app).aplicarRutas();
        app.start(5000);
        new LoginController(app).getRoutes();
        new FormController(app).getRoutes();

    }
}
