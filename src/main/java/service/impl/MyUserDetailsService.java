package service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mapper.UserMapper;

@Service("myUserDetailsService")
public class MyUserDetailsService  implements UserDetailsService{
	@Resource(name = "userMapper")
	private UserMapper userMapper;
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Map user = userMapper.getUser(username);
		if(user == null)
			throw new UsernameNotFoundException("User not Found");
		System.out.println(user);
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(setAuths); 
		User user2 = new User(username, (String)user.get("password"), true, true, true, true, authList);
		System.out.println(user2);
		return user2;
	}

}