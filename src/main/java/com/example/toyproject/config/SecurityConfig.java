package com.example.toyproject.config;

import com.example.toyproject.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;
        private final DataSource dataSource;
        private final CustomAuthenticationSuccessHandler customSuccessHandler;

        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
                http
                    .rememberMe()
                    .userDetailsService(userDetailsService)
                    .tokenRepository(tokenRepository())
                    .and()
                    .authorizeRequests()
                    .antMatchers("/signup","/login","/shoppingBasket","/topClothes","/topDetail","/shoppingBasket","/","/myPage","/test","/adminPage","/**")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .successHandler(customSuccessHandler)
                    .failureUrl("/fail")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
            ;
        }
        @Bean
        public PersistentTokenRepository tokenRepository() {
                JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
                jdbcTokenRepository.setDataSource(dataSource);
                return jdbcTokenRepository;
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }
        @Override
        public void configure(WebSecurity web) throws Exception {
                web
                        .ignoring()
                        .antMatchers("/templates/**","/static/**","/error");
        }



}
