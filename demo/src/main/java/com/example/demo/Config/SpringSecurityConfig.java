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
               .antMatchers(HttpMethod.GET,"/users/id/{userid}").permitAll()
               .antMatchers(HttpMethod.GET,"/users/username/{username}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.PUT,"/users/{userid}").permitAll()
               .antMatchers(HttpMethod.DELETE,"/users/{userid}").permitAll()
               .antMatchers(HttpMethod.GET,"/users/{userid}/authorities").hasRole("ADMIN")
               .antMatchers(HttpMethod.POST,"/users/{userid}/authorities").hasRole("ADMIN")
               .antMatchers(HttpMethod.DELETE,"/users/{userid}/authorities/{authority}").hasRole("ADMIN")

               .antMatchers(HttpMethod.POST,"/profiles/{profileid}/addProfileImage").permitAll()
               .antMatchers(HttpMethod.GET,"/profiles/download/{profileid}").permitAll()
               .antMatchers(HttpMethod.GET,"/profiles/{profileid}").permitAll()
               .antMatchers(HttpMethod.GET,"/profiles/user/{userid}").permitAll()
               .antMatchers(HttpMethod.GET,"/profiles").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/profiles/profile/{profilename}").permitAll()
               .antMatchers(HttpMethod.DELETE,"/profiles/{profileid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.PUT,"/profiles/{profileid}").permitAll()

               .antMatchers(HttpMethod.POST,"/posts/{profileid}").permitAll()
               .antMatchers(HttpMethod.POST,"/posts/step/{postid}/addPostImageVideo").permitAll()
               .antMatchers(HttpMethod.GET,"/posts/post/{postid}").permitAll()
               .antMatchers(HttpMethod.GET,"/posts/{profileid}").permitAll()
               .antMatchers(HttpMethod.GET,"/posts//downloadpostfile/{postid}").permitAll()
               .antMatchers(HttpMethod.DELETE,"/posts/post/{postid}").permitAll()
               .antMatchers(HttpMethod.PUT,"/posts/post/{postid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")

               .antMatchers(HttpMethod.POST,"/friendrequests/create/{makerid}/{receiverid}").permitAll()
               .antMatchers(HttpMethod.GET,"/friendrequests/{friendrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/friendrequests/profile/{profileid}").permitAll()

               .antMatchers(HttpMethod.DELETE,"/friendrequests/{friendrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.PUT,"/friendrequests/{friendrequestid}").permitAll()
               .antMatchers(HttpMethod.GET,"/friendrequests/profile/{profileid}/friends").permitAll()
               .antMatchers(HttpMethod.DELETE,"/friendrequests/profile/{profileid}/friends/{profilefriendid}").permitAll()

               .antMatchers(HttpMethod.POST,"/followrequests/create/{makerid}/{receiverid}").permitAll()
               .antMatchers(HttpMethod.GET,"/followrequests/{followrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/followrequests/profile/{profileid}").permitAll()
               .antMatchers(HttpMethod.DELETE,"/followrequests/{followrequestid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.PUT,"/followrequests/{followrequestid}").permitAll()
               .antMatchers(HttpMethod.GET,"/followrequests/profile/{profileid}/followers").permitAll()
               .antMatchers(HttpMethod.GET,"/followrequests/profile/{profileid}/followings").permitAll()
               .antMatchers(HttpMethod.DELETE,"/followrequests/profile/{profileid}/followers/{profilefollowerid}").permitAll()
               .antMatchers(HttpMethod.DELETE,"/followrequests/profile/{profileid}/following/{profilefollowingid}").permitAll()

               .antMatchers(HttpMethod.POST,"/comments/post/{postid}/profile/{profileidmaker}").permitAll()
               .antMatchers(HttpMethod.GET,"/comments/{commentid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.GET,"/comments/post/{postid}").hasAnyRole("ROLE_NORMAL_USER","ROLE_CELEB_USER","ROLE_PAGE_ADMIN_USER","ADMIN")
               .antMatchers(HttpMethod.DELETE,"/comments/{commentid}").permitAll()



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