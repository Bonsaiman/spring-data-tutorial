import idv.chy.entity.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaTest {

    static EntityManagerFactory factory;

    @BeforeAll
    static void init(){
        // 對應persistence.xml 中 persistence-unit 名稱
        factory = Persistence.createEntityManagerFactory("hibernateJPA");
    }

    @Test
    void test_create(){
        EntityManager manager = factory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder().customerName("Suck my balls").build();

        manager.persist(customer);

        transaction.commit();
    }


    @Test
    void test_read_find(){
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = entityManager.find(Customer.class, 1L);

        System.out.println("====================================");
        System.out.println(customer);

        transaction.commit();
    }



    @Test
    void test_read_lazy(){
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = entityManager.getReference(Customer.class, 1L);

        System.out.println("====================================");
        System.out.println(customer);

        transaction.commit();
    }


    @Test
    void test_update(){
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

//        List list = entityManager.createQuery("FROM Customer").getResultList();

        Customer customer = Customer.builder().customerName("QOO").build();

        entityManager.merge(customer);

        /**
         * 如果指定Primary Key:
         *      先查詢看是否變化，如果有變化則更新，沒有變化則不更新
         * 如果沒有指定PK:
         *      插入(Insert)
         */

        transaction.commit();
    }

    @Test
    void test_update_JPQL(){
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

//        List list = entityManager.createQuery("FROM Customer").getResultList();

        Customer customer = Customer.builder().customerName("QOO").build();

        String jpql = "UPDATE Customer SET customerName = :name WHERE id = :id";
        entityManager.createQuery(jpql)
                .setParameter("name", "Hogan")
                .setParameter("id", 5L)
                .executeUpdate();

        transaction.commit();
    }


    @Test
    void test_update_SQL(){
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

//        List list = entityManager.createQuery("FROM Customer").getResultList();

        Customer customer = Customer.builder().customerName("QOO").build();

        String sql = "UPDATE t_customer SET name = :name WHERE id = :id";
        entityManager.createNativeQuery(sql)
                .setParameter("name", "Hogan")
                .setParameter("id", 5L)
                .executeUpdate();

        transaction.commit();
    }


    @Test
    void test_delete(){
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

//        List list = entityManager.createQuery("FROM Customer").getResultList();

        Customer customer = entityManager.find(Customer.class, 7L);

        entityManager.remove(customer);

        transaction.commit();
    }
}
