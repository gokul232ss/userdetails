package com.fse.userdetails.repository;

import com.fse.userdetails.model.UserDetail;
import com.google.common.base.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetail, Integer> {
    Optional<UserDetail>findByEmail(String email);
}
