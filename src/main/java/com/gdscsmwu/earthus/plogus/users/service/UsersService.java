package com.gdscsmwu.earthus.plogus.users.service;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.domain.UsersRole;
import com.gdscsmwu.earthus.plogus.users.dto.*;
import com.gdscsmwu.earthus.plogus.users.repository.UsersRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;
    @Value("${spring.cloud.gcp.storage.project-id}")
    private String projectId;

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
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Users users = usersRepository.findByEmail(loginRequestDto.getEmail())
                .orElse(null);

        // email X 또는 password 일치 X
        if (users == null || !bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), users.getPassword())) {
            return null;
        }

        // email & password 일치
        return new LoginResponseDto(users);

    }

    // 메인페이지 : username 조회
    @Transactional
    public UsernameResponseDto findByUserId(Long userUuid) {

        Users entity = usersRepository.findByUserUuid(userUuid);

        return new UsernameResponseDto(entity);

    }

    // 마이페이지 : username, userprofile, ploggingStart, totalPloggingScore, totalQuizScore 조회
    @Transactional(readOnly = true)
    public MypageResponseDto viewMypage(Long userUuid) {

        Users entity = usersRepository.findByUserUuid(userUuid);

        return new MypageResponseDto(entity);

    }

    @Transactional(readOnly = true)
    // 플로깅 리더보드 : username, totalPloggingScore
    public List<LeaderboardPloggingResponseDto> leaderboardPlogging() {

        return usersRepository.findAll().stream()
                .map(LeaderboardPloggingResponseDto::new)
                .sorted(Comparator.comparingInt(LeaderboardPloggingResponseDto::getTotalPloggingScore).reversed())
                .limit(6)
                .collect(Collectors.toList());

    }

    // 퀴즈 리더보드 : username, totalQuizScore
    @Transactional(readOnly = true)
    public List<LeaderboardQuizResponseDto> leaderboardQuiz() {

        return usersRepository.findAll().stream()
                .map(LeaderboardQuizResponseDto::new)
                .sorted(Comparator.comparingInt(LeaderboardQuizResponseDto::getTotalQuizScore).reversed())
                .limit(6)
                .collect(Collectors.toList());

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

    // 프로필 사진 수정 : userProfile, profileUuid
    @Transactional
    public void updateUserProfile(Long userUuid, UserProfileUpdateRequestDto userProfileUpdateRequestDto) throws IOException {

        Users users = usersRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 userUuid가 없습니다. userUuid = " + userUuid));

        // 이미지 업로드
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();

        // Google Cloud Storage에 저장될 파일 이름
        String uuid = UUID.randomUUID().toString();
        // 파일의 형식 ex) JPG
        String ext = userProfileUpdateRequestDto.getUserProfile().getContentType();

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        String imgUrl = "https://storage.googleapis.com/" + bucketName + "/" + uuid;

        // Cloud에 이미지 업로드
        if (userProfileUpdateRequestDto.getUserProfile().isEmpty()) {
            imgUrl = null;
        } else {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                    .setContentType(ext).build();

            Blob blob = storage.create(blobInfo, userProfileUpdateRequestDto.getUserProfile().getInputStream());

            // DB에 반영
            users.updateUserProfile(imgUrl, uuid);
        }

    }

    // 프로필 사진 삭제 : userProfile, profileUuid --> null
    @Transactional
    public void deleteUserProfile(Long userUuid) {

        Users users = usersRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 userUuid가 없습니다. userUuid = " + userUuid));

        Storage storage = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .build()
                .getService();

        Blob blob = storage.get(bucketName, users.getProfileUuid());

        if (blob == null) {

            System.out.println("The object " + users.getProfileUuid() + " wasn't found in " + bucketName);

        }
        else {
            Storage.BlobSourceOption precondition =
                    Storage.BlobSourceOption.generationMatch(blob.getGeneration());

            storage.delete(bucketName, users.getProfileUuid(), precondition);

            usersRepository.deleteUserProfile(userUuid);

            System.out.println("Object " + users.getProfileUuid() + " was deleted from " + bucketName);

        }

    }






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
