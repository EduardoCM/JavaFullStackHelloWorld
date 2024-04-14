package com.upa.codigorupestre.api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	List<UserEntity> findByUserName(String userName);
}
