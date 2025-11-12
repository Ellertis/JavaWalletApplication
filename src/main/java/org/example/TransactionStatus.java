package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TransactionStatus {
    Failed,
    UnProcessed,
    BeingProcessed,
    Processed;

    private static final List<TransactionStatus> Values = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = Values.size();
    private static final Random RANDOM = new Random();

    public static TransactionStatus randomTransactionStatus(){
        return Values.get(RANDOM.nextInt(SIZE));
    }
}
