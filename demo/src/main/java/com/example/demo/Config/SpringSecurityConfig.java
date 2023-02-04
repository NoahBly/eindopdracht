package com.example.demo.Config;

import com.example.demo.Filter.JwtRequestFilter;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  {

    /*autowire customUserDetailService en jwtRequestFilter*/
    CustomUserDetailsService customUserDetailsService;
    JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }


    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .httpBasic().disable()
               .cors().and()
               .authorizeRequests()
               // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .antMatchers("/**").permitAll()
               .antMatchers(HttpMethod.POST, "/users/normal").permitAll()
              // .antMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
               .antMatchers(HttpMethod.POST, "/users/celebrity").permitAll()
               .antMatchers(HttpMethod.POST, "/users/pageadmin").permitAll()
               .antMatchers(HttpMethod.GET,"/users/id/{userid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/users/username/{username}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.PUT,"/users//{userid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.DELETE,"/users//{userid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/users/{userid}/authorities").hasRole("ADMIN")
               .antMatchers(HttpMethod.POST,"/users/{userid}/authorities").hasRole("ADMIN")
               .antMatchers(HttpMethod.DELETE,"/users/{userid}/authorities/{authority}").hasRole("ADMIN")

               .antMatchers(HttpMethod.POST,"/profiles/{profileid}/addProfileImage").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/profiles/{profileid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/profiles/user/{userid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/profiles").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/profiles/profile/{profilename}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.DELETE,"/profiles/{profileid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.PUT,"/profiles/{profileid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")

               .antMatchers(HttpMethod.POST,"/posts/{profileid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.POST,"/posts/{profileid}/addPostImageVideo").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/posts/post/{postid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/posts/{profileid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.DELETE,"/posts/post/{postid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.PUT,"/posts/post/{postid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")

               .antMatchers(HttpMethod.POST,"/friendrequests/create/{makerid}/{receiverid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/friendrequests/{friendrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/friendrequests/profile/{profileid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")

               .antMatchers(HttpMethod.DELETE,"/friendrequests/{friendrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.PUT,"/friendrequests/{friendrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/friendrequests/profile/{profileid}/friends").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.DELETE,"/friendrequests/profile/{profileid}/friends/{profilefriendid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")

               .antMatchers(HttpMethod.POST,"/followrequests/create/{makerid}/{receiverid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/followrequests/{followrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/followrequests/profile/{profileid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.DELETE,"/followrequests/{followrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.PUT,"/followrequests/{followrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/followrequests/profile/{profileid}/followers").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/followrequests/profile/{profileid}/followings").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.DELETE,"/followrequests/profile/{profileid}/followers/{profilefollowerid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.DELETE,"/followrequests/profile/{profileid}/followers/{profilefollowingid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")

               .antMatchers(HttpMethod.POST,"/comments/post/{postid}/profile/{profileidmaker}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/comments/{commentid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/comments/post/{postid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.DELETE,"/comments/{commentid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")



               // Je mag meerdere paths tegelijk definieren
             //  .antMatchers("/cimodules", "/remotecontrollers", "/televisions", "/wallbrackets").hasAnyRole("ADMIN", "USER")
              .antMatchers("/authenticated").authenticated()
               .antMatchers("/authenticate").permitAll()
               .and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
       return http.build();
   }


}