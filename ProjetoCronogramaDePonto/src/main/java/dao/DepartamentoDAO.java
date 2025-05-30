package main.java.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import main.java.modelo.Departamento;
import main.java.modelo.Funcionario;

public class DepartamentoDAO {
    private Map<String,Departamento> departamentos = new ConcurrentHashMap<>();

    private static final String FILE_NAME = "departamento.dat";

    public DepartamentoDAO(){
        carregarDepartamento();
    }

     @SuppressWarnings("unchecked") // Para o cast de ois.readObject()
    private synchronized void carregarDepartamento() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof Map) {
                    // ConcurrentHashMap é serializável, então isso deve funcionar.
                    this.departamentos = (Map<String, Departamento>) obj;
                    // Se o objeto lido não for um ConcurrentHashMap, mas um Map genérico (ex: HashMap),
                    // e você precisar especificamente de ConcurrentHashMap, você precisaria reconstruí-lo:
                    // Map<String, Departamento> tempMap = (Map<String, Departamento>) obj;
                    // this.departamentos = new ConcurrentHashMap<>(tempMap);
                    System.out.println("Departamentos carregados de " + FILE_NAME);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Arquivo " + FILE_NAME + " não encontrado. Iniciando com mapa de departamentos vazio.");
                this.departamentos = new ConcurrentHashMap<>();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar departamentos de " + FILE_NAME + ": " + e.getMessage());
                this.departamentos = new ConcurrentHashMap<>(); // Garante que não seja nulo
            }
        } else {
            System.out.println("Arquivo " + FILE_NAME + " não encontrado. Iniciando com mapa de departamentos vazio.");
            this.departamentos = new ConcurrentHashMap<>();
        }
    }

    private synchronized void salvarTodosDepartamentos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this.departamentos);
            // System.out.println("Departamentos salvos em " + FILE_NAME); // Opcional
        } catch (IOException e) {
            System.err.println("Erro ao salvar departamentos em " + FILE_NAME + ": " + e.getMessage());
        }
    }

    public void salvar(Departamento departamento){
        if (departamento == null || departamento.getNome() == null) {
            throw new IllegalArgumentException("Departamento inválido");
        }
        
        departamentos.put(departamento.getNome(), departamento);
        salvarTodosDepartamentos();
    }
    public Map<Departamento, Set<Funcionario>> getDeparmtentocomFuncionarios(){
        return departamentos.values().stream()
        .collect(Collectors.toMap(
            d -> d, 
            Departamento::getFuncionarios
            ));

    }
    public List<Departamento>buscarPorPrefixo(String prefixo){
        return departamentos.entrySet().stream()
        .filter(e -> e.getKey().startsWith(prefixo))
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());
    }
   
    public Map<Departamento, Double> mediaSalarialPorDepartamento() {
    return departamentos.values().stream()
        .collect(Collectors.groupingBy(
            d -> d,
            Collectors.averagingDouble(d -> 
                d.getFuncionarios().stream()
                    .mapToDouble(f -> {
                        if (f.getCargo() != null) {
                            return f.getCargo().getsalarioBase();
                        }
                        return 0.0;
                    })
                    .average()
                    .orElse(0.0) // Usando 0.0 (double) em vez de 0 (int)
            )
        ));
}
}
