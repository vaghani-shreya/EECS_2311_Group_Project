package showPage;

import java.util.List;

public class showDetails {
    private String description;
    private List<String> cast;
    private String director;
    private List<String> awards;

    public showDetails(String description, List<String> cast, String director, List<String> awards) {
        this.description = description;
        this.cast = cast;
        this.director = director;
        this.awards = awards;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCast() {
        return cast;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public void removeDescription() {
        this.description = null;
    }

    public void removeCast() {
        this.cast = null;
    }

    public void removeDirector() {
        this.director = null;
    }

    public void removeAwards() {
        this.awards = null;
    }

    public void removeAll() {
        this.description = null;
        this.cast = null;
        this.director = null;
        this.awards = null;
    }


}


