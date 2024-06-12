package controle.estoque.stockmanagement.controller;

import controle.estoque.stockmanagement.model.Product;
import controle.estoque.stockmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        product.setCode(generateProductCode());
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setName(productDetails.getName());
            product.setQuantity(productDetails.getQuantity());
            product.setSupplier(productDetails.getSupplier());
            product.setSize(productDetails.getSize());
            product.setWeight(productDetails.getWeight());
            product.setColor(productDetails.getColor());
            product.setBrand(productDetails.getBrand());
            product.setCategory(productDetails.getCategory());
            return productService.saveProduct(product);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProducts(name);
    }

    private String generateProductCode() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        Random random = new Random();
        return "" + letters.charAt(random.nextInt(letters.length())) +
                    numbers.charAt(random.nextInt(numbers.length())) +
                    letters.charAt(random.nextInt(letters.length())) +
                    letters.charAt(random.nextInt(letters.length())) +
                    numbers.charAt(random.nextInt(numbers.length()));
    }
}
