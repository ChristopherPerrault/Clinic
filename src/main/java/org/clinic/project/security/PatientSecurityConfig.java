package org.clinic.project.security;

import org.springframework.security.web.SecurityFilterChain;
import org.clinic.project.service.CustomPatientDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Order(2)
public class PatientSecurityConfig {
    @Bean
    public UserDetailsService customerUserDetailsService2() {
        return new CustomPatientDetailsService();
    }
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider2() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerUserDetailsService2());
        authProvider.setPasswordEncoder(passwordEncoder2());
 
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider2());
        http.antMatcher("/patient/**")
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/patient/login")
                .usernameParameter("patientID")
                .loginProcessingUrl("/patient/login")
                .defaultSuccessUrl("/patient/homepage")
                .failureUrl("/patient/login?error")
                .permitAll()
            .and()
                .logout()
                    .logoutUrl("/patient/logout")
                    .logoutSuccessUrl("/");
 
        return http.build();
    }
}
