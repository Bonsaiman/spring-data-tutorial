package idv.chy;

import idv.chy.config.SpringDataJPAConfig;
import idv.chy.entity.Customer;
import idv.chy.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@ExtendWith(SpringExtension.class)
public class PaginateTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void testPaging() {
        Page<Customer> all = repository.findAll(PageRequest.of(0, 2));
        System.out.println("getTotalElements: " + all.getTotalElements());
        System.out.println("getTotalPages: " + all.getTotalPages());
        System.out.println("getNumber: " + all.getNumber());
        System.out.println("getSize: " + all.getSize());
    }


    @Test
    void testSort() {
        Sort orderById = Sort.by("customerId").descending();
        Sort orderByName = Sort.by("customerName").ascending();
        Iterable<Customer> all = repository.findAll(orderById);
        Iterable<Customer> all2 = repository.findAll(orderByName);
        System.out.println(all);
        System.out.println(all2);
    }

    @Test
    void testSortType() {
        Sort.TypedSort<Customer> customer = Sort.sort(Customer.class);
        Sort sort = customer.by(Customer::getCustomerId).ascending();
    }

}
