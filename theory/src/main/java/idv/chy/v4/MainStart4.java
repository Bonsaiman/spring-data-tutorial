package idv.chy.v4;

import idv.chy.config.SpringDataJPAConfig;
import idv.chy.entity.Customer;
import idv.chy.repository.CustomerRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class MainStart4 {

    public static void main(String[] args) {
        // spring  application context  spring container --> ioc 加載過程: 建立所有的 Bean, 包刮 Repository 的 Bean
        AnnotationConfigApplicationContext ioc =
                new AnnotationConfigApplicationContext(SpringDataJPAConfig.class);

        CustomerRepository repository = ioc.getBean(CustomerRepository.class);
        Optional<Customer> byId = repository.findById(7L);
        System.out.println(byId.get());
    }
}
