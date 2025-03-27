package modelo;
import modelo.Funcionario;
import servico.PontoService;
import dao.FuncionarioDao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PontoService pontoService = new PontoService();
    private static FuncionarioDao funcionarioDAO = new FuncionarioDao();
    private static Funcionario funcionarioAtual = null;

    public static void main(String[] args) {
        // Cadastro inicial de funcionários (simulando um "banco de dados")
        cadastrarFuncionariosExemplo();
        
        // Menu principal
        while (true) {
            if (funcionarioAtual == null) {
                menuLogin();
            } else {
                menuPrincipal();
            }
        }
    }

    private static void cadastrarFuncionariosExemplo() {
        Funcionario func1 = new Funcionario("João Silva", "123.456.789-00", LocalDateTime.now());
        Funcionario func2 = new Funcionario("Maria Souza", "987.654.321-00", LocalDateTime.now());
        funcionarioDAO.salvar(func1);
        funcionarioDAO.salvar(func2);
    }

    private static void menuLogin() {
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
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } catch (IllegalStateException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private static void registrarSaida() {
        try {
            pontoService.registrarSaida(funcionarioAtual);
            System.out.println("\nSaída registrada com sucesso em: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
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
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
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

