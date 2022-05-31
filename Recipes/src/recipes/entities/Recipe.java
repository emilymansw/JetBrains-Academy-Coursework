package recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    private LocalDateTime date = LocalDateTime.now();

    @NotNull
    @Size(min = 1)
    private CopyOnWriteArrayList<String> ingredients;

    @NotNull
    @Size(min = 1)
    private CopyOnWriteArrayList<String> directions;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CopyOnWriteArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(CopyOnWriteArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public CopyOnWriteArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(CopyOnWriteArrayList<String> directions) {
        this.directions = directions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

