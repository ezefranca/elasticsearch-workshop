package br.com.fiap.pagseguro.transform;

import br.com.fiap.pagseguro.vo.TransactionVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransformTransaction {

    @Handler
    public List<TransactionVO> transform(final List<Map<String, String>> resultSet) {

        System.out.println("Start transform");

        final List<TransactionVO> transactions = new ArrayList<>();

        for (final Map<String, String> transactionDB : resultSet) {

            final String id = ((Object) transactionDB.get("ID")).toString();
            final String hash = transactionDB.get("hash").toString();
            final String buyerEmail = (transactionDB.get("buyer_email")).toString();
            final String sellerEmail = transactionDB.get("seller_email").toString();
            final String value = ((Object) transactionDB.get("value")).toString();
            final String createDate = ((Object) transactionDB.get("create_date")).toString().replace(".0", "").replace(" ", "T");

            transactions.add(new TransactionVO(Long.valueOf(id), hash, buyerEmail, sellerEmail, Double.valueOf(value), createDate));
        }

        System.out.println("End transform");

        return transactions;
    }

    /*
    ##################################################################
                            CODE CHALLENGE
    ##################################################################
    */
    public String createBulk(final List<TransactionVO> transactions) {

        System.out.println("Start bulk");

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        final StringBuilder bulk = new StringBuilder();

        for (final TransactionVO transaction : transactions) {

            final String index = createIndex(transaction);
            final String json = gson.toJson(transaction);

            bulk.append(index);
            bulk.append("\n");
            bulk.append(json);
            bulk.append("\n");
        }

        System.out.println("End bulk");

        return bulk.toString();
    }

    private String createIndex(final TransactionVO transaction) {

        final String index = "fiap1";
        return String.format(
                "{ \"index\" : { \"_index\" : \"%s\", \"_type\" : \"transaction\" , \"_id\" : \"%s\"} }",
                index, transaction.getId());
    }

     /*
    ##################################################################
                            CODE CHALLENGE
    ##################################################################
    */



}
