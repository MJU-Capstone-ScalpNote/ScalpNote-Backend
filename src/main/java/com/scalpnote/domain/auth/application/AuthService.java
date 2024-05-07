package com.scalpnote.domain.auth.application;

import com.scalpnote.domain.auth.dto.AuthRes;
import com.scalpnote.domain.auth.dto.SignInReq;
import com.scalpnote.domain.auth.dto.SignUpReq;
import com.scalpnote.domain.user.domain.Role;
import com.scalpnote.domain.user.domain.User;
import com.scalpnote.domain.user.domain.repository.UserRepository;
import com.scalpnote.global.DefaultAssert;
import com.scalpnote.global.config.security.token.UserPrincipal;
import com.scalpnote.global.payload.ResponseCustom;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {


    @Value("${app.auth.token-secret}")
    private String key;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Transactional
    public ResponseCustom<?> signUp(SignUpReq signUpReq) {
        DefaultAssert.isTrue(!userRepository.existsByEmail(signUpReq.getEmail()), "해당 이메일이 이미 존재합니다.");

        User user = User.builder().
                email(signUpReq.getEmail())
                .password(signUpReq.getPassword()) // 암호화 작업 추후 추가 .. bcrypt고려 중
                .name(signUpReq.getName())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return ResponseCustom.OK(user.getId());
    }

    @Transactional
    public ResponseCustom<?> signIn(SignInReq signInReq) {
        Optional<User> user = userRepository.findByEmail(signInReq.getEmail());

        DefaultAssert.isTrue(user.isPresent(), "유저가 올바르지 않습니다");

        User findUser = user.get();


//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        signInReq.getEmail(),
//                        signInReq.getPassword()
//                )
//        );
//
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();

        Date accessTokenExpiresIn = new Date(now.getTime() + 300000000);

        String accessToken = Jwts.builder()
                .setSubject(Long.toString(findUser.getId()))
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        AuthRes authRes = AuthRes.builder()
                .accessToken(accessToken)
                .build();

        return ResponseCustom.OK(authRes);
    }
}
