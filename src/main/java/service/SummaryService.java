package service;

import model.Expense;
import repository.ExpenseRepository;
import repository.ExpenseRepositoryGson;

import java.time.Month;
import java.util.List;

public class SummaryService {

    private final ExpenseRepository repository;

    public SummaryService(String fileName) {

        this.repository = new ExpenseRepositoryGson(fileName);

    }

    public Double findAll() {

        List<Double> list = repository.findAll().stream().map(Expense::getAmount).toList();

        return list.stream().mapToDouble(Double::doubleValue).sum();

    }

    public Double findByMonth(int monthNumber) {

        Month month = Month.of(monthNumber);

        List<Double> list = repository.findAll().stream()
                .filter(expense -> expense.getDate().getMonth().equals(month))
                .map(Expense::getAmount).toList();

        return list.stream().mapToDouble(Double::doubleValue).sum();

    }

}
