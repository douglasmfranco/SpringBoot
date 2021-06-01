package br.com.jogos.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // segurança por metodo
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsSerivce;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http 
		.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.httpBasic()
		.and().csrf().disable(); //desabilita para poder da post em novos objetos
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //encripta  a senha dos usuarios
		
		auth.userDetailsService(userDetailsSerivce).passwordEncoder(encoder);
		
		/*
		auth
		.inMemoryAuthentication().passwordEncoder(encoder)
		.withUser("user").password(encoder.encode("user")).roles("USER") //determina o nivel de restrição de acesso
		.and()
		.withUser("admin").password(encoder.encode("admin")).roles("USER","ADMIN");
	*/	
	}
	
	
	
}
