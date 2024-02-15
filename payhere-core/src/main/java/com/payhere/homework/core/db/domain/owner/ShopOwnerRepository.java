package com.payhere.homework.core.db.domain.owner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopOwnerRepository extends JpaRepository<ShopOwner, Long> {

    Optional<ShopOwner> findByPhoneNumberAndPassword(String phoneNumber, String password);

    Optional<ShopOwner> findByPhoneNumber(String phoneNumber);

}
