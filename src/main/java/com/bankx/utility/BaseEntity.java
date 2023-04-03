package com.bankx.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @CreatedDate
    private LocalDate createdAt;
    @LastModifiedBy
    private LocalDate updatedAt;
    private boolean deleted;
    @org.springframework.data.annotation.Version
    private Long version;
    public BaseEntity(int id, boolean deleted) {
        this.id=id;
        this.deleted = deleted;
    }
}
