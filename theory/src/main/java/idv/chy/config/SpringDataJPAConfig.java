package idv.chy.config;

import com.alibaba.druid.pool.DruidDataSource;
import idv.chy.repository.CustomerRepository;
import idv.chy.v1.MyJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

@Configuration // configuration --> xml
//@EnableJpaRepositories("idv.chy.repository") // 啟動 JPA
@EnableTransactionManagement // 開啟 Transaction
@ComponentScan("idv.chy")
public class SpringDataJPAConfig {


    @Bean
    public DataSource dataSource() {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("idv.chy.entity");
        factory.setDataSource(dataSource());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }


    /**
     * 寫死 CustomerRepository 作法
     */
//    @Bean
//    public CustomerRepository customerRepository(LocalContainerEntityManagerFactoryBean entityManagerFactory) throws ClassNotFoundException {
//        // 取得 EntityManager
//        EntityManager em = entityManagerFactory.createNativeEntityManager(null);
//
//        // 透過反射取得目前 Clazz
//        // getGenericInterfaces() 取得 root interface = PagingAndSortingRepository
//        ParameterizedType genericInterface =
//                (ParameterizedType) CustomerRepository.class.getGenericInterfaces()[0];// 多個 interface 需要迭代
//        // 能拿到 interface 的泛型  <Customer, Long>
//        Type type = genericInterface.getActualTypeArguments()[0];
//        Class<?> clazz = Class.forName(type.getTypeName());
//
//        // 實現 repository 動態代理
//        return (CustomerRepository) Proxy.newProxyInstance(
//                CustomerRepository.class.getClassLoader(),
//                new Class[]{CustomerRepository.class},
//                new MyJpaRepository(em, clazz)
//        );
//    }
}
