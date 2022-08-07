package lt.jurgitavis.graphiql.repositories;

import lt.jurgitavis.graphiql.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByName(String name);
}
