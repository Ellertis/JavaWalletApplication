package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.concurrent.ThreadLocalRandom;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static List<String> CustomersList = new ArrayList<>(List.of("Amadou","Emils","Tomas","Ivan"));

    public static void main(String[] args){
        Rool();
    }
    static void Rool() {
        Transaction myTransaction1 = TransactionManager.CreateNewTransaction(TransactionStatus.Processed,new String[]{"Cino","Tino"},76, Currency.Euro,LocalDate.now(),true);

        Transaction my2Transaction2 = TransactionManager.CreateNewTransaction(
                TransactionStatus.BeingProcessed, //Status
                new String[]{"alalalla","bououboubou"}, //Sender|Receiver
                24, //Amount
                Currency.Euro, //Currency
                LocalDate.now(), //Date
                true); //Print Details upon creation

        TransactionManager newTM = new TransactionManager();

        Stream<Transaction> streamGenerated =
                Stream.generate(() -> TransactionManager.CreateNewTransaction(TransactionStatus.UnProcessed,new String[]{GetRandomCustomer(),GetRandomCustomer()},GetRandomAmount(269), Currency.Euro,LocalDate.now(),true)).limit(10);
        streamGenerated.forEach(t -> {newTM.SendTransaction(t);});

        /*
        Transaction newTRS = TransactionManager.CreateNewTransaction(TransactionStatus.UnProcessed,new String[]{"Emils","Amadou"},76, Currency.Euro,LocalDate.now(),true);
        Transaction newTRS2 = TransactionManager.CreateNewTransaction(TransactionStatus.UnProcessed,new String[]{"Amadou","Emils"},30, Currency.Euro,LocalDate.now(),true);
        Transaction newTRS3 = TransactionManager.CreateNewTransaction(TransactionStatus.UnProcessed,new String[]{"Emils","Amadou"},0, Currency.Euro,LocalDate.now(),true);

        newTM.SendTransaction(newTRS);
        newTM.SendTransaction(newTRS2);
        newTM.SendTransaction(newTRS3);
        */
    }

    public static String GetRandomCustomer(){
        int rngId = ThreadLocalRandom.current().nextInt(CustomersList.size());
        return CustomersList.get(rngId);
    }

    public static int GetRandomAmount(int maxRange){
        return ThreadLocalRandom.current().nextInt(maxRange);
    }
}
