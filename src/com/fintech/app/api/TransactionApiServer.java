package com.fintech.app.api;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import com.fintech.app.dto.TransactionRequestDTO;
import com.fintech.app.enums.TransactionType;
import com.fintech.app.model.TransactionResult;
import com.fintech.app.repository.jdbc.JdbcAccountRepository;
import com.fintech.app.repository.jdbc.JdbcTransactionRepository;
import com.fintech.app.service.CurrencyService;
import com.fintech.app.service.TransactionService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class TransactionApiServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/transaction", exchange -> {
            addCorsHeaders(exchange);

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                try {
					send(exchange, 405, "{\"error\":\"Only POST allowed\"}");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return;
            }

            try {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                TransactionRequestDTO request = new TransactionRequestDTO();
                request.setSourceAccountId(extract(body, "sourceAccountId"));
                request.setDestinationAccountId(extract(body, "destinationAccountId"));
                request.setAmount(Double.parseDouble(extract(body, "amount")));
                request.setSourceCurrency(extract(body, "sourceCurrency"));
                request.setTargetCurrency(extract(body, "targetCurrency"));
                request.setTransactionType(TransactionType.CROSS_BORDER);

                TransactionService service = new TransactionService(
                        new JdbcAccountRepository(),
                        new JdbcTransactionRepository(),
                        new CurrencyService()
                );

                TransactionResult result = service.processTransaction(request);

                String json = "{"
                        + "\"status\":\"" + result.getTransaction().getStatus() + "\","
                        + "\"creditedAmount\":" + result.getCreditedAmount()
                        + "}";

                send(exchange, 200, json);

            } catch (Exception e) {
                e.printStackTrace();
                try {
					send(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        server.start();
        System.out.println("ARGENTIX Java API running on http://localhost:8080");
    }

    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void send(HttpExchange exchange, int status, String response) throws Exception {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private static String extract(String json, String key) {
        String search = "\"" + key + "\":";
        int start = json.indexOf(search) + search.length();

        if (start < search.length()) return "";

        while (start < json.length() && json.charAt(start) == ' ') start++;

        if (json.charAt(start) == '"') {
            int end = json.indexOf("\"", start + 1);
            return json.substring(start + 1, end);
        } else {
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return json.substring(start, end).trim();
        }
    }
}