package idv.chy;

import idv.chy.config.SpringDataJPAConfig;
import idv.chy.entity.Account;
import idv.chy.entity.Customer;
import idv.chy.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ContextConfiguration(classes = SpringDataJPAConfig.class)
@ExtendWith(SpringExtension.class)
class OneToOneTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void test_create() {
        Account account = Account.builder().userName("Ya-Ru").build();

        Customer customer = Customer.builder()
                .customerName("Ru")
                .account(account).build();

        account.setCustomer(customer);

        repository.save(customer);

    }

    @Test
    // 懶加載必須配置事務:
    // 透過 repository 調用完查詢方法, session就會立即關閉, 關閉後就不能再查詢或進行操作
    // 加了 Transactional 後, 能讓session直到事務方法執行完畢後才關閉
    @Transactional(readOnly = true)
    void test_read() {
        Optional<Customer> customer = repository.findById(9L);
        System.out.println("======================================");
        System.out.println(customer.get());
    }



    @Test
    void test_delete() {
        repository.deleteById(1L);
    }


    @Test
    void test_update() {
        Customer customer = Customer.builder()
                .customerId(13L)
                .customerName("Riley")
                .customerAddress("Hockley")
                .account(null)
                .build();

        repository.save(customer);
    }
}