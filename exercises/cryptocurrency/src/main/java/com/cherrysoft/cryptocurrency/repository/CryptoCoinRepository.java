package com.cherrysoft.cryptocurrency.repository;

import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCoinRepository extends JpaRepository<CryptoCoin, String> {
}
