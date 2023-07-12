package idv.chy;


import idv.chy.config.SpringDataJPAConfig;
import idv.chy.entity.Customer;
import idv.chy.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@ExtendWith(SpringExtension.class)
public class JpqlTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void testNamingConvention() {
        boolean customers = repository.existsByCustomerName("Hogan");
        System.out.println(customers);
    }


    @Test
    void testNamingConvention2() {
        int num = repository.deleteByCustomerId(5L);
        System.out.println(num);
    }
}
