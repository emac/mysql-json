package cn.emac.demo.mysql.json;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Emac
 * @since 2016-05-02
 */
@Entity
@Data
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Convert(converter = NameConvertor.class)
    private Name name;
}
