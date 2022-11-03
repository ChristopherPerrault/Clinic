package org.clinic.project.security;

import org.springframework.security.web.SecurityFilterChain;
import org.clinic.project.service.CustomDoctorDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Order(1)
public class DoctorSecurityConfig {
    @Bean
    public UserDetailsService customerUserDetailsService1() {
        return new CustomDoctorDetailsService();
    }
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder1() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider1() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerUserDetailsService1());
        authProvider.setPasswordEncoder(passwordEncoder1());
 
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider1());

        http.authorizeRequests().antMatchers("/").permitAll();

        http.antMatcher("/doctor/**")
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/doctor/login")
                .usernameParameter("doctorID")
                .loginProcessingUrl("/doctor/login")
                .defaultSuccessUrl("/doctor/homepage")
                .failureUrl("/doctor/login?error")
                .permitAll()
            .and()
                .logout()
                    .logoutUrl("/doctor/logout")
                    .logoutSuccessUrl("/");
 
        return http.build();
    }
}
