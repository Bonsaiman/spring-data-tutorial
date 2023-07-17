package idv.chy;

import idv.chy.config.SpringDataJPAConfig;
import idv.chy.repository.CustomerRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jca.support.LocalConnectionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public class MainStart {
    public static void main(String[] args) throws ClassNotFoundException {
        // spring  application context  spring container --> ioc 加載過程: 建立所有的 Bean, 包刮 Repository 的 Bean
        AnnotationConfigApplicationContext ioc =
                new AnnotationConfigApplicationContext(SpringDataJPAConfig.class);

        // 取得 EntityMaanger
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                ioc.getBean(LocalContainerEntityManagerFactoryBean.class);
        EntityManager em = entityManagerFactoryBean.createNativeEntityManager(null);


        // 透過反射取得目前 Clazz
        // getGenericInterfaces() 取得 root interface = PagingAndSortingRepository
        ParameterizedType genericInterface =
                (ParameterizedType) CustomerRepository.class.getGenericInterfaces()[0];// 多個 interface 需要迭代
        // 能拿到 interface 的泛型  <Customer, Long>
        Type type = genericInterface.getActualTypeArguments()[0];
        Class<?> clazz = Class.forName(type.getTypeName());

        // 實現 repository 動態代理
        CustomerRepository repository = (CustomerRepository) Proxy.newProxyInstance(
                CustomerRepository.class.getClassLoader(),
                new Class[]{CustomerRepository.class},
                new MyJpaRepository(em, clazz)
        );

        System.out.println(repository.findById(7L));
    }
}