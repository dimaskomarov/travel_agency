package ua.dima.agency.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class OAuth2Configuration extends ResourceServerConfigurerAdapter {
    private static final String ALL_ROLES = "hasAnyAuthority('ADMIN', 'MANAGER', 'DEFAULT')";
    private static final String ADMIN_ONLY = "hasAnyAuthority('ADMIN')";

    private static final String COMPANIES_PATH = "/companies/*";
    private static final String COUNTRIES_PATH = "/countries/*";
    private static final String TOURS_PATH = "/companies/**/tours";
    private static final String TRAVEL_TYPES_PATH = "/travelTypes/*";

    private final TokenStore tokenStore;

    public OAuth2Configuration(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, COMPANIES_PATH).access(ALL_ROLES)
                .antMatchers(HttpMethod.POST, COMPANIES_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.PUT, COMPANIES_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.DELETE, COMPANIES_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.GET, COUNTRIES_PATH).access(ALL_ROLES)
                .antMatchers(HttpMethod.POST, COUNTRIES_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.PUT, COUNTRIES_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.DELETE, COUNTRIES_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.GET, TOURS_PATH).access(ALL_ROLES)
                .antMatchers(HttpMethod.POST, TOURS_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.PUT, TOURS_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.DELETE, TOURS_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.GET, TRAVEL_TYPES_PATH).access(ALL_ROLES)
                .antMatchers(HttpMethod.POST, TRAVEL_TYPES_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.PUT, TRAVEL_TYPES_PATH).access(ADMIN_ONLY)
                .antMatchers(HttpMethod.DELETE, TRAVEL_TYPES_PATH).access(ADMIN_ONLY)
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }
}
