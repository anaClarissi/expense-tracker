package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Category;
import model.Expense;
import utils.LocalDateAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class ExpenseRepositoryGson implements ExpenseRepository {

    private final String fileName;

    public ExpenseRepositoryGson(String fileName) {

        this.fileName = fileName;

        initFile(fileName);

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

        List<Expense> list = findAll();

        list.add(expense);

        saveAll(list);

        return expense;

    }

    @Override
    public Expense update(Long id, Expense expense) {

        try {

            List<Expense> list = findAll();

            Expense expenseOptional = findById(id, list);

            String description = expense.getDescription();

            Double amount = expense.getAmount();

            Category category = expense.getCategory();

            LocalDate date = expense.getDate();

            expenseOptional.setDescription(description);

            expenseOptional.setAmount(amount);

            expenseOptional.setCategory(category);

            expenseOptional.setDate(date);

            saveAll(list);

            return expenseOptional;


        } catch (RuntimeException e) {

            throw new RuntimeException("Error update Expense: ", e);

        }

    }

    @Override
    public void delete(Long id) {

        try {

            List<Expense> list = findAll();

            boolean removed = list.removeIf(obj -> obj.getId().equals(id));

            if (!removed) throw new RuntimeException("Expense not found");

            saveAll(list);


        } catch (RuntimeException e) {

            throw new RuntimeException("Error deleting by id: ", e);

        }

    }

    @Override
    public List<Expense> findAll() {

        try {

            String content = Files.readString(Path.of(fileName));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .setPrettyPrinting()
                    .create();

            Type listType = new TypeToken<List<Expense>>(){}.getType();

            List<Expense> list = gson.fromJson(content, listType);

            return list != null ? list : new ArrayList<>();

        } catch (IOException e) {

            throw new RuntimeException("Error listing file: ", e);

        }

    }

    @Override
    public Expense findById(Long id, List<Expense> list) {

        Optional<Expense> expense = list.stream().filter(obj -> obj.getId().equals(id)).findAny();

        return expense.orElseThrow(() -> new RuntimeException("Error finding Id"));

    }
}
