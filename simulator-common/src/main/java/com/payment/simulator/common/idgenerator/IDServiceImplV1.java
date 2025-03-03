package com.payment.simulator.common.idgenerator;

import com.payment.simulator.common.enums.PaymentSystem;
import com.payment.simulator.common.enums.FundDirection;
import com.payment.simulator.common.enums.PaymentMethod;
import com.payment.simulator.common.enums.TransactionType;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * transactionTime(yyyyMMddhhmmss 14) + systemId(2) + transactionTypeId(2) + cardTypeId(2) + fundDirection(1) + id version(1) + resvered digits(2) + sequence number(8)
 */
public class IDServiceImplV1 implements IDGeneratorService {
    private final int SEQUENCE_NUMBER_LENGTH = 4;

    private PaymentSystem paymentSystem;
    private String instanceId;
    private AtomicLong atomicLong = new AtomicLong(0);
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");

    public IDServiceImplV1(String instanceId, PaymentSystem paymentSystem){
        this.instanceId = instanceId;
        this.paymentSystem = paymentSystem;
    }

    @Override
    public String generateId(TransactionType transactionType, PaymentMethod paymentMethod, FundDirection fundDirection) {
        String formattedId = String.format("%08d", atomicLong.incrementAndGet());// at least 8 digits
        String sequenceValue = formattedId.substring(formattedId.length() - SEQUENCE_NUMBER_LENGTH, formattedId.length());

        StringBuilder idBuilder = new StringBuilder("");
        idBuilder.append(dateTimeFormatter.format(OffsetDateTime.now(ZoneOffset.UTC))); // 14 digits
        idBuilder.append(paymentSystem.getSystemId()); // 2 digits
        idBuilder.append(transactionType.getTransactionTypeId()); // 2 digits
        idBuilder.append(paymentMethod.getPaymentMethodId()); // 2 digits
        idBuilder.append(fundDirection.getDirectionId()); // 1 digit
        idBuilder.append(IDVersion.VERSION1.getVersionNumber()); // 1 digit
        idBuilder.append(instanceId.length() > 1 ? instanceId.substring(instanceId.length() - 2) : "0" + instanceId); // instance id - only
        idBuilder.append(sequenceValue); // 4 digits

        return idBuilder.toString();
    }

    public static void main(String[] args) {
        IDServiceImplV1 v1 = new IDServiceImplV1("999", PaymentSystem.PAYMENT_API);
        System.out.println(v1.generateId(TransactionType.AUTHENTICATION, PaymentMethod.BANK_TRANSFER, FundDirection.PAYIN));
    }
}
