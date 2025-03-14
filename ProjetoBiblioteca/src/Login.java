import java.util.ArrayList;

public class Login {
    private ArrayList<CadastroUsuario> usuarios;

    public void login(){
        usuarios = new ArrayList<>();
        usuarios.add(new CadastroUsuario("joaolucasdealmeida@lemail.com", "22122004"));
    }

    public boolean verificarLogin(String email,String senha){
        for (CadastroUsuario usuario : usuarios){
            if (CadastroUsuario.getEmail().equals(email) && CadastroUsuario.getSenha().equals(senha)){
                return true;
            }
        }
        return false;
    }
}
