package org.example;
import lombok.Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class WalletManager {
    private Wallet senderWallet;
    private Wallet receiverWallet;
    public static List<Wallet> Wallets = new ArrayList<>();

    public void ProcessTransaction(Transaction NewTransaction) {
        System.out.println("Status: " + NewTransaction.getStatus());

        SetWallets(NewTransaction);
        if (senderWallet == receiverWallet) {
            System.out.println("Transaction is not processed further, the sender and receiver are the same");
            NewTransaction.setStatus(TransactionStatus.Failed);
            return;
        }

        CheckCurrency(NewTransaction, senderWallet);
        CheckCurrency(NewTransaction, receiverWallet);

        try {
            if (CheckBalance(NewTransaction, senderWallet))
                throw new ArithmeticException(("Transaction is not processed further, insufficient funds " + "Sender Balance: " + senderWallet.getBalance()));
        }

        //Transaction Failed
        catch (ArithmeticException e) {
            System.out.println("Transaction Failed");
            NewTransaction.setStatus(TransactionStatus.Failed);
            //Saving Failed Transactions
            SaveTransaction(NewTransaction, senderWallet, receiverWallet, false);
            try {
                WriteToSaveFile(NewTransaction, senderWallet, receiverWallet);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            return;
        }

        //Transaction Succes
        senderWallet.Withdraw(NewTransaction.getAmount());
        receiverWallet.Deposit(NewTransaction.getAmount());

        //Saving Successful Transactions
        SaveTransaction(NewTransaction, senderWallet, receiverWallet, true);
        try {
            WriteToSaveFile(NewTransaction, senderWallet, receiverWallet);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void SetWallets(Transaction NewTransaction){
        senderWallet = FindWallet(NewTransaction.getSenderReceiver()[0]);
        receiverWallet = FindWallet(NewTransaction.getSenderReceiver()[1]);
    }

    public Wallet FindWallet(String WalletName){
        for(Wallet wallet : Wallets){
            if(wallet.getName().equals(WalletName)){
                return wallet;
            }
        }
        Wallet NewWallet = CreateNewWallet(GetRandomAmount(300),Currency.Euro,WalletName);
        Wallets.add(NewWallet);
        return NewWallet;
    }

    public Wallet CreateNewWallet(float balance,Currency currency,String name){
        Wallet wallet = new Wallet();
        wallet.setBalance(balance);
        wallet.setName(name);
        wallet.setCurrency(currency);
        return wallet;
    }

    public void CheckCurrency(Transaction NewTransaction,Wallet wallet){
        if (!NewTransaction.getCurrency().equals(wallet.getCurrency())) {
            ConvertFunds(NewTransaction,wallet);
        }
    }

    public void ConvertFunds(Transaction NewTransaction,Wallet wallet){
        wallet.Balance = new ConversionRates().ConvertFunds(NewTransaction.getCurrency(),wallet.Currency, wallet.Balance);
    }

    public boolean CheckBalance(Transaction newTransaction, Wallet wallet) {
        return (newTransaction.getAmount() > wallet.getBalance());
    }

    public void SaveTransaction(Transaction NewTransaction,Wallet wallet,Wallet wallet2,boolean bSuccess){
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
    }

    public static void WriteToSaveFile(Transaction TransactionToWrite,Wallet wallet1,Wallet wallet2) throws IOException {
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

    public static int GetRandomAmount(int maxRange){
        return ThreadLocalRandom.current().nextInt(maxRange);
    }

    public void RejectTransaction(Transaction NewTransaction,String Reason){
        System.out.println("Transaction :"+ Arrays.toString(NewTransaction.getSenderReceiver()) + " Amount: "+ NewTransaction.getAmount() + "Rejected");
        System.out.println("Reason: " + Reason);
    }
}
