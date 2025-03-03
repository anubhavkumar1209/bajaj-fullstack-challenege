package com.example.bfhl.models;

import java.util.List;

public class BfhlResponse {
    private boolean is_success;
    private String user_id;
    private String email;
    private String roll_number;
    private List<String> numbers;
    private List<String> alphabets;
    private List<String> highest_alphabet;

    // Getters
    public boolean isIs_success() {
        return is_success;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public List<String> getAlphabets() {
        return alphabets;
    }

    public List<String> getHighest_alphabet() {
        return highest_alphabet;
    }

    // Setters
    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public void setAlphabets(List<String> alphabets) {
        this.alphabets = alphabets;
    }

    public void setHighest_alphabet(List<String> highest_alphabet) {
        this.highest_alphabet = highest_alphabet;
    }
}