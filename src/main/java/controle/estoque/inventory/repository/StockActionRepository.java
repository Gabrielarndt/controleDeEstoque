package controle.estoque.inventory.repository;

import controle.estoque.inventory.model.StockAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockActionRepository extends JpaRepository<StockAction, Long> {
}
