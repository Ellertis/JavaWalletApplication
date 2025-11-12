package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static List<String> CustomersList = new ArrayList<>(List.of("Amadou","Emils","Tomas","Ivan"));
    public static TransactionManager TrsMngr = new TransactionManager();

    public static void main(String[] args){
        //StreamGenerateTransactions(10);

        Runnable task = () -> {
            Transaction NewTransaction = TransactionManager.CreateNewTransaction(TransactionStatus.randomTransactionStatus(),new String[]{GetRandomCustomer(),GetRandomCustomer()},GetRandomAmount(269), Currency.Euro,LocalDate.now(),true);
            TrsMngr.SendTransaction(NewTransaction);
        };
        while (true){
            task.run();
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    static void StreamGenerateTransactions(int limit) {
        Stream<Transaction> streamGenerated =
                Stream.generate(() -> TransactionManager.CreateNewTransaction(TransactionStatus.UnProcessed,new String[]{GetRandomCustomer(),GetRandomCustomer()},GetRandomAmount(269), Currency.Euro,LocalDate.now(),true)).limit(limit);
        streamGenerated.forEach(t -> {TrsMngr.SendTransaction(t);});
    }

    public static String GetRandomCustomer(){
        int rngId = ThreadLocalRandom.current().nextInt(CustomersList.size());
        return CustomersList.get(rngId);
    }

    public static int GetRandomAmount(int maxRange){
        return ThreadLocalRandom.current().nextInt(maxRange);
    }
}
