package com.example.blog.entity;

import com.example.blog.entity.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"}),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;
    @NotBlank
    @Size(max=20)
    private String password;
    private String phone;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name ="user_role",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Album> albums;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments;
}
