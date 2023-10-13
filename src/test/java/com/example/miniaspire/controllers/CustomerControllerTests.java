package com.example.miniaspire.controllers;


import com.example.miniaspire.MiniAspireApplication;
import com.example.miniaspire.dto.LoanDto;
import com.example.miniaspire.dto.RepaymentDto;
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
public class CustomerControllerTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGetLoanById() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/customer/1/loan/1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{\"loanId\":1,\"customerId\":1,\"loanAmount\":25.0,\"terms\":3,\"date\":\"2023-05-17\"," +
                "\"payments\":[{\"paymentId\":1,\"amount\":8.333333,\"dueDate\":\"2023-05-24\",\"status\":\"PENDING\"}," +
                "{\"paymentId\":2,\"amount\":8.333333,\"dueDate\":\"2023-05-31\",\"status\":\"PENDING\"},{\"paymentId\":" +
                "3,\"amount\":8.333334,\"dueDate\":\"2023-06-07\",\"status\":\"PENDING\"}],\"status\":\"PENDING\"}\n";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGetAllLoansByCustomerId() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/customer/1/loan"),
                HttpMethod.GET, entity, String.class);

        String expected = "[{\"loanId\":1,\"customerId\":1,\"loanAmount\":25.0,\"terms\":3,\"date\":\"2023-05-17\",\"" +
                "payments\":[{\"paymentId\":1,\"amount\":8.333333,\"dueDate\":\"2023-05-24\",\"status\":\"PENDING\"},{\"" +
                "paymentId\":2,\"amount\":8.333333,\"dueDate\":\"2023-05-31\",\"status\":\"PENDING\"},{\"paymentId\":3," +
                "\"amount\":8.333334,\"dueDate\":\"2023-06-07\",\"status\":\"PENDING\"}],\"status\":\"PENDING\"},{\"" +
                "loanId\":3,\"customerId\":1,\"loanAmount\":102.1,\"terms\":6,\"date\":\"2023-06-01\",\"payments\":[{" +
                "\"paymentId\":8,\"amount\":17.016666,\"dueDate\":\"2023-06-08\",\"status\":\"PENDING\"},{\"paymentId\"" +
                ":9,\"amount\":17.016666,\"dueDate\":\"2023-06-15\",\"status\":\"PENDING\"},{\"paymentId\":10,\"amount\"" +
                ":17.016666,\"dueDate\":\"2023-06-22\",\"status\":\"PENDING\"},{\"paymentId\":11,\"amount\":17.016666,\"" +
                "dueDate\":\"2023-06-29\",\"status\":\"PENDING\"},{\"paymentId\":12,\"amount\":17.016666,\"dueDate\":\"" +
                "2023-07-06\",\"status\":\"PENDING\"},{\"paymentId\":13,\"amount\":17.01667,\"dueDate\":\"2023-07-13\",\"" +
                "status\":\"PENDING\"}],\"status\":\"APPROVED\"}]\n";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGetLoanById_returnsForbidden() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/customer/1/loan/2"),
                HttpMethod.GET, entity, String.class);

        String expected = "{\"message\":\"CustomerId 1 don't have permission to view LoanId 2\"}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    public void testCreateLoan() throws JSONException {
        LoanDto loanDto = new LoanDto();
        loanDto.setLoanAmount(124.11f);
        loanDto.setCustomerId(3);
        loanDto.setDate("2023-01-05");
        loanDto.setTerms(3);
        HttpEntity<LoanDto> entity = new HttpEntity<LoanDto>(loanDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/customer/loan"),
                HttpMethod.POST, entity, String.class
        );

        System.out.println(response.getBody());

        String expectedResponse = "{\"loanId\":4,\"customerId\":3,\"loanAmount\":124.11,\"terms\":3,\"date\":\"2023-01-05\"," +
                "\"payments\":[{\"paymentId\":14,\"amount\":41.37,\"dueDate\":\"2023-01-12\",\"status\":\"PENDING\"}," +
                "{\"paymentId\":15,\"amount\":41.37,\"dueDate\":\"2023-01-19\",\"status\":\"PENDING\"},{\"paymentId\":16," +
                "\"amount\":41.370003,\"dueDate\":\"2023-01-26\",\"status\":\"PENDING\"}],\"status\":\"PENDING\"}\n";

        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void testRepayAmount() throws JSONException {
        RepaymentDto repaymentDto = new RepaymentDto();
        repaymentDto.setAmount(17.2f);
        repaymentDto.setCustomerId(1);
        repaymentDto.setLoanId(3);
        HttpEntity<RepaymentDto> entity = new HttpEntity<RepaymentDto>(repaymentDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/customer/payment"),
                HttpMethod.PUT, entity, String.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRepayAmount_lessAmount() throws JSONException {
        RepaymentDto repaymentDto = new RepaymentDto();
        repaymentDto.setAmount(14.2f);
        repaymentDto.setCustomerId(1);
        repaymentDto.setLoanId(3);
        HttpEntity<RepaymentDto> entity = new HttpEntity<RepaymentDto>(repaymentDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v1/customer/payment"),
                HttpMethod.PUT, entity, String.class
        );

        String expectedResponse = "{\"message\":\"Amount less than the payment amount\"}";

        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
