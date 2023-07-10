package com.ukids.todo.service;

import com.ukids.todo.dto.request.LoginRequestDto;
import com.ukids.todo.dto.request.SignupRequestDto;
import com.ukids.todo.entity.Authority;
import com.ukids.todo.entity.Member;
import com.ukids.todo.jwt.TokenProvider;
import com.ukids.todo.repository.MemberRepository;
import com.ukids.todo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequestDto loginDto) {

        if (!memberRepository.existsByUsername(loginDto.getUsername())) {
            throw new RuntimeException("가입되지 않은 유저입니다.");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication);
    }

    public Member signup(SignupRequestDto signupDto) {

        if (memberRepository.existsByUsername(signupDto.getUsername())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Member member = Member.builder()
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .name(signupDto.getName())
                .authorities(Collections.singleton(Authority.ROLE_USER))
                .build();

        return memberRepository.save(member);
    }

    public Member getCurrentUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new IllegalArgumentException("현재 사용자가 없습니다."));
    }

}
