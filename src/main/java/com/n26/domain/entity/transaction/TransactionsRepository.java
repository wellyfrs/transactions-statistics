package com.n26.domain.entity.transaction;

import java.time.OffsetDateTime;

public interface TransactionsRepository {

    void saveTransaction(Transaction transaction);

    void removeAllBefore(OffsetDateTime offsetDateTime);

    void removeAll();

}