package idv.chy;

import idv.chy.config.SpringDataJPAConfig;
import idv.chy.entity.Customer;
import idv.chy.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ContextConfiguration(classes = SpringDataJPAConfig.class)
//@ContextConfiguration(locations = "/spring.xml")
@ExtendWith(SpringExtension.class)
class SpringDataJpaTest {


    @Autowired
    CustomerRepository customerRepository;

    @Test
    void test_create_or_update() {
        Customer customer = Customer.builder().customerId(3L).customerName("Ru").build();
        customerRepository.save(customer);
    }

    @Test
    void test_read() {
        Optional<Customer> byId = customerRepository.findById(1L);
        Iterable<Customer> all = customerRepository.findAll();
        System.out.println(byId.get());
        System.out.println(all);
    }

    @Test
    void test_delete(){

        Customer customer = Customer.builder().customerName("Ru").customerId(3L).build();
        customerRepository.delete(customer);
    }
}