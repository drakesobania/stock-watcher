# Stock-Watcher

This is a proof-of-concept REST endpoint for watching stocks.

Note that this uses the [Alpha Vantage API](https://www.alphavantage.co/) with a restriction on the number of total requests and requests per minute, so errors are likely to occur when multiple requests are made within a short time period.

Special thanks to [baeldung](https://www.baeldung.com/rest-api-with-play) for a great REST API with Play Framework example that I used as a reference.

## Running
Assuming `sbt` is already installed with JDK 11+, simply run `sbt run` from the command line while in the project root directory.

## Adding a stock to watch

```
curl --location --request POST 'http://localhost:9000/' \
--header 'Content-Type: application/json' \
--data-raw '{"symbol":"AMC"}'
```

## Removing a stock from the watch list

```
curl --location --request DELETE 'http://localhost:9000/' \
--header 'Content-Type: application/json' \
--data-raw '{"symbol":"AMC"}'
```

## Getting the list of watched stocks with quotes

```
curl --location --request GET 'http://localhost:9000/' \
--header 'Content-Type: application/json' \
--data-raw ''
```

## Getting the list of watched stocks with quotes and averages

```
curl --location --request GET 'http://localhost:9000/?interval=weekly&timePeriod=10&seriesType=open' \
--header 'Content-Type: application/json' \
--data-raw ''
```