package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

	@Query(value = "Select * from Employee e ORDER BY e.id LIMIT :limit OFFSET :offset", nativeQuery = true)
	Optional<List<Employee>> findEmployeesWithLimit(@Param("offset")int offset, @Param("limit")int limit);

}
