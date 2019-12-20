package cat.iot.auth.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.iot.auth.db.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByUsername(String u);
}
