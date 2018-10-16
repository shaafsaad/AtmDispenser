# AtmDispenser
Have used Spring Boot as the framework and REST Api with PUT method to withdraw amount.
To run spring boot using maven: mvn spring-boot:run


URL for postman is 'http://localhost:8080/user/withdraw/{withdrawAmount}'
Example: http://localhost:8080/user/withdraw/500


Sameple Response:
{
    "success": true,
    "denomination": [
        20,
        50,
        500
    ],
    "quantity": [
        20,
        18,
        1
    ],
    "dispensedNotes": [
        0,
        2,
        0
    ],
    "balanceLeftInATM": 1800,
    "dispensedCurrencyString": "[$50 x 2 ]",
    "withdrawAmount": 100,
    "errorMessage": null
}


Unit testing has been done using Junit and mocking using mockito.
Have added few dummy classes for show purpose whose coverage has not been done.
