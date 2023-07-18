package idv.chy.v4;

import idv.chy.repository.CustomerRepository;
import idv.chy.v1.MyJpaRepository;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * FactoryBean 是有屬性的特殊 Bean (一般 @Bean 不會有屬性)
 */
public class MyJpaFactoryBean implements FactoryBean {

    @Autowired
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

    // 使用 constructor 賦值
    Class<?> repositoryInterface;


    public MyJpaFactoryBean(Class<?> repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    // 隨意控制 instance 過程
    @Override
    public Object getObject() throws ClassNotFoundException {
        // 取得 EntityManager
        EntityManager em = entityManagerFactoryBean.createNativeEntityManager(null);

        // 透過反射取得目前 Clazz
        // getGenericInterfaces() 取得 root interface = PagingAndSortingRepository
        ParameterizedType genericInterface =
                (ParameterizedType) repositoryInterface.getGenericInterfaces()[0];// 多個 interface 需要迭代
        // 能拿到 interface 的泛型  <Customer, Long>
        Type type = genericInterface.getActualTypeArguments()[0];
        Class<?> clazz = Class.forName(type.getTypeName());

        // 實現 repository 動態代理
        return Proxy.newProxyInstance(
                repositoryInterface.getClassLoader(),
                new Class[]{repositoryInterface},
                new MyJpaRepository(em, clazz)
        );
    }


    // 回傳 getObject() 的物件型態
    @Override
    public Class<?> getObjectType() {
        return CustomerRepository.class;
    }
}
