package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputTransaction {

    public TransactionStatus status;
    public LocalDate localDate;
    public String[] senderReceiver = new String[2];
    public float transactionAmount;
    public Currency currency;

}
