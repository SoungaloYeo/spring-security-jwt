package securitypoc.service.impl;

import javax.servlet.http.HttpServletRequest;

import securitypoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import securitypoc.exception.CustomException;
import securitypoc.domain.User;
import securitypoc.repository.UserRepository;
import securitypoc.security.JwtTokenProvider;

import static securitypoc.config.Constants.*;

@Service
public class UserServiceImpl implements UserService {


  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;


  @Override
  public String signin(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
    } catch (AuthenticationException e) {
      throw new CustomException(INVALID_USERNAME_PASSWORD_SUPPLIED, HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }


  @Override
  public String signup(User user) {
    if (!userRepository.existsByUsername(user.getUsername())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    } else {
      throw new CustomException(USERNAME_IS_ALREADY_IN_USE, HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }


  @Override
  public void delete(String username) {
    userRepository.deleteByUsername(username);
  }

  @Override
  public User search(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new CustomException(THE_USER_DOESN_T_EXIST, HttpStatus.NOT_FOUND);
    }
    return user;
  }


  @Override
  public User whoami(HttpServletRequest req) {
    return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }


  @Override
  public String refresh(String username) {
    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
  }

}
