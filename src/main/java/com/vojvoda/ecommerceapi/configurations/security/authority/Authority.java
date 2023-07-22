package com.vojvoda.ecommerceapi.configurations.security.authority;

import com.vojvoda.ecommerceapi.core.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Enumerated(EnumType.STRING)
  private AuthorityEnum name;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "users_authorities",
          joinColumns = @JoinColumn(name = "authority_id"),
          inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users;
}
