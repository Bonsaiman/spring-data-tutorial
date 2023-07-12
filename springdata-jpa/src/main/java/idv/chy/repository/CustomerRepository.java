package idv.chy.repository;

import idv.chy.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
    @Query(" FROM Customer WHERE customerName = :name ")
    Customer findByCustomerName(@Param("name") String name);

    boolean existsByCustomerName(String customerName);

    @Transactional
    @Modifying
    int deleteByCustomerId(Long id);
}
