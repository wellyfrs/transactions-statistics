# Transactions Statistics

## Specs

### `POST /transactions`

This endpoint is called to create a new transaction.

Body:

```json
{
  "amount": "12.3343",
  "timestamp": "2018-07-17T09:59:51.312Z"
}
```

Where:

- `amount` – transaction amount; a string of arbitrary length that is parsable as a BigDecimal
- `timestamp` – transaction time in the ISO 8601 format `YYYY-MM-DDThh:mm:ss.sssZ` in the UTC timezone (this is not the current timestamp)

Returns: Empty body with one of the following:

- `201` – in case of success
- `204` – if the transaction is older than 60 seconds
- `400` – if the JSON is invalid
- `422` – if any of the fields are not parsable or the transaction date is in the  future

### `GET /statistics`

This endpoint returns the statistics computed on the transactions within the last 60 seconds.

Returns:

```json
{
  "sum": "1000.00",
  "avg": "100.53",
  "max": "200000.49",
  "min": "50.23",
  "count": 10
}
```

Where:

- `sum` – a BigDecimal specifying the total sum of transaction value in the last 60  seconds
- `avg` – a BigDecimal specifying the average amount of transaction value in the  last 60 seconds
- `max` – a BigDecimal specifying single highest transaction value in the last 60  seconds
- `min` – a BigDecimal specifying single lowest transaction value in the last 60  seconds
- `count` – a long specifying the total number of transactions that happened in  the last 60 seconds

All BigDecimal values always contain exactly two decimal places and use
`HALF_ROUND_UP` rounding. eg: `10.345` is returned as `10.35`, `10.8` is returned as
`10.80`.

### `DELETE /transactions`

This endpoint causes all existing transactions to be deleted.

The endpoint should accept an empty request body and return a `204` status code.

---

## Solution

The idea is quite simple: keep the statistics up to date within the last 60 seconds.

For each new valid transaction, update the statistics with the new value and without the old ones.

To reduce time and space complexity to O(1), aggregate the statistics by a constant number of groups using some time criteria: by second, in this case.

I based architecture on [Uncle Bob's Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), which is based on Onion, Hexagonal and a few others. I simplified this a bit to avoid over-engineering. Basically, there are two layers:
- `domain`: the core, with the entities and the use cases (services)
- `infra`: non-domain stuff like the in-memory data provider, the framework, entrypoints (controllers)

The domain layer is totally agnostic to the infra, which guarantees a cool Separation of Concerns. You will also find some traits of Clean Code, as well as SOLID principles such as the Dependence Inversion Principle.

All business requirements have been implemented. To achieve the thread-safety requirement, I used immutable classes like `ConcurrentHashMap` for map, and `OffsetDateTime` for date and time, and I made sure that my own classes were too, with private fields with no setters.

All integration tests via `mvn clean integration-test` passed, and I got good code coverage (100% classes, 85% methods, 89% lines), including REST controllers by using `MockMvc`.

I had some fun! 😀