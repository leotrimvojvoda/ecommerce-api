package com.vojvoda.ecommerceapi.core.user;

import com.vojvoda.ecommerceapi.configurations.security.authority.Authority;
import com.vojvoda.ecommerceapi.core.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * The User entity is used by Spring Security for authentication and authorization.
 * This object extends the UserDetails that is required from Spring Security and
 * overrides all the mandatory methods.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id",
            nullable = false)
    )
    private Set<Authority> authorities;

    public User() {
    }


}
