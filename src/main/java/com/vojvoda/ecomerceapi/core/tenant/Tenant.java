package com.vojvoda.ecomerceapi.core.tenant;

import java.sql.Timestamp;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tenant")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "domain")
    private String domain;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}