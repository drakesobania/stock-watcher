package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Stock;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class StocksControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testAdd() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(Json.toJson(new Stock("PLTR")))
                .uri("/");

        Result result = route(app, request);
        assertEquals(CREATED, result.status());
    }

    @Test
    public void testRemove() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(DELETE)
                .bodyJson(Json.toJson(new Stock("PLTR")))
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

}
