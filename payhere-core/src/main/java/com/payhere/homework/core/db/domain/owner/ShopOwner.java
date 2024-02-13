package com.payhere.homework.core.db.domain.owner;

import com.payhere.homework.core.db.audit.AuditingDomain;
import com.payhere.homework.core.db.domain.Phone;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopOwner extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Phone shopOwnerPhone;

    @Column
    private String password;

}
