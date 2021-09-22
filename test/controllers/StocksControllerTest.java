package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Stock;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.File;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class StocksControllerTest extends WithApplication {

    @Before
    public void deleteFile() {
        new File("stocks.txt").delete();
    }

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
        assertTrue(contentAsString(result).contains("PLTR"));

        request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        result = route(app, request);
        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("PLTR"));
    }

    @Test
    public void testRemove() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(Json.toJson(new Stock("PLTR")))
                .uri("/");

        Result result = route(app, request);
        assertEquals(CREATED, result.status());

        request = new Http.RequestBuilder()
                .method(DELETE)
                .bodyJson(Json.toJson(new Stock("PLTR")))
                .uri("/");

        result = route(app, request);
        assertEquals(OK, result.status());

        request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        result = route(app, request);
        assertEquals(OK, result.status());
        assertFalse(contentAsString(result).contains("PLTR"));
    }

}
