package com.geekster.Recipe_management_system_API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @NotBlank
    @Column(nullable = false)
    private String recipeName;

    @NotBlank
    @Column(nullable = false, length = 2000)
    private String ingredients;

    @NotBlank
    @Column(nullable = false, length = 5000)
    private String instructions;

    private int cookingTimeInMinutes;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "recipe")
    @JsonIgnore
    private List<Comment> comments;

}
