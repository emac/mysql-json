package cn.emac.demo.mysql.json;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Emac
 * @since 2016-05-02
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
