package mate.academy.hibernate.relations.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "actor_id", nullable = false)
    )
    private List<Actor> actors = new ArrayList<>(); // ⭐ KLUCZOWE zabezpieczenie

    public Movie() {
        // wymagane przez Hibernate
    }

    public Movie(String title) {
        this.title = title;
        this.actors = new ArrayList<>(); // dodatkowe bezpieczeństwo
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors != null ? actors : new ArrayList<>();
    }

    // 🔥 wygodne metody do pracy z relacją

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    @Override
    public Movie clone() {
        try {
            Movie movie = (Movie) super.clone();
            movie.actors = new ArrayList<>();
            for (Actor actor : this.actors) {
                movie.actors.add(actor.clone());
            }
            return movie;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Can't clone movie " + this, e);
        }
    }

    @Override
    public String toString() {
        return "Movie{"
                + "id=" + id
                + ", title='" + title + '\''
                + '}';
    }
}
