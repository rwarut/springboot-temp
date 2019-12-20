package cat.iot.auth.db.domain

import static javax.persistence.GenerationType.IDENTITY

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(schema = "DBO", name = "IOT_USER_ROLE")
class UserRole {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	Long id
	String username
	String role
}
