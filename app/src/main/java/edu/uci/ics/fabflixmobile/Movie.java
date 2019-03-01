package edu.uci.ics.fabflixmobile;

public class Movie {
    private String id;
    private String name;
    private String Year;
    private String directname;
    private String starlist;
    private String genreslist;

    public Movie(String id, String name, String birthYear,String directname, String starlist,String genreslist) {
        this.id=id;
        this.name = name;
        this.Year = birthYear;
        this.directname=directname;
        this.starlist=starlist;
        this.genreslist=genreslist;
    }
    public String getId(){return id;}

    public String getName() {
        return name;
    }

    public String getBirthYear() {
        return Year;
    }

    public String getDirectname(){return directname; }

    public String getStarlist(){return starlist;}

    public String getGenreslist(){return  genreslist;}
}
