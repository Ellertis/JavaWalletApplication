package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Wallet implements WalletActions {

    //BALANCE
    public float Balance;
    public Currency Currency;
    public String Name;

    public List<Transaction> TransactionList = new ArrayList<>();

    @Override
    public void Deposit(float amount){
        Balance += amount;
    }

    @Override
    public void Withdraw(float amount){
        Balance -= amount;
    }
}