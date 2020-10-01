package com.innova.controller;

import com.innova.model.Attempt;
import com.innova.model.Role;
import com.innova.model.Roles;
import com.innova.model.User;
import com.innova.message.request.LoginForm;
import com.innova.message.request.SignUpForm;
import com.innova.message.response.JwtResponse;
import com.innova.repository.AttemptRepository;
import com.innova.repository.RoleRepository;
import com.innova.repository.UserRepository;
import com.innova.security.jwt.JwtProvider;

import com.innova.security.services.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AttemptRepository attemptRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm, HttpServletRequest request) {

            Attempt attempt = attemptRepository.findById(request.getRemoteAddr()).orElseThrow(()->
                    new UsernameNotFoundException("User Not Ip addr: " + request.getRemoteAddr()));
            if((attempt.getAttemptCounter() >= 3 && loginForm.getCaptcha() == null)||(attempt.getAttemptCounter() >= 3 && !loginForm.getCaptcha().equals("captcha"))){
                System.out.println(loginForm.getCaptcha());
                return new ResponseEntity<String>("Captcha is needed!", HttpStatus.BAD_REQUEST);
            }
            else{
                System.out.println("I'm in else");
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtProvider.generateJwtToken(authentication);

                UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();

                List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

                attempt.setAttemptCounter(0);

                attemptRepository.save(attempt);

                return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
            }


    }

    @PostMapping("sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpForm) {
        if (userRepository.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<String>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<String>("Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByRole(Roles.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }
}