package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Stock;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import models.StockStore;
import utils.Util;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;

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
}
