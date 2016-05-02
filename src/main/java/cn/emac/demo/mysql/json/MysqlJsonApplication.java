package cn.emac.demo.mysql.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class MysqlJsonApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    public static void main(String[] args) {
        SpringApplication.run(MysqlJsonApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        byJdbc();
        byRepository();
        byTemplate();
    }

    private void byTemplate() {
        customerService.findAll().forEach(System.out::println);
        Customer customer = newCustomer();
        long id = customerService.insert(customer);
        customerService.delete(id);
    }

    private Customer newCustomer() {
        Name name = new Name();
        name.setFirstName("emac");
        name.setLastName("shen");
        Customer customer = new Customer();
        customer.setName(name);
        return customer;
    }

    private void byRepository() {
        customerRepository.findAll().forEach(System.out::println);
        Customer customer = newCustomer();
        customer = customerRepository.save(customer);
        customerRepository.delete(customer);
    }

    private void byJdbc() throws Exception {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/test?useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true", "root", "root");
             Statement stmt = conn.createStatement()) {
            // 1. select
            String query = "select * from customer";
            ResultSet rs = conn.createStatement().executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            // Types.CHAR
            System.out.println("Type: " + rsmd.getColumnType(2));
            while (rs.next()) {
                System.out.println("Value: " + rs.getObject(2));
            }
            // 2. insert
            stmt.executeUpdate("insert into customer(name) values(\'{\"firstName\": \"emac\", \"lastName\": \"shen\"}\')", Statement.RETURN_GENERATED_KEYS);
            ResultSet rs2 = stmt.getGeneratedKeys();
            rs2.next();
            long id = rs2.getLong(1);
            // 3. delete
            stmt.executeUpdate("delete from customer where id=" + id);
        }
    }
}
