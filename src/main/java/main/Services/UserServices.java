package main.Services;

import main.Entidades.Usuario;

public class UserServices extends GestionDB<Usuario> {
    private static UserServices instance;

    public UserServices() {
        super(Usuario.class);
    }

    public static UserServices getInstance() {
        if(instance==null){
            instance = new UserServices();
        }
        return instance;
    }
    public Usuario getUsuario(String username){
        return find(username);
    }
    public boolean verify_user(String username, String password){

        try {
            Usuario aux = find(username);
            if (aux.getUsuario().equals(username) && aux.getPassword().equals(password)){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

}
