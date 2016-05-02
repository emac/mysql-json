package cn.emac.demo.mysql.json;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * @author Emac
 * @since 2016-05-02
 */
@Service
public class CustomerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Gson gson;

    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customer", (resultSet, i) -> {
            Customer customer = new Customer();
            customer.setId(resultSet.getLong(1));
            customer.setName(gson.fromJson(resultSet.getString(2), Name.class));
            return customer;
        });
    }

    public long insert(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into customer(name) values(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, gson.toJson(customer));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int delete(long id) {
        return jdbcTemplate.update("delete from customer where id=" + id);
    }
}
