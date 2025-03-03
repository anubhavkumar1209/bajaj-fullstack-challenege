package com.example.bfhl.controllers;

import com.example.bfhl.models.BfhlRequest;
import com.example.bfhl.models.BfhlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class BfhlController {

    private static final String USER_ID = "john_doe_01011990"; // Replace with your details
    private static final String EMAIL = "john@college.com";
    private static final String ROLL_NUMBER = "ABCD123";

    @GetMapping("/bfhl")
    public ResponseEntity<Map<String, Integer>> getOperationCode() {
        return ResponseEntity.ok(Collections.singletonMap("operation_code", 1));
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> processData(@RequestBody BfhlRequest request) {
        BfhlResponse response = new BfhlResponse();
        response.setUser_id(USER_ID);
        response.setEmail(EMAIL);
        response.setRoll_number(ROLL_NUMBER);

        try {
            List<String> data = request.getData();
            List<String> numbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> highestAlphabets = new ArrayList<>();
            char maxChar = 0;

            for (String item : data) {
                if (item.matches("\\d+")) {
                    numbers.add(item);
                } else if (item.matches("[a-zA-Z]")) {
                    alphabets.add(item);
                    char current = Character.toUpperCase(item.charAt(0));
                    if (current > maxChar) {
                        maxChar = current;
                        highestAlphabets.clear();
                        highestAlphabets.add(item);
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }

            response.setNumbers(numbers);
            response.setAlphabets(alphabets);
            response.setHighest_alphabet(highestAlphabets);
            response.setIs_success(true);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setIs_success(false);
            return ResponseEntity.badRequest().body(response);
        }
    }
}