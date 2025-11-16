package org.example;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class WalletManager {
    public static List<Wallet> Wallets = new ArrayList<>();

    public Wallet findWallet(String WalletName){
        for(Wallet wallet : Wallets){
            if(wallet.getName().equals(WalletName)){
                return wallet;
            }
        }
        Wallet NewWallet = createNewWallet(ThreadLocalRandom.current().nextInt(300),Currency.Euro,WalletName);
        Wallets.add(NewWallet);
        return NewWallet;
    }

    public Wallet createNewWallet(float balance, Currency currency, String name){
        Wallet wallet = new Wallet();
        wallet.setBalance(balance);
        wallet.setName(name);
        wallet.setCurrency(currency);
        return wallet;
    }
}
