package com.pedidos;

import java.util.List;

/**
 * Service: Contém a lógica de negócio
 * 
 * RESPONSABILIDADE: Validar dados e aplicar regras de negócio
 * 
 * ============================================================
 * 💡 ARQUITETURA SERVERLESS - CAMADA DE NEGÓCIO
 * ============================================================
 * 
 * Esta camada é INDEPENDENTE de onde o código roda:
 * - Roda igual localmente (como agora)
 * - Roda igual na nuvem (AWS Lambda, Azure Functions, etc.)
 * 
 * NÃO conhece detalhes de:
 * - HTTP (isso é responsabilidade do Handler)
 * - Banco de dados (isso é responsabilidade do Repository)
 * 
 * Apenas foca em: validações e regras de negócio
 * ============================================================
 */
public class PedidoService {
    
    private final PedidoRepository repository;
    
    public PedidoService() {
        this.repository = new PedidoRepository();
        System.out.println("🔧 Service inicializado");
    }
    
    /**
     * Cria um novo pedido
     * 
     * Este método funciona IGUAL tanto localmente quanto na nuvem!
     */
    public Pedido criarPedido(String cliente, String produto, int quantidade) {
        System.out.println("\n📦 Processando novo pedido:");
        System.out.println("   Cliente: " + cliente);
        System.out.println("   Produto: " + produto);
        System.out.println("   Quantidade: " + quantidade);
        
        // 1. VALIDAÇÕES (regras de negócio)
        validarDados(cliente, produto, quantidade);
        
        // 2. CRIAR ENTIDADE
        Pedido pedido = new Pedido(cliente, produto, quantidade);
        System.out.println("   ✓ Pedido criado: " + pedido.getId());
        
        // 3. PERSISTIR (delega ao Repository)
        return repository.salvar(pedido);
    }
    
    /**
     * Lista todos os pedidos
     */
    public List<Pedido> listarPedidos() {
        System.out.println("\n📋 Listando todos os pedidos");
        List<Pedido> pedidos = repository.listarTodos();
        System.out.println("   ✓ Total encontrado: " + pedidos.size());
        return pedidos;
    }
    
    /**
     * Valida os dados do pedido (regras de negócio)
     */
    private void validarDados(String cliente, String produto, int quantidade) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Cliente é obrigatório");
        }
        
        if (produto == null || produto.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Produto é obrigatório");
        }
        
        if (quantidade <= 0) {
            throw new IllegalArgumentException("❌ Quantidade deve ser maior que zero");
        }
        
        if (quantidade > 100) {
            throw new IllegalArgumentException("❌ Quantidade máxima é 100 unidades");
        }
        
        System.out.println("   ✓ Validações OK");
    }
}
