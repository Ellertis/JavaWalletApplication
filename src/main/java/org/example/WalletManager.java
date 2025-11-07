package org.example;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class WalletManager {
    private Wallet senderWallet;
    private Wallet receiverWallet;
    public static List<Wallet> Wallets = new ArrayList<>();

    //receive transaction to determine if funds should be sent or received
    public void ProcessTransaction(Transaction NewTransaction){

        SetWallets(NewTransaction);
        CheckCurrency(NewTransaction,senderWallet);
        CheckCurrency(NewTransaction,receiverWallet);
        ProcessFunds(NewTransaction,senderWallet,receiverWallet);
        SaveTransaction(NewTransaction,senderWallet);
        SaveTransaction(NewTransaction,receiverWallet);

    }

    public void SetWallets(Transaction NewTransaction){
        senderWallet = FindWallet(NewTransaction, NewTransaction.getSenderReceiver()[1]);
        receiverWallet = FindWallet(NewTransaction, NewTransaction.getSenderReceiver()[0]);
    }

    public Wallet FindWallet(Transaction NewTransaction,String WalletName){
        for(Wallet wallet : Wallets){
            if(wallet.getName().equals(WalletName)){
                return wallet;
            }
        }
        Wallet NewWallet = CreateNewWallet(0f,Currency.Euro,WalletName);
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
        return;
    }

    public void ProcessFunds(Transaction NewTransaction,Wallet senderWallet,Wallet receiverWallet){
        senderWallet.Balance += NewTransaction.getAmount();
        receiverWallet.Balance -= NewTransaction.getAmount();
    }

    public void ConvertFunds(Transaction NewTransaction,Wallet wallet){
        wallet.Balance = new ConversionRates().ConvertFunds(NewTransaction.getCurrency(),wallet.Currency, wallet.Balance);
    }

    public void SaveTransaction(Transaction NewTransaction,Wallet wallet){
        wallet.TransactionList.add(NewTransaction);
        NewTransaction.setStatus(TransactionStatus.Processed);
        System.out.print("Transaction saved of amount: " + NewTransaction.getAmount()+" | Wallet: "+ wallet.getName() + " | New Amount: "+ wallet.getBalance());
    }

    public void RejectTransaction(Transaction NewTransaction,String Reason){
        System.out.println("Transaction :"+ Arrays.toString(NewTransaction.getSenderReceiver()) + " Amount: "+ NewTransaction.getAmount() + "Rejected");
        System.out.println("Reason: " + Reason);
    }
}
