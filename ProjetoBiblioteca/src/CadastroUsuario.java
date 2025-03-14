public class CadastroUsuario {
    private static String email;
    private static String senha;

    public CadastroUsuario(String email, String senha){
        this.email = email;
        this.senha = senha;
    }
    public static String getEmail(){
        return email;
    }
    public static String getSenha(){
        return senha;
    }

}
