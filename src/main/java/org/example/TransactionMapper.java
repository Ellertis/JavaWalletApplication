package org.example;

import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {
    Transaction mapToTransaction(InputTransaction inputTransaction);
}
