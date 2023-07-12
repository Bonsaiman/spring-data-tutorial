import idv.chy.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.persistence.Table;
import java.util.List;

public class HibernateTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();

        // 根據服務註冊創建metadata, 生成session工廠
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Test
    void test_create() {
        // 透過session進行持久化操作
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Customer customer = Customer.builder().customerName("KKK").build();
            session.save(customer);

            transaction.commit();
        }

    }

    @Test
    void test_read_find() {
        // 透過session進行持久化操作
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Customer customer = session.find(Customer.class, 1L);
            System.out.println("=========================================");
            System.out.println(customer);

            transaction.commit();
        }

    }

    @Test
    void test_read_load() {
        // 透過session進行持久化操作
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // lazy load
            Customer customer = session.load(Customer.class, 3L);
            System.out.println("=========================================");
            System.out.println(customer);

            transaction.commit();
        }

    }

    @Test
    void test_update() {
        // 透過session進行持久化操作
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Customer customer = Customer.builder()
                    .customerName("Big Dick")
                    .build();

            session.saveOrUpdate(customer);

            transaction.commit();
        }
    }


    @Test
    void test_delete() {
        // 透過session進行持久化操作
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Customer customer = Customer.builder()
                    .customerId(2L)
                    .build();

            session.remove(customer);

            transaction.commit();
        }
    }


    @Test
    void test_hql() {
        // 透過session進行持久化操作
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM Customer";
            String hql2 = "FROM Customer WHERE id = :id";

            List<Customer> resultList = session.createQuery(hql, Customer.class).getResultList();

            List<Customer> resultList2 = session.createQuery(hql2, Customer.class).setParameter("id", 3L).getResultList();

            System.out.println(resultList);
            System.out.println(resultList2);

            transaction.commit();
        }
    }
}
