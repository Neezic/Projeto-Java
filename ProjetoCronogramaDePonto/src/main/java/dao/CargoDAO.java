package main.java.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;  // importa a classe ArrayList para armazenar listas de funcionários
import java.util.EnumMap;       // importa a interface List para criar listas dinâmicas
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.*;
import main.java.modelo.Cargo;

public class CargoDAO {
    private Map<String, Cargo> cargos = new TreeMap<>();
    private transient EnumMap<NivelCargo,List<Cargo>> cargosPorNivel = new EnumMap<>(NivelCargo.class);

    private static final String FILE_NAME = "cargos.dat";

    public enum NivelCargo{
        JUNIOR, PLENO, SENIOR, GERENCIAL
    }

    public CargoDAO(){
        carregarCargos();
    }
 @SuppressWarnings("unchecked") // Para o cast de ois.readObject()
    private synchronized void carregarCargos() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof Map) {
                    this.cargos = (Map<String, Cargo>) obj;
                    System.out.println("Cargos carregados de " + FILE_NAME);
                    // Recarregar o mapa derivado se ele for usado como cache persistente
                    // this.cargosPorNivel = agruparPorNivelInterno(); // Se desejar preencher ao carregar
                }
            } catch (FileNotFoundException e) {
                // Isso não deveria acontecer se file.exists() for verdadeiro, mas é bom ter.
                System.out.println("Arquivo " + FILE_NAME + " não encontrado. Iniciando com mapa de cargos vazio.");
                this.cargos = new TreeMap<>();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar cargos de " + FILE_NAME + ": " + e.getMessage());
                this.cargos = new TreeMap<>(); // Garante que 'cargos' não seja nulo
            }
        } else {
            System.out.println("Arquivo " + FILE_NAME + " não encontrado. Iniciando com mapa de cargos vazio.");
            this.cargos = new TreeMap<>();
        }
    }

    private synchronized void salvarTodosCargos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this.cargos);
            // System.out.println("Cargos salvos em " + FILE_NAME); // Opcional
        } catch (IOException e) {
            System.err.println("Erro ao salvar cargos em " + FILE_NAME + ": " + e.getMessage());
        }
    }

    public void salvar (Cargo cargo){
        cargos.put(cargo.getNome(),cargo);
        salvarTodosCargos();
    }

    public List<Cargo> listarTodos(){
        return new ArrayList<>(cargos.values());
    }

    public Cargo buscarPorNome(String nome){
        return cargos.get(nome);
    }
    private Map<NivelCargo, List<Cargo>> agruparPorNivelInterno(){
        return cargos.values().stream()
        .collect(Collectors.groupingBy(
            c -> {
                try {
                    return NivelCargo.valueOf(c.getNivel().toUpperCase());
                } catch (IllegalArgumentException | NullPointerException e ){
                    System.err.println("Cargo com nível invalido ou nulo: " + c.getNome()+ "- Nivel: " + c.getNivel());
                    return null;
                }
            
            },
            () -> new EnumMap<>(NivelCargo.class),
            Collectors.toList()
        ));
    }
    public Map<NivelCargo,List<Cargo>> agruparPorNivel(){
        return agruparPorNivelInterno();
    }
    public List<Cargo> filtrarPorSalarioMinimo(double salarioMinimo){
        return cargos.values().stream()
        .filter(c -> c.getsalarioBase() >= salarioMinimo)
        .collect(Collectors.toList());
        }
}
