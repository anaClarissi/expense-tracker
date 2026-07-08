package utils;

import model.Category;
import picocli.CommandLine;

public class CategoryConverter implements CommandLine.ITypeConverter<Category> {

    @Override
    public Category convert(String value) throws Exception {

        return Category.valueOf(value.toUpperCase());

    }

}
