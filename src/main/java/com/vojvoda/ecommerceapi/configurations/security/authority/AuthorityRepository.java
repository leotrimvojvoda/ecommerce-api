package com.vojvoda.ecommerceapi.configurations.security.authority;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> getAuthorityByName(AuthorityEnum name);

}