package com.vojvoda.ecomerceapi.core.base;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This class represents common fields (referred as metadata) that are used across all tables.
 * These fields will save when the column was changed and by whom it was changed.
 * This class is marked with the @{@link MappedSuperclass} meaning that this class itself is not
 * represented in the database, but it is used by other entities to persist the fields that are
 * listed in this class.<br>
 * All the values except for deletedAt are initialized automatically upon creation or modification.
 * The field deletedBy has not been added since it is the same as the updatedBy field because the
 * updatedBy field stores the username of the user that modified it, so to know who deleted the row
 * we can refer to the updatedBy
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

}