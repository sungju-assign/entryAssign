package com.sungjujjang.entryAssgin.domain.auth.service;

import com.sungjujjang.entryAssgin.domain.auth.dto.LoginRequest;
import com.sungjujjang.entryAssgin.domain.auth.dto.LoginResponse;
import com.sungjujjang.entryAssgin.domain.auth.dto.MeResponse;
import com.sungjujjang.entryAssgin.domain.auth.dto.RegisterRequest;
import com.sungjujjang.entryAssgin.domain.auth.entity.Member;
import com.sungjujjang.entryAssgin.domain.auth.enums.Role;
import com.sungjujjang.entryAssgin.domain.auth.repository.MemberRepository;
import com.sungjujjang.entryAssgin.global.exception.exceptions.AuthInvalidException;
import com.sungjujjang.entryAssgin.global.exception.exceptions.DuplicatedLoginIdException;
import com.sungjujjang.entryAssgin.global.exception.exceptions.NotFoundException;
import com.sungjujjang.entryAssgin.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByUsername(loginRequest.loginId())
                .orElseThrow(() -> AuthInvalidException.EXCEPTION);

        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw AuthInvalidException.EXCEPTION;
        }

        String accessToken = jwtProvider.createAccessToken(member.getId(), String.valueOf(member.getRole()));

        return new LoginResponse(accessToken);
    }

    public void register(RegisterRequest registerRequest) {
        if (memberRepository.existsByUsername(registerRequest.loginId())) {
            throw DuplicatedLoginIdException.EXCEPTION;
        }

        String encodedPassword =
                passwordEncoder.encode(registerRequest.password());


        Member member = Member.builder()
                .username(registerRequest.loginId())
                .role(Role.USER)
                .password(encodedPassword).build();

        memberRepository.save(member);
    }

    public MeResponse me(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.EXCEPTION);

        return MeResponse.from(member);
    }
}
