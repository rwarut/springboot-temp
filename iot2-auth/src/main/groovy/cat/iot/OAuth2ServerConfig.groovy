package cat.iot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore

import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService
	
	@Autowired
	private AuthenticationManager authenticationManager
	
	@Value('${security.oauth2.client.client-id}')
	String CLIENT_ID;
	@Value('${security.oauth2.client.client-secret}')
	String CLIENT_SECRET;
	static final String GRANT_TYPE = "password";
	static final String AUTHORIZATION_CODE = "authorization_code";
	static final String REFRESH_TOKEN = "refresh_token";
	static final String IMPLICIT = "implicit";
	static final String SCOPE_READ = "read";
	static final String SCOPE_WRITE = "write";
	static final String TRUST = "trust";
	@Value('${security.oauth2.access-token-valid-hours}')
	Integer ACCESS_TOKEN_VALIDITY_HOURS
	@Value('${security.oauth2.refresh-token-valid-hours}')
	Integer REFRESH_TOKEN_VALIDITY_HOURS
//	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 6*60*60;
//	static final int REFRESH_TOKEN_VALIDITY_SECONDS = 24*60*60;

	@Autowired
	DataSource dataSource

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder()
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
		configurer.authenticationManager(authenticationManager)
		configurer.userDetailsService(userDetailsService)
		configurer.tokenStore(tokenStore())
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
		  .tokenKeyAccess("permitAll()")
		  .checkTokenAccess("isAuthenticated()")
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.
			inMemory()
			.withClient(CLIENT_ID)
			.secret(passwordEncoder().encode(CLIENT_SECRET))
			.authorizedGrantTypes(GRANT_TYPE, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
			.scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
			.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_HOURS * 60 * 60)
			.refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_HOURS * 60 * 60)

	}
}
