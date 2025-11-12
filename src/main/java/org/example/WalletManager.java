package org.example;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class WalletManager {
    private Wallet senderWallet;
    private Wallet receiverWallet;
    public static List<Wallet> Wallets = new ArrayList<>();

    public void ProcessTransaction(Transaction NewTransaction){
        System.out.println("Status: "+NewTransaction.getStatus());
        try {
            SetWallets(NewTransaction);
            CheckCurrency(NewTransaction, senderWallet);
            CheckCurrency(NewTransaction, receiverWallet);
            if (CheckBalance(NewTransaction, senderWallet)) {
                throw new ArithmeticException(("Transaction is not processed further, insufficient funds " +
                        "Sender Balance: " + senderWallet.getBalance()));
            }
        } catch(ArithmeticException e) {
            System.out.println("Transaction Failed");
            NewTransaction.setStatus(TransactionStatus.Failed);
            SaveTransaction(NewTransaction, senderWallet, receiverWallet,false);
            return;
        }
        SaveTransaction(NewTransaction, senderWallet, receiverWallet,true);
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

    public boolean CheckBalance(Transaction newTransaction, Wallet wallet) {
        return (newTransaction.getAmount() > wallet.getBalance());
    }

    public static int GetRandomAmount(int maxRange){
        return ThreadLocalRandom.current().nextInt(maxRange);
    }

    public void RejectTransaction(Transaction NewTransaction,String Reason){
        System.out.println("Transaction :"+ Arrays.toString(NewTransaction.getSenderReceiver()) + " Amount: "+ NewTransaction.getAmount() + "Rejected");
        System.out.println("Reason: " + Reason);
    }
}
