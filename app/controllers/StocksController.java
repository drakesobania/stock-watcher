package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.Quote;
import models.Stock;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import models.StockStore;
import utils.Util;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class StocksController extends Controller {

    private final HttpExecutionContext httpExecutionContext;
    private final StockStore stockStore;

    @Inject
    public StocksController(HttpExecutionContext httpExecutionContext, StockStore stockStore) {
        this.httpExecutionContext = httpExecutionContext;
        this.stockStore = stockStore;
    }

    public CompletionStage<Result> add(Http.Request request) {
        JsonNode json = request.body().asJson();
        return supplyAsync(() -> {
            if (json == null) {
                return badRequest(Util.createResponse("Expecting Json data", false));
            }

            Optional<Stock> stockOptional = stockStore.add(Json.fromJson(json, Stock.class));
            return stockOptional.map(stock -> {
                JsonNode jsonObject = Json.toJson(stock);
                return created(Util.createResponse(jsonObject, true));
            }).orElse(internalServerError(Util.createResponse("Could not create data.", false)));
        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> remove(Http.Request request) {
        JsonNode json = request.body().asJson();
        return supplyAsync(() -> {
            if (json == null) {
                return badRequest(Util.createResponse("Expecting Json data", false));
            }

            Optional<Stock> stockOptional = stockStore.add(Json.fromJson(json, Stock.class));
            return stockOptional.map(stock -> {
                stockStore.remove(stock);
                return ok(Util.createResponse("Stock with symbol:" + stock.getSymbol() + " deleted", true));
            }).orElse(internalServerError(Util.createResponse("Could not read data.", false)));
        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> get() {
        return supplyAsync(() -> {
            JsonNode jsonObject = Json.toJson(
                    stockStore
                            .getAll()
                            .stream()
                            .map(stock -> {
                                URL url = null;
                                try {
                                    url = new URL(
                                            "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + stock.getSymbol() + "&apikey=O0E8CRJWEWC6WVH0");
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                Map<String, Map<?, ?>> map = new HashMap<>();
                                ObjectMapper objectMapper = new ObjectMapper();
                                double price = 0;
                                double change = 0;
                                try {
                                    InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
                                    JsonObject response = JsonParser.parseReader(inputStreamReader).getAsJsonObject();
                                    JsonObject globalQuote = response.get("Global Quote").getAsJsonObject();
                                    price = globalQuote.get("05. price").getAsDouble();
                                    change = globalQuote.get("09. change").getAsDouble();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return new Stock(stock.getSymbol(), price, change);
                            })
                            .collect(Collectors.toList()));
            return created(Util.createResponse(jsonObject, true));
        });
    }
}
