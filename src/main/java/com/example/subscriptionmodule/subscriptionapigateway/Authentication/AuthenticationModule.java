package com.example.subscriptionmodule.subscriptionapigateway.Authentication;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.example.subscriptionmodule.subscriptionapigateway.Models.UserDetailsFromDb;
import com.example.subscriptionmodule.subscriptionapigateway.Repository.UserDetailsDbRepository;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebFluxSecurity
public class AuthenticationModule{

    @Autowired
    private UserDetailsDbRepository userrepo;

    private Collection<UserDetails> details=new ArrayList<UserDetails>();

    @Bean
    public MapReactiveUserDetailsService getManager(){
        System.out.println("---------------------------------------->");
        System.out.println();
        System.out.println("in get manager method mate");
        System.out.println();
        System.out.println("---------------------------------------->");
        

        return new MapReactiveUserDetailsService( getDetails() ); //geting details from database
    }
    
    @Bean
    public SecurityWebFilterChain getFilterChain(ServerHttpSecurity http) throws Exception{
        System.out.println("---------------------------------------->");
        System.out.println();
        System.out.println("in filter chain method mate");
        System.out.println();
        System.out.println("---------------------------------------->");
        return http.authorizeExchange(auth -> auth.pathMatchers("/subscription/**").hasAnyRole("ADMIN","USER").anyExchange().permitAll()
        ).csrf().disable().httpBasic().and().formLogin().and().build();//`.hasAnyRole("ADMIN","USER").anyExchange().authenticated().and().httpBasic().and().formLogin().and().build();
   } 
 
   private Collection<UserDetails> getDetails(){
        
        details.clear();
        Iterable<UserDetailsFromDb> data=userrepo.findAll();
        details=new ArrayList<UserDetails>();
        
            for(UserDetailsFromDb ud:data)
            {
                if(ud!=null && ud.getRole().equals("USER")){
                    UserDetails user=User.withDefaultPasswordEncoder()
                    .username(ud.getUsername())
                    .password(ud.getPassword())
                    .roles(ud.getRole())
                    .build();
                    details.add(user);
                }
                if(ud!=null && ud.getRole().equals("ADMIN")){
                    UserDetails admin=User.withDefaultPasswordEncoder()
                    .username(ud.getUsername())
                    .password(ud.getPassword())
                    .roles(ud.getRole())
                    .build();
                    details.add(admin);
                }
            }
        return details;
    }

    // @Bean
    // PasswordEncoder getEncoderp(){
    //     return new BCryptPasswordEncoder();
    // }

}