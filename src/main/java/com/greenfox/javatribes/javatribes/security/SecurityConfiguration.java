package com.greenfox.javatribes.javatribes.security;

import com.greenfox.javatribes.javatribes.model.CustomUserDetails;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import com.greenfox.javatribes.javatribes.service.CustomUserDetailsServiceImpl;
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

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    public SecurityConfiguration(PasswordEncoder passwordEncoder, UserDetailsService customUserDetailsService, DataSource dataSource) {
//        this.passwordEncoder = passwordEncoder;
//        this.customUserDetailsService = customUserDetailsService;
//        this.dataSource = dataSource;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
        auth.inMemoryAuthentication().withUser("fooname").password("pass123").roles("USER");

        System.out.println(passwordEncoder.encode("pass123"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").authenticated()
                    .antMatchers("/tribes/**").authenticated()
                    .anyRequest().permitAll()
                .and()
                .formLogin()
                .successForwardUrl("/tribes/")
                .and()
                .csrf().disable();
        http
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
