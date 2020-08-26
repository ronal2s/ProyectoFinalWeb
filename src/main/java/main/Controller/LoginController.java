package main.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import main.Entidades.ModeloForm;
import main.Entidades.Usuario;
import main.Services.UserServices;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static j2html.TagCreator.p;

public class LoginController {

    Javalin app;
    public static List<Session> usuariosSesion = new ArrayList<>();
    public static List<ModeloForm> formularios = new ArrayList<>();
    UserServices usuarios = UserServices.getInstance();

    public LoginController(Javalin app) {
        this.app = app;
    }

    public void getRoutes(){
        app.get("/", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Inicio");
            ctx.redirect("/login");
        });
        app.get("/home", ctx -> {
            Usuario aux = ctx.sessionAttribute("usuario");
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Inicio");
            model.put("usuario", aux);
            ctx.render("/public/templates/home.ftl", model);
        });

        app.get("/login", ctx -> {
            try {
                if (UserServices.getInstance().verify_user(ctx.queryParam("user"), ctx.queryParam("password"))){
                    ctx.sessionAttribute("usuario", UserServices.getInstance().getUsuario(ctx.queryParam("user")));
                    ctx.redirect("/formulario");
                }else{
                    ctx.render("/public/templates/login/login.ftl");
                }
            }catch (Exception e){
                ctx.render("/public/templates/login/login.ftl");
            }


        });
        app.post("/login", ctx -> {
            if (UserServices.getInstance().verify_user(ctx.formParam("user"), ctx.formParam("password"))){
                ctx.sessionAttribute("usuario", UserServices.getInstance().getUsuario(ctx.formParam("user")));
                ctx.redirect("/formulario");
            }

        });
//updated
        app.get("/loginAPI", ctx -> {
            System.out.println(ctx.formParam("user"));
            System.out.println(ctx.queryParam("user"));
            System.out.println(ctx.queryParam("password"));
            Map<String, Object> model = new HashMap<>();
            if (UserServices.getInstance().verify_user(ctx.queryParam("user"), ctx.queryParam("password"))){
                model.put("error", false);
                ctx.json(model);
                ctx.status(200);
            } else {
                model.put("error", true);
                ctx.json(model);
                // ctx.status(200);
            }
        });


        app.get("/register", ctx -> {
            ctx.render("/public/templates/login/register.ftl");
        });
        app.post("/register", ctx -> {
                Usuario aux = new Usuario(ctx.formParam("user"),ctx.formParam("name"),ctx.formParam("password"));
                usuarios.crear(aux);
                ctx.render("/public/templates/login/register.ftl");
        });

        app.ws("/mensajeServidor", ws -> {

            ws.onConnect(ctx -> {
                usuariosSesion.add(ctx.session);
            });

            ws.onMessage(ctx -> {
                ctx.headerMap();
                ctx.pathParamMap();
                ctx.queryParamMap();
                boolean cond = true;
                ModeloForm tmpForm = jsonForms(ctx.message());
                for(ModeloForm form : formularios){
                    if(tmpForm.getId() == form.getId()){
                        cond = false;
                    }
                }
                if(cond){
                    formularios.add(tmpForm);
                }
            });

            ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje: "+ctx.data().length);
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada - "+ctx.getSessionId());
                usuariosSesion.remove(ctx.session);
            });

            ws.onError(ctx -> {
                System.out.println("ERROR en el WS");
            });
        });
    }

    public static ModeloForm jsonForms(String jsonString)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ModeloForm.class);
    }

}


