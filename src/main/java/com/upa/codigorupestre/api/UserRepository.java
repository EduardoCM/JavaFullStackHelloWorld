package com.upa.codigorupestre.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	List<UserEntity> findByUserName(String userName);
}
