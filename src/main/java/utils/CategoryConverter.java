package utils;

import exceptions.InvalidCategoryException;
import model.Category;
import picocli.CommandLine;

public class CategoryConverter implements CommandLine.ITypeConverter<Category> {

    @Override
    public Category convert(String value) throws InvalidCategoryException {

        try {

            return Category.valueOf(value.toUpperCase());

        } catch (IllegalArgumentException e) {

            throw new InvalidCategoryException(value);

        }

    }

}
