package cat.iot

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler
import org.springframework.security.oauth2.provider.token.RemoteTokenServices

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(Ordered.HIGHEST_PRECEDENCE)
class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Value('${security.oauth2.resource.service-id}')
	String resourceId = "iotapi";
	@Value('${security.oauth2.resource.token-info-uri}')
	String checkTokenEndpoint
	@Value('${security.oauth2.client.client-id}')
	String clientId
	@Value('${security.oauth2.client.client-secret}')
	String clientSecret
	
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId).stateless(false);
	}

	@Primary
	@Bean
	public RemoteTokenServices tokenService() {
		RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl(checkTokenEndpoint);
		tokenService.setClientId(clientId);
		tokenService.setClientSecret(clientSecret);
		
		return tokenService;
	}
	
	
	
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/auth/token").permitAll()
				.antMatchers("/auth/refresh/**").permitAll()
				.anyRequest().authenticated()
	}
}
