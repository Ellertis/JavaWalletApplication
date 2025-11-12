package org.example;

import java.time.LocalDate;

public class TransactionManager {
    static WalletManager walletManager = new WalletManager();
    static TransactionProcessor transactionProcessor = new TransactionProcessor();

    public static Transaction CreateNewTransaction(TransactionStatus NewTransactionStatus, String[] SenderReceiver, float Amount, Currency NewTransactionCurrency, LocalDate Date, boolean bPrintDetails){
        Transaction NewTransaction = new Transaction();

        NewTransaction.setStatus(NewTransactionStatus);

        if (SenderReceiver.length != 2){
            System.out.println("SenderReceiver String Array length is incorrect");
        } else{
            NewTransaction.setSenderReceiver(SenderReceiver);
        }

        if (Amount <= 0){
            System.out.println("Invalid amount, 0 or less");
        } else{
            NewTransaction.setAmount(Amount);
        }

        NewTransaction.setCurrency(NewTransactionCurrency);

        NewTransaction.setDate(Date);

        if (bPrintDetails) {
            System.out.println("Created a new transaction");
            NewTransaction.printDetails();
        }
        return NewTransaction;
    }

    public void SendTransaction(Transaction NewTransaction){
        NewTransaction.setStatus(TransactionStatus.BeingProcessed);
        transactionProcessor.ProcessTransaction(NewTransaction);
    }

}
