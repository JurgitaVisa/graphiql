package lt.jurgitavis.graphiql.controlers;

import lt.jurgitavis.graphiql.model.Customer;
import lt.jurgitavis.graphiql.model.Purchase;
import lt.jurgitavis.graphiql.repositories.CustomerRepository;
import lt.jurgitavis.graphiql.repositories.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureGraphQlTester
class CustomerGraphqlControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    void shouldReturnCorrectNumberOfCustomers() {
        graphQlTester.documentName("customers")
                .execute()
                .path("customers")
                .entityList(Customer.class)
                .hasSize(8);
    }

    @Test
    void shouldReturnAllCustomersWithTheirNames() {
        graphQlTester.documentName("customers")
                .execute()
                .path("customers[*].name")
                .entityList(String.class)
                .containsExactly("Una", "Judita", "Jonas", "Dominykas", "Aušra", "Matas", "Mykolas", "Marius");
    }

    @Test
    void shouldFindCustomerByName(){
        int currentPurchaseCount = purchaseRepository.findByCustomerId(1).size();

        graphQlTester.documentName("customerByName")
                .variable("name", "Una")
                .execute()
                .path("customersByName[*].name")
                .entityList(String.class)
                .containsExactly("Una")
                .path("customersByName[0].purchases")
                .entityList(Purchase.class)
                .hasSize(currentPurchaseCount);
    }

    @Test
    void shouldAddNewCustomer() {
        int currentCustomerCount = customerRepository.findAll().size();

        graphQlTester.documentName("addCustomer")
                .variable("name", "Janina")
                .execute()
                .path("addCustomer")
                .entity(Customer.class)
                .satisfies(customer -> {
                    assertNotNull(customer.getId());
                    assertEquals("Janina", customer.getName());
                });

        assertEquals(currentCustomerCount + 1, customerRepository.findAll().size());
    }

    @Test
    void shouldAddNewPurchaseForCustomer(){
        int currentPurchaseCount = purchaseRepository.findByCustomerId(1).size();
        assertEquals(2, currentPurchaseCount);

        graphQlTester.documentName("createPurchase")
                .variable("customerId", 1)
                .execute()
                .path("addPurchase")
                .entity(Purchase.class)
                .satisfies(purchase->
                    assertEquals(1, purchase.getCustomerId())
                );

        assertEquals(currentPurchaseCount+1, purchaseRepository.findByCustomerId(1).size() );

    }






}