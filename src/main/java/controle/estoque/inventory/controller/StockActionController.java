package controle.estoque.inventory.controller;

import controle.estoque.inventory.model.StockAction;
import controle.estoque.inventory.repository.StockActionRepository;
import controle.estoque.stockmanagement.model.Product;
import controle.estoque.stockmanagement.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StockActionController {

    @Autowired
    private StockActionRepository stockActionRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/stock-actions")
    public List<StockAction> getAllStockActions() {
        return stockActionRepository.findAll();
    }

    @PostMapping("/restore/{id}")
    public void restoreProduct(@PathVariable Long id) {
        StockAction action = stockActionRepository.findById(id).orElseThrow();
        if (action.getActionType().equals("DELETE")) {
            Product product = action.getProduct();
            product.setQuantity(action.getPreviousQuantity());
            productRepository.save(product);
            logStockAction(product, "RESTORE", "Product restored", 0, product.getQuantity());
        }
    }

    private void logStockAction(Product product, String actionType, String actionDescription, int previousQuantity, int newQuantity) {
        StockAction stockAction = new StockAction();
        stockAction.setProduct(product);
        stockAction.setActionType(actionType);
        stockAction.setActionDescription(actionDescription);
        stockAction.setPreviousQuantity(previousQuantity);
        stockAction.setNewQuantity(newQuantity);
        stockActionRepository.save(stockAction);
    }
}
