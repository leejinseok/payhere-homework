package com.payhere.homework.core.db.domain.owner;

import com.payhere.homework.core.db.audit.AuditingDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
        name = "shop_owner",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"phoneNumber"}, name = "shop_owner_uk_1")
        }
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopOwner extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 11)
    private String phoneNumber;

    @Column
    private String password;


}
