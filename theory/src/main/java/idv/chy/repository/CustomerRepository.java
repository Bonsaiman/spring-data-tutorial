package idv.chy.repository;

import idv.chy.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
