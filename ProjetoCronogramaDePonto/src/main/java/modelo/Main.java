package modelo;
import dao.FuncionarioDao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import servico.PontoService;

public class Main {

    // inicializando scanner e outros serviços para o sistema
    private static Scanner scanner = new Scanner(System.in);
    private static PontoService pontoService = new PontoService();
    private static FuncionarioDao funcionarioDAO = new FuncionarioDao();
    private static Funcionario funcionarioAtual = null; // variável para guardar o funcionário logado

    public static void main(String[] args) {
        // cadastrando funcionários iniciais de exemplo (simulando um "banco de dados")
        cadastrarFuncionariosExemplo();
        
        // loop principal que exibe o menu do sistema
        while (true) {
            if (funcionarioAtual == null) {
                menuLogin();  // exibe o menu de login se ninguém estiver logado
            } else {
                menuPrincipal();  // exibe o menu principal se o usuário estiver logado
            }
        }
    }

    // criando dois funcionários de exemplo
    private static void cadastrarFuncionariosExemplo() {
        Funcionario func1 = new Funcionario("João Silva", "123.456.789-00", LocalDateTime.now());
        Funcionario func2 = new Funcionario("Maria Souza", "987.654.321-00", LocalDateTime.now());
        funcionarioDAO.salvar(func1);
        funcionarioDAO.salvar(func2);
    }

    // menu que aparece quando o usuário não está logado
    private static void menuLogin() {
        System.out.println("\n=== SISTEMA DE PONTO ===");
        System.out.println("1. Login");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine(); // limpar buffer

        // controlando a escolha do usuário no menu
        switch (opcao) {
            case 1:
                fazerLogin();
                break;
            case 2:
                System.out.println("Saindo do sistema...");
                System.exit(0); // sai do sistema
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    // método que faz o login do funcionário
    private static void fazerLogin() {
        System.out.print("\nDigite seu CPF (somente números): ");
        String cpf = scanner.nextLine();
        
        // busca o funcionário pelo CPF
        funcionarioAtual = funcionarioDAO.buscarPorCPF(cpf);
        
        if (funcionarioAtual == null) {
            System.out.println("Funcionário não encontrado!");
        } else {
            System.out.println("\nBem-vindo, " + funcionarioAtual.getnome() + "!");
        }
    }

    // menu que aparece quando o usuário já está logado
    private static void menuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Registrar Entrada");
        System.out.println("2. Registrar Saída");
        System.out.println("3. Consultar Meus Registros");
        System.out.println("4. Logout");
        System.out.print("Escolha uma opção: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine(); // limpar buffer

        // controlando a escolha do usuário no menu
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

    // registra a entrada do funcionário
    private static void registrarEntrada() {
        try {
            pontoService.registrarEntrada(funcionarioAtual);
            System.out.println("\nEntrada registrada com sucesso em: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } catch (IllegalStateException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    // registra a saída do funcionário
    private static void registrarSaida() {
        try {
            pontoService.registrarSaida(funcionarioAtual);
            System.out.println("\nSaída registrada com sucesso em: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } catch (IllegalStateException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    // consulta os registros de entrada e saída do funcionário
    private static void consultarRegistros() {
        System.out.println("\n=== MEUS REGISTROS ===");
        var registros = pontoService.listarRegistros(funcionarioAtual);
        
        if (registros.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        // exibe os registros de entrada e saída do funcionário
        for (var registro : registros) {
            System.out.println("\nData: " + registro.gethoraEntrada().format(formatter));
            System.out.println("Entrada: " + registro.gethoraEntrada().toLocalTime());
            
            if (registro.gethoraSaida() != null) {
                System.out.println("Saída: " + registro.gethoraSaida().toLocalTime());
                System.out.println("Horas trabalhadas: " + registro.calcularHorasTrabalhadas() + " horas");
            } else {
                System.out.println("Saída: Ainda não registrada");
            }
        }
    }
}
