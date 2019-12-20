package cat.iot.auth.db.domain

import static javax.persistence.GenerationType.IDENTITY

import java.time.LocalDateTime
import java.util.Collection

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.persistence.Transient

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(schema = "DBO", name = "IOT_USER")
class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	Integer id
	String username
	String password
	Boolean enabled
	String email
	String firstName
	String lastName
	LocalDateTime createdDate
	
	@Transient
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>()
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> c) {
		authorities.clear()
		authorities.addAll(c)
	}
	
	public void addAuthority(GrantedAuthority a) {
		authorities.add(a)
	}

	public String getFullName() {
		return firstName + " " + lastName
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

}
