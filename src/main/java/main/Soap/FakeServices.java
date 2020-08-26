package main.Soap;

import main.Entidades.Formulario;
import main.Entidades.Usuario;
import main.Services.FormularioServices;

import java.util.List;

public class FakeServices {


    private static FakeServices instancia;


    public static FakeServices getInstancia(){
        if(instancia==null){
            instancia = new FakeServices();
        }
        return instancia;
    }

    public Usuario crearUsuario(String usuario, String password){
        return new Usuario(usuario, "Usuario "+usuario, password);
    }

    public List<Formulario> getlistaFormulario(){
        return FormularioServices.getInstance().findall();
    }

    public Formulario getFormulario(int id){
        return FormularioServices.getInstance().find(id);
    }

    public Formulario crearFormulario(Formulario formulario){
        FormularioServices.getInstance().crear(formulario);
        return formulario;
    }

    public Formulario actualizarFormulario(Formulario formulario){
        Formulario tmp = getFormulario(formulario.getId());
        if (FormularioServices.getInstance().editar(formulario)){
            return formulario;
        }
        return null;
    }

    public boolean eliminandoFormulario(int id){
        return FormularioServices.getInstance().eliminar(id);
    }
}
