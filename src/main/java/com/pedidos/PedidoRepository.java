package com.pedidos;

import java.util.*;

/**
 * Repository: Gerencia o armazenamento de pedidos
 * 
 * RESPONSABILIDADE: Abstrair ONDE os dados são salvos
 * 
 * ============================================================
 * 💡 ARQUITETURA SERVERLESS - CAMADA DE DADOS
 * ============================================================
 * 
 * Esta classe é a camada de acesso a dados.
 * Atualmente salva em MEMÓRIA, mas poderia salvar em:
 * 
 * ☁️  NA NUVEM (Serverless):
 *     - AWS DynamoDB (NoSQL gerenciado)
 *     - AWS S3 (arquivos)
 *     - Azure Cosmos DB
 *     - Google Firestore
 * 
 * 💻 LOCAL:
 *     - Banco SQL (PostgreSQL, MySQL)
 *     - MongoDB
 *     - Arquivo JSON
 * 
 * A vantagem: trocar a implementação não afeta o resto do código!
 * ============================================================
 */
public class PedidoRepository {
    
    // Armazenamento em memória (simples para demonstração)
    private final Map<String, Pedido> storage = new HashMap<>();
    
    public PedidoRepository() {
        System.out.println("💾 Repository inicializado: armazenamento em MEMÓRIA");
        System.out.println("   (Em produção serverless: seria DynamoDB, Firestore, etc.)");
    }
    
    /**
     * Salva um pedido
     * 
     * ☁️ EM SERVERLESS NA NUVEM, seria algo como:
     *    dynamoDB.putItem(...)  // AWS
     *    firestore.collection("pedidos").add(...)  // Google
     */
    public Pedido salvar(Pedido pedido) {
        storage.put(pedido.getId(), pedido);
        System.out.println("   ✓ Pedido salvo: " + pedido.getId());
        return pedido;
    }
    
    /**
     * Lista todos os pedidos
     * 
     * ☁️ EM SERVERLESS NA NUVEM, seria algo como:
     *    dynamoDB.scan(...)  // AWS
     *    firestore.collection("pedidos").get()  // Google
     */
    public List<Pedido> listarTodos() {
        return new ArrayList<>(storage.values());
    }
    
    /**
     * Busca pedido por ID
     * 
     * ☁️ EM SERVERLESS NA NUVEM, seria algo como:
     *    dynamoDB.getItem(...)  // AWS
     *    firestore.collection("pedidos").doc(id).get()  // Google
     */
    public Optional<Pedido> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }
}
