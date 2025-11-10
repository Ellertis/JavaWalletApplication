package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static List<String> CustomersList = new ArrayList<>(List.of("Amadou","Emils","Tomas","Ivan"));

    public static void main(String[] args){
        Rool();
    }
    static void Rool() {

        TransactionManager newTM = new TransactionManager();

        Stream<Transaction> streamGenerated =
                Stream.generate(() -> TransactionManager.CreateNewTransaction(TransactionStatus.UnProcessed,new String[]{GetRandomCustomer(),GetRandomCustomer()},GetRandomAmount(269), Currency.Euro,LocalDate.now(),true)).limit(10);
        streamGenerated.forEach(t -> {newTM.SendTransaction(t);});
    }

    public static String GetRandomCustomer(){
        int rngId = ThreadLocalRandom.current().nextInt(CustomersList.size());
        return CustomersList.get(rngId);
    }

    public static int GetRandomAmount(int maxRange){
        return ThreadLocalRandom.current().nextInt(maxRange);
    }
}
