package org.example;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-07T16:49:56+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction mapToTransaction(InputTransaction inputTransaction) {
        if ( inputTransaction == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setStatus( inputTransaction.getStatus() );
        String[] senderReceiver = inputTransaction.getSenderReceiver();
        if ( senderReceiver != null ) {
            transaction.setSenderReceiver( Arrays.copyOf( senderReceiver, senderReceiver.length ) );
        }
        transaction.setCurrency( inputTransaction.getCurrency() );

        return transaction;
    }
}
