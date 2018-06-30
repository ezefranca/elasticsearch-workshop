package br.com.fiap.pagseguro.extract;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class ExtractTransaction {

    @Value("${route.transaction.id.start}")
    private Long transactionIdStart;

    @Value("${route.transaction.id.range}")
    private Long transactionIdRange;

    private static Long betweenStart;
    private static Long betweenEnd;

    public List<Long> prepareSelectParameters() {

        final ArrayList<Long> selectParameters = new ArrayList<>();

        Long actualBetweenStart = betweenStart;
        Long actualBetweenEnd = betweenEnd;

        if (actualBetweenStart == null || actualBetweenEnd == null) {

            betweenStart = transactionIdStart;
            betweenEnd = transactionIdRange;

        } else {

            Long nextBetweenStart = actualBetweenEnd + 1L;
            Long nextBetweenEnd = actualBetweenEnd + transactionIdRange;

            betweenStart = nextBetweenStart;
            betweenEnd = nextBetweenEnd;
        }

        selectParameters.add(betweenStart);
        selectParameters.add(betweenEnd);


        return selectParameters;
    }

}
