package lt.jurgitavis.graphiql.repositories;

import lt.jurgitavis.graphiql.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query("SELECT p FROM Purchase p WHERE p.customerId=?1")
    List<Purchase> findByCustomerId(Integer customerId);

}
