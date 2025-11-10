package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Transaction {

    public TransactionStatus status;
    public LocalDate date;
    public String[] senderReceiver = new String[2];
    public float amount;
    public Currency currency;
/*
    public TransactionStatus getStatus() {
        return status;
    }
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String[] getSenderReceiver() {
        return senderReceiver;
    }
    public void setSenderReceiver(String[] senderReceiver) {
        this.senderReceiver = senderReceiver;
    }
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
*/
    //Print TransactionDetails
    public void printDetails(){
        System.out.println("Status: " + getStatus());
        System.out.println("Sender|Receiver: " + Arrays.toString(getSenderReceiver()));
        System.out.println("Amount: " + getAmount() + getCurrency());
        System.out.println("Date: " + getDate());
    }
}
