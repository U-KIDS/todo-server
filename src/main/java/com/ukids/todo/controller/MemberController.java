package com.ukids.todo.controller;

import com.ukids.todo.dto.request.LoginRequestDto;
import com.ukids.todo.dto.request.SignupRequestDto;
import com.ukids.todo.dto.response.TokenResponseDto;
import com.ukids.todo.jwt.JwtFilter;
import com.ukids.todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDto loginDto) {

        String jwt = memberService.login(loginDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return ResponseEntity.ok().headers(httpHeaders).body(new TokenResponseDto(jwt));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@Valid @RequestBody SignupRequestDto signupDto) {
        return ResponseEntity.ok().body(memberService.signup(signupDto));
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // user, admin 권한을 가진 토큰만 호츨 가능
    public ResponseEntity getMyUserInfo() {
        return ResponseEntity.ok().body(memberService.getCurrentUserWithAuthorities());
    }

}
