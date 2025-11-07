package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Wallet {

    //BALANCE
    public float Balance;
    public Currency Currency;
    public String Name;

    public List<Transaction> TransactionList = new ArrayList<>();

    public void AddTransactionToList(Transaction NewTransaction){
        TransactionList.add(TransactionList.toArray().length, NewTransaction);
        return;
    }
}