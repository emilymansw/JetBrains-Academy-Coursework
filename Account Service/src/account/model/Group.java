package account.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "principle_groups")
public class Group{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    public Group() {
    }

    public Group(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return code;
    }

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER )
    private Set<User> users;
}
