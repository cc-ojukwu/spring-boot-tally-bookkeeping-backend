package com.chrisojukwu.tallybookkeeping.userauthentication;

import com.chrisojukwu.tallybookkeeping.userauthentication.security.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTFilter jwtFilter;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

//    @Autowired
//    private CustomOAuth2UserService oAuth2UserService;
//
//    @Autowired
//    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> {
                    //auth.antMatchers("/**").hasRole("USER");
                    auth.antMatchers("/user/**").hasAnyRole("USER", "ADMIN");
                    auth.antMatchers("/info/**").hasAnyRole("USER", "ADMIN");
                    auth.antMatchers("/auth/login/**").permitAll();
                    auth.antMatchers("/auth/oauth2/**").permitAll();
                    auth.antMatchers("/register/**").permitAll();
                    auth.antMatchers("/process-register/**").permitAll();
                    auth.antMatchers("/test").permitAll();
                    auth.antMatchers("/").permitAll();
                    auth.anyRequest().authenticated();
                })
                .httpBasic(httpBasic -> httpBasic.disable())
                .userDetailsService(userDetailsServiceImpl)
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(((request, response, authException) ->
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Identify yaself!"))
                                ))
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(formLogin -> {
                    formLogin.usernameParameter("email"); //this enables us to use email instead of username in form data
                    formLogin.defaultSuccessUrl("/user");
                    //formLogin.loginPage("/myCustomLoginPage"); //use custom login page (get method)
                    //formLogin.loginProcessingUrl("/auth/login"); //use custom login endpoint (post method)
                    //formLogin.failureUrl("/loginFailed");
                    //formLogin.successForwardUrl("/");
                    //formLogin.permitAll();
                })
                .logout(logout -> {
                    logout.logoutSuccessUrl("/");
                    logout.permitAll();
                })
//                .oauth2Login(oauth2login -> {
//                    oauth2login.loginPage("/auth/oauth2/login");
//                    oauth2login.userInfoEndpoint().userService(oAuth2UserService);
//                    oauth2login.successHandler(new AuthenticationSuccessHandler() {
//                        @Override
//                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                                            Authentication authentication) throws IOException, ServletException {
//                            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//                            userService.processOAuthPostLogin(oAuth2User.getEmail());
//                            //response.sendRedirect("/user");
//
//                        }
//                    });
//                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}


//? matches one character
//* matches zero or more characters
//** matches zero or more directories in a path
//Examples
//com/t?st.jsp — matches com/test.jsp but also com/tast.jsp or com/txst.jsp
//com/*.jsp — matches all .jsp files in the com directory
//com/**/test.jsp — matches all test.jsp files underneath the com path
//        org/springframework/**/*.jsp — matches all .jsp files underneath the org/springframework path
//        org/**/servlet/bla.jsp — matches org/springframework/servlet/bla.jsp but also org/springframework/testing/servlet/bla.jsp and org/servlet/bla.jsp

