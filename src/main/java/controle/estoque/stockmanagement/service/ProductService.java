package controle.estoque.stockmanagement.service;

import controle.estoque.inventory.service.StockActionService;
import controle.estoque.inventory.service.StockActionService.ProductNotFoundException;
import controle.estoque.stockmanagement.model.Product;
import controle.estoque.stockmanagement.repository.ProductRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockActionService stockActionService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        if (product.getId() == null) {
            return stockActionService.createProduct(product);
        } else {
            return stockActionService.updateProduct(product.getId(), product);
        }
    }

    public class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    private Logger logger;

    @Autowired
    private StockActionService stockService;

    @Transactional
    public void deleteProduct(Long id) {
      Product product = productRepository.findById(id)
          .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    
      try {
        stockService.logStockAction(product, "DELETE", "Product deleted", product.getQuantity(), 0);
      } catch (Exception e) {
        // Log the exception for debugging purposes
        logger.error("Failed to log stock action for product deletion", e);
        throw e; // Propagate the original exception
      }
    
      productRepository.delete(product);
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
