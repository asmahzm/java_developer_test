package com.test.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.test.entities.AccountLoginJwtTable;

@Transactional
@Repository
public interface AccountLoginJwtRepository extends JpaRepository<AccountLoginJwtTable, String> {

	@Query(value = "SELECT * FROM account_login_jwt", nativeQuery = true)
//	List<TracenoRedisTable> getAccountLoginJwtList();
	List<Map<String, Object>> getAccountLoginJwtList();
	
	@Query(value = "SELECT * FROM account_login_jwt WHERE jwt_value = ?1 LIMIT 1", nativeQuery = true)
//	Optional<TracenoRedisTable> getAccountLoginJwt(String jwt_value);
	Map<String, Object> getAccountLoginJwt(String jwt_value);
	
	@Modifying
	@Query(value = "INSERT INTO account_login_jwt (username, password, jwt_value, created_date, created_by) "
			+ "VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	int insertAccountLoginJwt(String username, String password, String jwt_value, String created_date, 
			String created_by);
}
