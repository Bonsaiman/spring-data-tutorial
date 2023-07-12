package idv.chy;

import idv.chy.config.SpringDataJPAConfig;
import idv.chy.entity.Account;
import idv.chy.entity.Customer;
import idv.chy.entity.Message;
import idv.chy.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ContextConfiguration(classes = SpringDataJPAConfig.class)
@ExtendWith(SpringExtension.class)
class OneToManyTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void test_create() {

        List<Message> messageList = new ArrayList<>();
        messageList.add(Message.builder().info("Mother fucker!!!").build());
        messageList.add(Message.builder().info("You son of a bitch!!!").build());

        Customer customer = Customer.builder()
                .customerName("Hogan")
                .messages(messageList)
                .build();

        repository.save(customer);

    }


    @Test
    @Transactional(readOnly = true)
    void test_read() {
        // 懶加載過程
        // 1. findById 只會查詢Customer 和設定Eager的關聯立即加載
        Optional<Customer> customer = repository.findById(1L);
        System.out.println("======================================");
        // 2. 自動調用Customer.toString()方法
        System.out.println(customer);

    }


    @Test
    void test_delete() {
        repository.deleteById(2L);

    }
}