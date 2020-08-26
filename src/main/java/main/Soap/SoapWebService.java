package main.Soap;

import main.Entidades.Formulario;
import main.Entidades.Usuario;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class SoapWebService {

    private FakeServices fakeServices = FakeServices.getInstancia();

    @WebMethod
    public Usuario getLogin(String usuario, String password){
        return fakeServices.crearUsuario(usuario,password);
    }

    @WebMethod
    public List<Formulario> getListaFormulario(){
        return fakeServices.getlistaFormulario();
    }

    @WebMethod
    public Formulario getFormulario(int id){
        return fakeServices.getFormulario(id);
    }

    @WebMethod
    public Formulario crearFormulario(Formulario formulario){
        return fakeServices.crearFormulario(formulario);
    }

    @WebMethod
    public Formulario actualizarFormulario(Formulario formulario){
        return fakeServices.actualizarFormulario(formulario);
    }
    @WebMethod
    public boolean borrarFormulario(int id){
        return fakeServices.eliminandoFormulario(id);
    }


}
