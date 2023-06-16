package com.example.pokedex.entities;

import java.util.Arrays;

public class BasicResponse {
    Integer count;
    String next;
    String previous;
    Pokemon[] results;

    public BasicResponse() {
    }

    public BasicResponse(Integer count, String next, String previous, Pokemon[] results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Pokemon[] getResults() {
        return results;
    }

    public void setResults(Pokemon[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "BasicResponse{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + Arrays.toString(results) +
                '}';
    }
}
