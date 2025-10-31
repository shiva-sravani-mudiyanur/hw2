package model;

import java.util.List;
/*
 * To filter a list of transctions based on specific criteria.
 * 
 */
public interface TransactionFilter {
        List<Transaction> filter(List<Transaction> transactions);
}
