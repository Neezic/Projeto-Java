import main.java.modelo.Funcionario;
import main.java.servico.PontoService;
import main.java.dao.FuncionarioDao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static PontoService pontoService = new PontoService();
    private static FuncionarioDao funcionarioDAO = new FuncionarioDao();
    private static Funcionario funcionarioAtual = null;

    public static void main(String[] args) throws Exception {
        cadastrarFuncionariosExemplo();

        while(true){
            if (funcionarioAtual == null){
                menuLogin();
            } else{
                menuPrincipal();
            }
        }
        
    
       
        
        private static void cadastrarFuncionariosExemplo(){
            Funcionario func1 = new Funcionario("João Lucas", "123.456.789-99",LocalDate.now());
            Funcionario func2 = new Funcionario("Sanguinius", "888.288.181-88",LocalDate.now());
            funcionarioDAO.salvar(func1);
            funcionarioDAO.salvar(func2);
        }
        private static void menuLogin(){
            System.out.println("\n=== SISTEMA DE PONTO ===");
        System.out.println("1. Login");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        
        switch (opcao) {
            case 1:
                fazerLogin();
                break;
            case 2:
                System.out.println("Saindo do sistema...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida!");
        }
        }
    
    private static void fazerLogin() {
        System.out.print("\nDigite seu CPF (somente números): ");
        String cpf = scanner.nextLine();
        
        funcionarioAtual = funcionarioDAO.buscarPorCPF(cpf);
        
        if (funcionarioAtual == null) {
            System.out.println("Funcionário não encontrado!");
        } else {
            System.out.println("\nBem-vindo, " + funcionarioAtual.getnome() + "!");
        }
    }
    private static void menuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Registrar Entrada");
        System.out.println("2. Registrar Saída");
        System.out.println("3. Consultar Meus Registros");
        System.out.println("4. Logout");
        System.out.print("Escolha uma opção: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        
        switch (opcao) {
            case 1:
                registrarEntrada();
                break;
            case 2:
                registrarSaida();
                break;
            case 3:
                consultarRegistros();
                break;
            case 4:
                funcionarioAtual = null;
                System.out.println("Logout realizado com sucesso!");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
    private static void registrarEntrada() {
        try {
            pontoService.registrarEntrada(funcionarioAtual);
            System.out.println("\nEntrada registrada com sucesso em: " + 
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } catch (IllegalStateException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }
    private static void registrarSaida() {
        try {
            pontoService.registrarSaida(funcionarioAtual);
            System.out.println("\nSaída registrada com sucesso em: " + 
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } catch (IllegalStateException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }
    private static void consultarRegistros() {
        System.out.println("\n=== MEUS REGISTROS ===");
        var registros = pontoService.listarRegistros(funcionarioAtual);
        
        if (registros.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }
    }
}

