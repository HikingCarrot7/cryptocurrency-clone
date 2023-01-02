package com.cherrysoft.cryptocurrency.repository;

import com.cherrysoft.cryptocurrency.model.CryptoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoUserRepository extends JpaRepository<CryptoUser, Long> {

  Optional<CryptoUser> getCryptoUserByUsername(String username);

  boolean existsByUsername(String username);

}
