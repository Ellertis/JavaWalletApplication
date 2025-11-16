package org.example;

import lombok.Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

@Data
public class TransactionProcessor {
    private Wallet senderWallet;
    private Wallet receiverWallet;

    public void setWallets(Transaction NewTransaction){
        senderWallet = TransactionManager.walletManager.findWallet(NewTransaction.getSenderReceiver()[0]);
        receiverWallet = TransactionManager.walletManager.findWallet(NewTransaction.getSenderReceiver()[1]);
    }

    public void checkCurrency(Transaction NewTransaction, Wallet wallet){
        if (!NewTransaction.getCurrency().equals(wallet.getCurrency())) {
            convertFunds(NewTransaction,wallet);
        }
    }

    public void convertFunds(Transaction NewTransaction, Wallet wallet){
        wallet.Balance = new ConversionRates().convertFunds(NewTransaction.getCurrency(),wallet.Currency, wallet.Balance);
    }

    public boolean checkBalance(Transaction newTransaction, Wallet wallet) {
        return (newTransaction.getAmount() > wallet.getBalance());
    }

    public void processTransaction(Transaction NewTransaction) {
        System.out.println("Status: " + NewTransaction.getStatus());

        setWallets(NewTransaction);
        if (senderWallet == receiverWallet) {
            System.out.println("Transaction is not processed further, the sender and receiver are the same");
            NewTransaction.setStatus(TransactionStatus.Failed);
            return;
        }

        checkCurrency(NewTransaction, senderWallet);
        checkCurrency(NewTransaction, receiverWallet);

        try {
            if (checkBalance(NewTransaction, senderWallet))
                throw new ArithmeticException(("Transaction is not processed further, insufficient funds " + "Sender Balance: " + senderWallet.getBalance()));}
        //Transaction Failed
        catch (ArithmeticException e) {
            System.out.println("Transaction Failed");
            NewTransaction.setStatus(TransactionStatus.Failed);
            //Saving Failed Transactions
            saveTransaction(NewTransaction, senderWallet, receiverWallet, false);
            return;
        }

        //Transaction Succes
        senderWallet.withdraw(NewTransaction.getAmount());
        receiverWallet.deposit(NewTransaction.getAmount());

        //Saving Successful Transactions
        saveTransaction(NewTransaction, senderWallet, receiverWallet, true);
    }

    public void saveTransaction(Transaction NewTransaction, Wallet wallet, Wallet wallet2, boolean bSuccess){
        wallet.TransactionList.add(NewTransaction);
        if(bSuccess)
            NewTransaction.setStatus(TransactionStatus.Processed);
        System.out.println("Transaction saved: "+"Amount :" + NewTransaction.getAmount()+
                " | Wallet: "+ wallet.getName()+
                " | New Amount: "+ wallet.getBalance()+
                " | TO: "+
                " | Wallet: "+ wallet2.getName()+
                " | New Amount: "+ wallet2.getBalance()+
                " Status: "+ NewTransaction.getStatus());
        try {
            writeToSaveFile(NewTransaction, senderWallet, receiverWallet);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void writeToSaveFile(Transaction TransactionToWrite, Wallet wallet1, Wallet wallet2) throws IOException {
        float Amount = TransactionToWrite.getAmount();
        LocalDate Date = TransactionToWrite.getDate();
        TransactionStatus Status = TransactionToWrite.getStatus();

        String GeneralOutput = "From: " + wallet1.getName() + " TO: " + wallet2.getName() + " AMOUNT: " + Amount + TransactionToWrite.getCurrency() + " AT: " + Date.toString() + " Status: " + Status.toString();
        String SenderOutput = "SENT: " + GeneralOutput + " New Balance: " + wallet1.getBalance();

        BufferedWriter writer = new BufferedWriter(new FileWriter("TransactionList" + wallet1.getName() + ".txt",true));
        writer.newLine();
        writer.write(SenderOutput);
        writer.close();

        String ReceiverOutput = "RECEIVED: " + GeneralOutput + " New Balance: " + wallet2.getBalance();
        writer = new BufferedWriter(new FileWriter("TransactionList" + wallet2.getName() + ".txt",true));
        writer.newLine();
        writer.write(ReceiverOutput);
        writer.close();
    }

}
