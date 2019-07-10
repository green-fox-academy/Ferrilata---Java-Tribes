package com.greenfox.javatribes.javatribes.security;

import com.greenfox.javatribes.javatribes.model.CustomUserDetails;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import com.greenfox.javatribes.javatribes.service.CustomUserDetailsServiceImpl;
import com.greenfox.javatribes.javatribes.service.RoleService;
import com.greenfox.javatribes.javatribes.service.UserService;
import com.greenfox.javatribes.javatribes.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DataSource dataSource;

    @Autowired
    RoleService roleService;

    private static String REALM="MY_TEST_REALM";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    public SecurityConfiguration(PasswordEncoder passwordEncoder, UserDetailsService customUserDetailsService, DataSource dataSource) {
//        this.passwordEncoder = passwordEncoder;
//        this.customUserDetailsService = customUserDetailsService;
//        this.dataSource = dataSource;
//    }

//    @Bean
//    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
//        return new CustomBasicAuthenticationEntryPoint();
//    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
        auth.userDetailsService(new UserServiceImpl(userRepository));

//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username,password from users where username=?")
//                .authoritiesByUsernameQuery("select username, role from user_roles where username=?");

//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled"
//                        + " from users where username=?")
//                .authoritiesByUsernameQuery("select username, authority "
//                        + "from authorities where username=?")
//                .passwordEncoder(new BCryptPasswordEncoder());
//        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("pass123")).roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("pass123")).roles("USER");

        String encoded=new BCryptPasswordEncoder().encode("pass123");
        System.out.println(encoded);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/tribes").hasRole("USER")
//                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .and()
//                .httpBasic()
//                .httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.headers().frameOptions().disable();
    }



//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(encoder);
//    }


}
