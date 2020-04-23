package ma.eshop.usersapi.config;

import ma.eshop.usersapi.filters.EntryEntryPoint;
import org.elasticsearch.common.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Inject
    private Filter jwtRequestFilter;
    @Inject
    private EntryEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }

            @Override
            public String encode(CharSequence rawPassword) {
                 return rawPassword.toString();
            }
        };
    }

    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("foo").password("bar").roles("");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need to worry about CSRF attacks because the browser is authenticating requests by looking at the authorization header
        httpSecurity
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
                    .antMatchers("/users/login","/users/signOn").permitAll()
                    .antMatchers("**/admin**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and() .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);// Sessions will not be created
        // now we have to use the the jwtRequestFilter before UsernamePasswordAuthenticationFilter that processes authentication form submission
        httpSecurity
                    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
