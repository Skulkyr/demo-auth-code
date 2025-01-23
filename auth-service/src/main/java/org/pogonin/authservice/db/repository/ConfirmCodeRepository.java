package org.pogonin.authservice.db.repository;

import org.pogonin.authservice.db.entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmCodeRepository extends JpaRepository<ConfirmationCode, Long> {
    @Query("select c from ConfirmationCode c where c.user.email = ?1 and c.code = ?2")
    Optional<ConfirmationCode> findByUserEmailAndCode(String email, String code);
}
