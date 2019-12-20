package cat.iot.auth.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import cat.iot.auth.db.domain.User
import cat.iot.auth.db.domain.UserRole
import cat.iot.auth.db.repo.UserRepository
import cat.iot.auth.db.repo.UserRoleRepository

@Service("userDetailsService")
class IotUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo
	
	@Autowired
	UserRoleRepository userRoleRepo
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username)
		
		if (user == null) {
			return null;
		}
		
		List<UserRole> roles = userRoleRepo.findAllByUsername(user.username)
		
		for(UserRole r in roles) {
			user.addAuthority(new SimpleGrantedAuthority("ROLE_" + r.role))
		}
		
		return user	
	}

}
