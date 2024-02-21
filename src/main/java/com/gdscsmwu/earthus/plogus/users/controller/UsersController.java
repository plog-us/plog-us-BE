package com.gdscsmwu.earthus.plogus.users.controller;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.dto.*;
import com.gdscsmwu.earthus.plogus.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsersController {

    private final UsersService usersService;

//    @GetMapping("/")
//    public String home() {
//
//        return "index";
//
//    }



    // 회원 가입 : username, password, email
    @PostMapping("/join")
    public ResponseEntity<Users> join(@Valid @RequestBody JoinRequestDto joinRequestDto, BindingResult bindingResult) {

        try {
            // username 중복 체크
            if(usersService.checkUsernameDuplicate(joinRequestDto.getUsername())) {
                bindingResult.addError(new FieldError("joinRequestDto", "username", "중복된 닉네임"));
            }

            // email 중복 체크
            if(usersService.checkEmailDuplicate(joinRequestDto.getEmail())) {
                bindingResult.addError(new FieldError("joinRequestDto", "email", "중복된 이메일"));
            }

            // 유효성 검사 실패시 에러
            if(bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }

            Users newUser = usersService.join(joinRequestDto);

            return ResponseEntity.ok(newUser);

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // 로그인 : email, password
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {

        return usersService.login(loginRequestDto);

    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("로그아웃");

    }

    // 메인페이지 : username 조회
    @GetMapping("/mainpage/{userUuid}")
    public UsernameResponseDto findById(@PathVariable Long userUuid) {

        return usersService.findByUserId(userUuid);

    }

    // 마이페이지 : username, userprofile, ploggingStart, totalPloggingScore, totalQuizScore 조회
    @GetMapping("/mypage/{userUuid}")
    public MypageResponseDto viewMypage(@PathVariable Long userUuid) {

        return usersService.viewMypage(userUuid);

    }

    // 플로깅 리더보드 : username, totalPloggingScore
    @GetMapping("/leaderboard/plogging")
    public List<LeaderboardPloggingResponseDto> leaderboardplogging() {

        return usersService.leaderboardPlogging();

    }

    // 퀴즈 리더보드 : username, totalQuizScore
    @GetMapping("/leaderboard/quiz")
    public List<LeaderboardQuizResponseDto> leaderboardQuiz() {

        return usersService.leaderboardQuiz();

    }

    // password 수정
    @PutMapping("/modify/password/{userUuid}")
    public Long modifyPassword(@PathVariable Long userUuid, @RequestBody PasswordUpdateRequestDto passwordUpdateRequestDto) {

        return usersService.modifyPassword(userUuid, passwordUpdateRequestDto.getPassword());

    }

    // username 수정 : 중복 검사
    @PutMapping("/modify/username/{userUuid}")
    public ResponseEntity<Long> modifyUsername(@PathVariable Long userUuid, @RequestBody UsernameUpdateRequestDto usernameUpdateRequestDto, BindingResult bindingResult) {

        try {
            // username 중복 체크
            if(usersService.checkUsernameDuplicate(usernameUpdateRequestDto.getUsername())) {
                bindingResult.addError(new FieldError("usernameUpdateRequestDto", "username", "중복된 닉네임"));
            }

            // 유효성 검사 실패시 에러
            if(bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }

            Long userId = usersService.modifyUsername(userUuid, usernameUpdateRequestDto.getUsername());

            return ResponseEntity.ok(userId);

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // 프로필 사진 수정 : userProfile, profileUuid
    @PatchMapping("/editprofile/{userUuid}")
    public ResponseEntity<Void> updateUserProfile(@PathVariable Long userUuid, UserProfileUpdateRequestDto userProfileUpdateRequestDto) throws IOException {

        usersService.updateUserProfile(userUuid, userProfileUpdateRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    // 프로필 사진 삭제 : userProfile, profileUuid --> null
    @DeleteMapping("/deleteprofile/{userUuid}")
    public void deleteUserProfile(@PathVariable Long userUuid) {

        usersService.deleteUserProfile(userUuid);

    }






    // userUuid 사용하여 회원정보 조회
    @GetMapping("/user/{userUuid}")
    public ResponseEntity<Users> findByUserUuid(@Valid @PathVariable("userUuid") Long userUuid) {

        try {
            Users users = usersService.findByUserUuid(userUuid);
            return ResponseEntity.ok(users);
        }
        // 잘못된 userUuid
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // username 중복 검사
    @PostMapping("/join/username/check")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@RequestBody String username) {

        boolean isDuplicate = usersService.checkUsernameDuplicate(username);

        return ResponseEntity.ok(isDuplicate);

    }

    // email 중복 검사
    @PostMapping("/join/email/check")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestBody String email) {

        boolean isDuplicate = usersService.checkUsernameDuplicate(email);

        return ResponseEntity.ok(isDuplicate);

    }

    // username 사용하여 email 찾기
    @PostMapping("/find/email")
    public ResponseEntity<String> findEmail(@RequestBody FindEmailRequestDto findEmailRequestDto) {

        String email = usersService.findEmail(findEmailRequestDto.getUsername());

        if(email != null) {
            return ResponseEntity.ok(email);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

}
