package showPage;

import java.util.List;


public class showBasicInfo {
    private int showID;
    private String name;
    private List<String> genres;
    private int rating;
    private int releaseDate;

    public showBasicInfo(int showID, String name, List<String> genres, int rating, int releaseDate) {
        this.showID = showID;
        this.name = name;
        this.genres = genres;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public int getShowID() {
        return showID;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }


    public int getRating() {
        return rating;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void removeShowID() {
        this.showID = 0;
    }

    public void removeName() {
        this.name = null;
    }

    public void removeGenres() {
        this.genres = null;
    }

    public void removeRating() {
        this.rating = 0;
    }

    public void removeReleaseDate() {
        this.releaseDate = 0;
    }

    public void removeAll() {
        this.showID = 0;
        this.name = null;
        this.genres = null;
        this.rating = 0;
        this.releaseDate = 0;
    }
}