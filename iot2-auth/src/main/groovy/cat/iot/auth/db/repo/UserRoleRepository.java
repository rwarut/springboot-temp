package cat.iot.auth.db.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.iot.auth.db.domain.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	List<UserRole> findAllByUsername(String uname);
}
