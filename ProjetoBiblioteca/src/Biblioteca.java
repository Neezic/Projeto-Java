import java.util.*;



public class Biblioteca {
    public final static void clearConsole(){

        try{
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")){
                Runtime.getRuntime().exec("cls");

            }else{
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e){
        //  Tratar Exceptions
        }
    }
    public static void main(String[] args) throws Exception {
        
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        System.out.println("Bem vindo a Biblioteca do povo");
        System.out.println("\n Digite seu email: ");
        String email = scanner.nextLine();
        System.out.println("\n Digite a sua senha: ");
        String senha = scanner.nextLine();
        if (login.verificarLogin(email, senha)){
            clearConsole();
            int opcao = 0;
            while(opcao != 5){
                    System.out.println("[1] Emprestar \n");
                    System.out.println("[2] Perfil \n");
                    System.out.println("[3] Postar Livro \n");
                    System.out.println("[4] Pagar Multa \n");
                    System.out.println("[5] Devolver Livro");
                    System.out.println("[6] Sair \n");
                    switch(opcao){
                        case 1: 
                        break;
                        case 2:
                        System.out.println();
                        break;
                    }
                    
                }
        }
        
        
        
        
        
        
        
        
        
       





    }
}
