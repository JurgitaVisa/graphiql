package lt.jurgitavis.graphiql.controlers;

import lt.jurgitavis.graphiql.domain.Customer;
import lt.jurgitavis.graphiql.domain.Purchase;
import lt.jurgitavis.graphiql.repositories.CustomerRepository;
import lt.jurgitavis.graphiql.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerGraphqlController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @QueryMapping(name = "customers")//name is optional...
    public List<Customer> customers() { //...cause this name matches schema
        return customerRepository.findAll();
    }
//    Another way for achieving the same:
//    @SchemaMapping(typeName = "Query", field = "customers")
//    public List<Customer> customers(){
//        return customerRepository.findAll();
//    }

    @QueryMapping
    public List<Customer> customersByName(@Argument String name){
        return customerRepository.findByName(name);
    }

    @SchemaMapping(typeName = "Customer")
    public List<Purchase> purchases(Customer customer){
        return purchaseRepository.findByCustomerId(customer.getId());
    }

    @MutationMapping
    public Customer addCustomer(@Argument String name){
        return customerRepository.save(new Customer(name));
    }

    @MutationMapping
    public Purchase addPurchase(@Argument Integer customerId){
        return purchaseRepository.save(new Purchase(customerId));
    }

}
