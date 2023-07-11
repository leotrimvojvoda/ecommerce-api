package com.vojvoda.ecomerceapi.core.tenant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findTenantByName(String name);
}