package br.com.jogos.api.security;

import br.com.jogos.domain.User;
import br.com.jogos.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;



@Service(value = "userDetailsService") // eu sou responsavel por criar e determinar qual o nivel de segurança dos usuarios
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRep;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRep.findByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException("usuario nao encontrado");
			}
		
			return user;


	}

}
