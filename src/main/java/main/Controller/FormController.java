package main.Controller;

import io.javalin.Javalin;
import main.Entidades.Formulario;
import main.Entidades.ModeloForm;
import main.Services.FormularioServices;
import org.eclipse.jetty.websocket.api.Session;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class FormController {

    Javalin app;
    public static List<Session> usuariosSesion = new ArrayList<>();
    public static List<ModeloForm> formularios = new ArrayList<>();

    public FormController(Javalin app) {
        this.app = app;
    }

    public void getRoutes(){

        app.before(ctx -> {
            for(ModeloForm formu : formularios){
                if(formu.getNombre() != null && formu.getSector() != null && formu.getNivelEscolar() != null){
                    Formulario formuTmp = new Formulario(formu.getNombre(), formu.getSector(), formu.getNivelEscolar(), formu.getLatitud(), formu.getLongitud(), formu.getMimeType(), formu.getFoto());
                    if(FormularioServices.getInstance().findByNombre(formuTmp.getNombre()).isEmpty()) {
                        FormularioServices.getInstance().crear(formuTmp);
                    }
                }
            }
        });

        app.get("/listadoAPI", ctx -> {
            List<Formulario> form = FormularioServices.getInstance().findall();
            Map<String, Object> model = new HashMap<>();
            model.put("formularios", form);
            ctx.json(model);
        });

        app.get("/nuevoItemAPI", ctx -> {
            System.out.println(ctx.queryParam("nombre"));
            System.out.println(ctx.queryParam("sector"));
            System.out.println(ctx.queryParam("nivelEscolar"));
            System.out.println(ctx.queryParam("longitud"));
            System.out.println(ctx.queryParam("latitud"));
            System.out.println("Listado");
            Formulario formulario = new Formulario(ctx.queryParam("nombre"), ctx.queryParam("sector"), ctx.queryParam("nivelEscolar"), Double.parseDouble(ctx.queryParam("latitud")), Double.parseDouble(ctx.queryParam("longitud")), "","");
            FormularioServices.getInstance().crear(formulario);
            Map<String, Object> model = new HashMap<>();
            model.put("error", false);
            ctx.json(model);
            // ctx.result("{error: false}");
        });

        app.routes(() -> {
            path("/formulario", () ->{
                path("/", () -> {
                    get(ctx -> {
                        List<String> nvlEscolar = Arrays.asList("", "Basico", "Medio", "Grado Universitario", "Postgrado", "Doctorado");
                        Map<String, Object> model = new HashMap<>();
                        model.put("title", "Formulario");
                        model.put("nvlEscolar", nvlEscolar);
                        ctx.render("/public/templates/formulario.ftl", model);
                    });

                    post(ctx -> {
                        String nomb = ctx.formParam("nombre");
                        String sector = ctx.formParam("sector");
                        String nivelEscolar = ctx.formParam("nivelEscolar");
                        ctx.redirect("/formulario");
                    });

                });
                path("/listado", () -> {
                    get(ctx -> {
                        List<Formulario> form = FormularioServices.getInstance().findall();
                        Map<String, Object> model = new HashMap<>();
                        model.put("title", "Listado");
                        model.put("formularios", form);
                        ctx.render("/public/templates/listado_formulario.ftl", model);
                    });

                });

                path("/listado/eliminar/:id", () -> {
                    get(ctx -> {
                        Formulario tmpForm = FormularioServices.getInstance().find(ctx.pathParam("id", Integer.class).get());
                        FormularioServices.getInstance().eliminar(tmpForm.getId());
                        ctx.redirect("/formulario/listado/");
                    });

                });

                path("/mapa", () -> {
                    get(ctx -> {
                        List<Formulario> forms = FormularioServices.getInstance().findall();
                        Map<String, Object> model = new HashMap<>();
                        model.put("title", "Formularios creados");
                        model.put("formularios", forms);
                        ctx.render("/public/templates/mapa.ftl", model);
                    });

                });

            });
        });

    }
}
