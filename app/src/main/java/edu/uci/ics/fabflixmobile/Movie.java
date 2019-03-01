package edu.uci.ics.fabflixmobile;

public class Movie {
    private String name;
    private Integer birthYear;

    public Movie(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }
}
