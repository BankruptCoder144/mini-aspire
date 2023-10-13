package com.example.miniaspire.controllers;

import com.example.miniaspire.MiniAspireApplication;
import com.example.miniaspire.dto.CustomerDto;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MiniAspireApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGetAllCustomers() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/admin/customer"),
                HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());

        String expected = "[{\"customerId\":1,\"firstName\":\"Bob\",\"lastName\":\"Smith\"},{\"customerId\":2,\"firstName" +
                "\":\"Steve\",\"lastName\":\"Mathew\"},{\"customerId\":3,\"firstName\":\"Robin\",\"lastName\":\"Singh\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGetAllLoans() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/admin/loan"),
                HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());

        String expected = "[{\"loanId\":1,\"customerId\":1,\"loanAmount\":25.0,\"terms\":3,\"date\":\"2023-05-17\",\"pa" +
                "yments\":[{\"paymentId\":1,\"amount\":8.333333,\"dueDate\":\"2023-05-24\",\"status\":\"PENDING\"}," +
                "{\"paymentId\":2,\"amount\":8.333333,\"dueDate\":\"2023-05-31\",\"status\":\"PENDING\"},{\"payment" +
                "Id\":3,\"amount\":8.333334,\"dueDate\":\"2023-06-07\",\"status\":\"PENDING\"}],\"status\":\"PENDING\"}" +
                ",{\"loanId\":2,\"customerId\":2,\"loanAmount\":35.4,\"terms\":4,\"date\":\"2023-07-30\",\"payments\":" +
                "[{\"paymentId\":4,\"amount\":8.85,\"dueDate\":\"2023-08-06\",\"status\":\"PENDING\"},{\"paymentId\":5," +
                "\"amount\":8.85,\"dueDate\":\"2023-08-13\",\"status\":\"PENDING\"},{\"paymentId\":6,\"amount\":8.85,\"" +
                "dueDate\":\"2023-08-20\",\"status\":\"PENDING\"},{\"paymentId\":7,\"amount\":8.85,\"dueDate\":\"" +
                "2023-08-27\",\"status\":\"PENDING\"}],\"status\":\"PENDING\"},{\"loanId\":3,\"customerId\":1,\"l" +
                "oanAmount\":102.1,\"terms\":6,\"date\":\"2023-06-01\",\"payments\":[{\"paymentId\":8,\"amount\":17.016666" +
                ",\"dueDate\":\"2023-06-08\",\"status\":\"PENDING\"},{\"paymentId\":9,\"amount\":17.016666,\"dueDate\":" +
                "\"2023-06-15\",\"status\":\"PENDING\"},{\"paymentId\":10,\"amount\":17.016666,\"dueDate\":\"2023-06-22\"," +
                "\"status\":\"PENDING\"},{\"paymentId\":11,\"amount\":17.016666,\"dueDate\":\"2023-06-29\",\"status\":" +
                "\"PENDING\"},{\"paymentId\":12,\"amount\":17.016666,\"dueDate\":\"2023-07-06\",\"status\":\"PENDING\"}," +
                "{\"paymentId\":13,\"amount\":17.01667,\"dueDate\":\"2023-07-13\",\"status\":\"PENDING\"}],\"status\":" +
                "\"APPROVED\"}]\n";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void testCreateCustomer() throws JSONException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Foo");
        customerDto.setLastName("Bar");
        HttpEntity<CustomerDto> entity = new HttpEntity<CustomerDto>(customerDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/admin/customer"),
                HttpMethod.POST, entity, String.class
        );

        System.out.println(response.getBody());

        String expectedResponse = "{\"id\":4,\"firstName\":\"Foo\",\"lastName\":\"Bar\",\"loans\":null}";

        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void testApproveLoan() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/admin/loan/2"),
                HttpMethod.PUT, entity, String.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testApproveLoan_throwError() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/admin/loan/4"),
                HttpMethod.PUT, entity, String.class
        );

        System.out.println(response.getBody());

        String expectedResponse = "{\"message\":\"Loan doesn't exist or is not in PENDING states\"}";

        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}

