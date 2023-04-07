package com.test.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.test.entities.AccountLoginTable;

@Transactional
@Repository
public interface AccountLoginRepository extends JpaRepository<AccountLoginTable, String> {

	@Query(value = "SELECT * FROM account_login", nativeQuery = true)
//	List<TracenoRedisTable> getAccountLoginList();
	List<Map<String, Object>> getAccountLoginList();
	
	@Query(value = "SELECT * FROM account_login WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	Optional<TracenoRedisTable> getAccountLogin(String username, String password);
	Map<String, Object> getAccountLogin(String username, String password);
}
