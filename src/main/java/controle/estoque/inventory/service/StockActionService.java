package controle.estoque.inventory.service;

import controle.estoque.inventory.model.StockAction;
import controle.estoque.inventory.repository.StockActionRepository;
import controle.estoque.stockmanagement.controller.ProductController;
import controle.estoque.stockmanagement.model.Product;
import controle.estoque.stockmanagement.repository.ProductRepository;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockActionService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockActionRepository stockActionRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        logStockAction(newProduct, "CREATE", "Product created", 0, newProduct.getQuantity());
        return newProduct;
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow();
        int previousQuantity = product.getQuantity();
        product.setName(productDetails.getName());
        product.setQuantity(productDetails.getQuantity());
        product.setSupplier(productDetails.getSupplier());
        product.setSize(productDetails.getSize());
        product.setWeight(productDetails.getWeight());
        product.setColor(productDetails.getColor());
        product.setBrand(productDetails.getBrand());
        product.setCategory(productDetails.getCategory());
        Product updatedProduct = productRepository.save(product);
        logStockAction(updatedProduct, "UPDATE", "Product updated", previousQuantity, updatedProduct.getQuantity());
        return updatedProduct;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    @Transactional
    public void deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

            logStockAction(product, "DELETE", "Product deleted", product.getQuantity(), 0);

            productRepository.delete(product);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        } catch (Exception e) {
            logger.error("Failed to delete product with id: " + id, e);
            throw new RuntimeException("Failed to delete product due to internal server error");
        }
    }

    public void restoreProduct(Long id) {
        StockAction action = stockActionRepository.findById(id).orElseThrow();
        if (action.getActionType().equals("DELETE")) {
            Product product = action.getProduct();
            product.setQuantity(action.getPreviousQuantity());
            productRepository.save(product);
            logStockAction(product, "RESTORE", "Product restored", 0, product.getQuantity());
        }
    }

    public void logStockAction(Product product, String actionType, String actionDescription, int previousQuantity,
            int newQuantity) {
        StockAction stockAction = new StockAction();
        stockAction.setProduct(product);
        stockAction.setActionType(actionType);
        stockAction.setActionDescription(actionDescription);
        stockAction.setPreviousQuantity(previousQuantity);
        stockAction.setNewQuantity(newQuantity);
        stockActionRepository.save(stockAction);
    }
}
