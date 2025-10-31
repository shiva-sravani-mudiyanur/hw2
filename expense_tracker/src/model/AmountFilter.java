package model;

import java.util.List;
import java.util.stream.Collectors;

/*
 * To filter the list of transactions based on the comparator selected and amount entered.
 */
public class AmountFilter implements TransactionFilter {
    private final double amount;
    private String comparator;


    public AmountFilter(double amount, String comparator) {
        this.amount = amount;
        this.comparator = comparator;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        switch (this.comparator) {
            case "=":
                return transactions.stream()
                                   .filter(t -> t.getAmount() == this.amount)
                                   .collect(Collectors.toList());
                                
            case "<":
                return transactions.stream()
                                   .filter(t -> t.getAmount() < this.amount)
                                   .collect(Collectors.toList());

            case "<=":
                return transactions.stream()
                                   .filter(t -> t.getAmount() <= this.amount)
                                   .collect(Collectors.toList());

            case ">":
                return transactions.stream()
                                   .filter(t -> t.getAmount() > this.amount)
                                   .collect(Collectors.toList());
            
            case ">=":
                return transactions.stream()
                                   .filter(t -> t.getAmount() >= this.amount)
                                   .collect(Collectors.toList());
            
            default:
                return transactions;
        }
    }
}