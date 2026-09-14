package com.LibraryProject.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AutitableEntity {
    //---------------------------------------------------------
    // Ye sb Humko likhna nahi Padega Auto Matic aajeyaga

    @CreatedDate
    private LocalDateTime createdate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
