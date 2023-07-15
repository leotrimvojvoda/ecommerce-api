package com.vojvoda.ecomerceapi.configurations.security.authority;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> getAuthorityByName(AuthorityEnum name);

}