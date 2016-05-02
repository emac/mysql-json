package cn.emac.demo.mysql.json;

import com.google.gson.Gson;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Emac
 * @since 2016-05-02
 */
@Converter
public class NameConvertor implements AttributeConverter<Name, String> {

    private Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(Name name) {
        return gson.toJson(name);
    }

    @Override
    public Name convertToEntityAttribute(String value) {
        return gson.fromJson(value, Name.class);
    }
}
