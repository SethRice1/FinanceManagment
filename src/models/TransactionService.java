package models;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private final List<Transaction> transactions;

    public TransactionService() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}

