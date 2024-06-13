package controle.estoque.inventory.model;

import controle.estoque.stockmanagement.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_actions")
public class StockAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String actionType;
    private String actionDescription;
    private LocalDateTime actionTime;
    private int previousQuantity;
    private int newQuantity;

    @PrePersist
    protected void onCreate() {
        actionTime = LocalDateTime.now();
    }


	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	};

	public String getActionType() {
		return this.actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActionDescription() {
		return this.actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	public LocalDateTime getActionTime() {
		return this.actionTime;
	}

	public void setActionTime(LocalDateTime actionTime) {
		this.actionTime = actionTime;
	}

	public int getPreviousQuantity() {
		return this.previousQuantity;
	}

	public void setPreviousQuantity(int previousQuantity) {
		this.previousQuantity = previousQuantity;
	}

	public int getNewQuantity() {
		return this.newQuantity;
	}

	public void setNewQuantity(int newQuantity) {
		this.newQuantity = newQuantity;
	}


   
}
