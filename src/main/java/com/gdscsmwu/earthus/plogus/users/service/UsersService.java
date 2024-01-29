package com.gdscsmwu.earthus.plogus.users.service;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.domain.UsersRole;
import com.gdscsmwu.earthus.plogus.users.dto.*;
import com.gdscsmwu.earthus.plogus.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원 가입 : username, password, email
    @Transactional
    public Users join(JoinRequestDto joinRequestDto) {

        Users newUser = Users.builder()
                .username(joinRequestDto.getUsername())
                .password(bCryptPasswordEncoder.encode(joinRequestDto.getPassword()))
                .email(joinRequestDto.getEmail())
                .role(UsersRole.ROLE_USER)
                .build();

        return usersRepository.save(newUser);

    }

    // 로그인 : email, password
    @Transactional
    public Users login(LoginRequestDto loginRequestDto) {

        Users users = usersRepository.findByEmail(loginRequestDto.getEmail())
                .orElse(null);

        // email X 또는 password 일치 X
        if (users == null || !bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), users.getPassword())) {
            return null;
        }

        // email & password 일치
        return users;

    }

    // 메인페이지 : username 조회
    @Transactional
    public UsernameResponseDto findByUserId(Long userUuid) {

        Users entity = usersRepository.findByUserUuid(userUuid);

        return new UsernameResponseDto(entity);

    }



    // 마이페이지 : username, userprofile, ploggingStart, totalPloggingScore, totalQuizScore 조회
    @Transactional
    public MypageResponseDto viewMypage(Long userUuid) {

        Users entity = usersRepository.findByUserUuid(userUuid);

        return new MypageResponseDto(entity);

    }



    // password 수정
    @Transactional
    public Long modifyPassword(Long userUuid, String newPassword) {

        Users users = usersRepository.findByUserUuid(userUuid);

        // newPassword 받기
        // setPassword : Users에 @Setter도 필요
        users.modifyPassword(bCryptPasswordEncoder.encode(newPassword));

        return userUuid;

    }

    // username 수정 : 중복 검사
    @Transactional
    public Long modifyUsername(Long userUuid, String newUsername) {

        Users users = usersRepository.findByUserUuid(userUuid);

        users.modifyUsername(newUsername);

        return userUuid;

    }

    // username 중복 검사
    @Transactional
    public boolean checkUsernameDuplicate(String username) {

        return usersRepository.existsByUsername(username);

    }

    // email 중복 검사
    @Transactional
    public boolean checkEmailDuplicate(String email) {

        return usersRepository.existsByEmail(email);

    }

    //////////////////

    // userUuid 사용하여 회원정보 조회
    @Transactional
    public Users findByUserUuid(Long userUuid) {

        return usersRepository.findByUserUuid(userUuid);

    }

    // email 사용하여 password 변경
    @Transactional
    public boolean resetPassword(String email, String newPassword) {

        // email 받기
        Users users = usersRepository.findByEmail(email)
                .orElse(null);

        if (users == null) {
            return false;
        }

        // newPassword 받기
        // setPassword : Users에 @Setter도 필요
        users.setPassword(bCryptPasswordEncoder.encode(newPassword));
        usersRepository.save(users);

        return true;

    }

    // username으로 users 찾기
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found : " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(users.getRole().toString()));

        // org.springframework.security.core.userdetails.User
        return new User(users.getUsername(), users.getPassword(), authorities);

    }

    // 인증, 인가 : userUuid
    @Transactional
    public Users getLoginUserByUserUuid(Long userUuid) {

        return usersRepository.findById(userUuid)
                .orElse(null);

    }

    // 인증, 인가 : userName
    @Transactional
    public Users getLoginUserByUsername(String username) {

        return usersRepository.findByUsername(username)
                .orElse(null);

    }

    // username 사용하여 email 찾기
    @Transactional
    public String findEmail(String username) {

        Users users = usersRepository.findByUsername(username)
                .orElse(null);

        return users != null ? users.getUsername() : null;

    }



}
