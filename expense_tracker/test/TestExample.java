package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

import static org.junit.Assert.*;


public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }
    


    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(50.00, "food"));
    
        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
        assertEquals(50.00, getTotalCost(), 0.01);
    }

    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
        Transaction addedTransaction = new Transaction(50.00, "Groceries");
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    @Test
    public void testAddValidTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(200.00, "travel"));

        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());

        // Check the contents of the list
        assertEquals(200.00, getTotalCost(), 0.01);

        // Check the cateogry of added transaction
        assertEquals("travel", model.getTransactions().get(0).getCategory());
    }

    @Test
    public void testAmountInputValidation() {
        // Pre-condition: List of transactions is empty
        double totalCostBeforeAddingTransaction = getTotalCost();
        assertEquals(0, model.getTransactions().size());

        // Adding a transaction with negative amount
        assertFalse(controller.addTransaction(-200.00, "travel"));
        double totalCostAfterAddingTransaction = getTotalCost();

        assertEquals(totalCostAfterAddingTransaction, totalCostBeforeAddingTransaction, 0.01);
    }
    
    @Test
    public void testCategoryInputValidation() {
        //Adding a transaction with invalid category
        assertFalse(controller.addTransaction(159.00, "pharmacy"));
    }
    
    @Test
    public void testAmountFilter() {
    	
    	//Pre-defined transactions for filter validation
        Transaction t1 = new Transaction(2.00, "food");
        Transaction t2 = new Transaction(4.00, "entertainment");
        Transaction t3 = new Transaction(6.00, "other");
        Transaction t4 = new Transaction(8.00, "bills");
        
        // expected filtered transactions list
        List<Transaction> expectedFilteredTransactions = new ArrayList<>(List.of(new Transaction[]{t1, t2, t3, t4}));

        //Adding actual transactions
        controller.addTransaction(2.00, "food");
        controller.addTransaction(4.00, "entertainment");
        controller.addTransaction(6.00, "other");
        controller.addTransaction(8.00, "bills");
        controller.addTransaction(10.00, "food");
        controller.addTransaction(12.00, "food");
        controller.addTransaction(14.00, "entertainment");

        //Selecting amount filter
        controller.filter(10, "<");

        //Asserting
        assertEquals(model.getFilteredTransactions().size(), expectedFilteredTransactions.size());

        for(int i=0; i<expectedFilteredTransactions.size(); i++) {
            assertEquals(model.getFilteredTransactions().get(i).getAmount(), expectedFilteredTransactions.get(i).getAmount(), 0.01);
            assertEquals(model.getFilteredTransactions().get(i).getCategory(), expectedFilteredTransactions.get(i).getCategory());
        }
    }

    @Test
    public void testCategoryFilter() {
    	
    	//Pre-defined transactions for filter validation
        Transaction t1 = new Transaction(2.00, "food");
        Transaction t2 = new Transaction(10.00, "food");
        Transaction t3 = new Transaction(12.00, "food");
        
        // expected filtered transactions list
        List<Transaction> expectedFilteredTransactions = new ArrayList<>(List.of(new Transaction[]{t1, t2, t3}));

        //Adding actual transactions
        controller.addTransaction(2.00, "food");
        controller.addTransaction(4.00, "entertainment");
        controller.addTransaction(6.00, "other");
        controller.addTransaction(8.00, "bills");
        controller.addTransaction(10.00, "food");
        controller.addTransaction(12.00, "food");
        controller.addTransaction(14.00, "entertainment");


        //Selecting category filter
        controller.filter("food");

        //Asserting 
        assertEquals(model.getFilteredTransactions().size(), expectedFilteredTransactions.size());

        for(int i=0; i<expectedFilteredTransactions.size(); i++) {
            assertEquals(model.getFilteredTransactions().get(i).getAmount(), expectedFilteredTransactions.get(i).getAmount(), 0.01);
            assertEquals(model.getFilteredTransactions().get(i).getCategory(), expectedFilteredTransactions.get(i).getCategory());
        }
    }
    
}