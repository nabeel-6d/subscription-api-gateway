package com.example.subscriptionmodule.subscriptionapigateway;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

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
       return http.authorizeExchange(auth -> auth.pathMatchers(HttpMethod.GET, "/").permitAll()
        .pathMatchers(HttpMethod.GET,"/subscription/subscriber").hasAnyRole("ADMIN","USER")
        .pathMatchers(HttpMethod.GET,"/subscription/plans").hasAnyRole("ADMIN","USER").anyExchange().authenticated()
       ).httpBasic().and().formLogin().and().build();//`.hasAnyRole("ADMIN","USER").anyExchange().authenticated().and().httpBasic().and().formLogin().and().build();
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
                    .roles(ud.getRole().toUpperCase())
                    .build();
                    details.add(user);
                }
                if(ud!=null && ud.getRole().equals("ADMIN")){
                    UserDetails admin=User.withDefaultPasswordEncoder()
                    .username(ud.getUsername())
                    .password(ud.getPassword())
                    .roles(ud.getRole().toUpperCase())
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