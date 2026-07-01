package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Category;
import model.Expense;
import utils.LocalDateAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class ExpenseRepositoryGson implements ExpenseRepository {

    private final String fileName;

    public ExpenseRepositoryGson(String fileName) {

        this.fileName = fileName;

    }

    private void initFile (String fileName) {

        File file = new File(fileName);

        if (!file.exists()) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

                writer.write("[]");

            } catch (IOException e) {

                throw new RuntimeException("Error creating file: ", e);

            }

        }

    }

    public void saveAll(List<Expense> list) {

        initFile(fileName);

        StringBuilder expenseString = new StringBuilder();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();

        expenseString.append("[\n");

        for (int i = 0; i < list.size(); i++) {

            expenseString.append(gson.toJson(list.get(i)));

            if (i < list.size() - 1) {

                expenseString.append(",");

            }

            expenseString.append("\n");

        }

        expenseString.append("]");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write(expenseString.toString());

        } catch (IOException e) {

            throw new RuntimeException("Error saving file: ", e);

        }


    }

    @Override
    public Expense add(Expense expense) {

        List<Expense> list = new ArrayList<>();

        list.add(expense);

        saveAll(list);

        return expense;

    }

    @Override
    public Optional<Expense> update(Long id, Expense expense) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Expense> findAll() {

        initFile(fileName);

        try {

            String content = Files.readString(Path.of(fileName));

            content = content.replace("\n", "").replace("\r", "").trim();

            if (content.equals("[]")) {

                return new ArrayList<>();

            }

            List<Expense> list = new ArrayList<>();

            content = content.substring(1, content.length() - 1);

            String[] expenseArray = content.split("},\\s*\\{");

            String[] fields;

            String[] keyValue;

            for (String expenseString : expenseArray) {

                Map<String, String> attributes = new HashMap<>();

                expenseString = expenseString.replace("{", "").replace("}", "").trim();

                fields = expenseString.split(",(?=\\s*\"\\w+\":)");

                for (String field : fields) {

                    keyValue = field.split(":", 2);

                    String key = keyValue[0].replace("\"", "").trim();

                    String value = keyValue[1].replace("\"", "").trim();

                    attributes.put(key, value);

                }

                Expense expense = new Expense(
                        Long.parseLong(attributes.get("id")),
                        attributes.get("description"),
                        Double.valueOf(attributes.get("amount")),
                        Category.valueOf(attributes.get("category").toUpperCase()),
                        LocalDate.parse(attributes.get("date"))

                );

                list.add(expense);

            }

            return list;

        } catch (IOException e) {

            throw new RuntimeException("Error listing file: ", e);

        }

    }

    @Override
    public Expense findById(Long id) {

        Expense expense = findAll().stream().filter(obj -> obj.getId().equals(id)).findAny().orElseThrow(() -> new RuntimeException("Id Inválid"));

        return expense;

    }
}
