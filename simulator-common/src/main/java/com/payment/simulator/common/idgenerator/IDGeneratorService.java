package com.payment.simulator.common.idgenerator;

import com.payment.simulator.common.enums.FundDirection;
import com.payment.simulator.common.enums.PaymentMethod;
import com.payment.simulator.common.enums.TransactionType;

public interface IDGeneratorService {
    String generateId(TransactionType transactionType, PaymentMethod paymentMethod, FundDirection fundDirection);
}
