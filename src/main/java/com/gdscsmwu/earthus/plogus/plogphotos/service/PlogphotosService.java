package com.gdscsmwu.earthus.plogus.plogphotos.service;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import com.gdscsmwu.earthus.plogus.plogging.repository.PloggingRepository;
import com.gdscsmwu.earthus.plogus.plogphotos.dto.PlogphotoSaveRequestDto;
import com.gdscsmwu.earthus.plogus.plogphotos.repository.PlogphotosRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlogphotosService {

    private final PlogphotosRepository plogphotosRepository;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;
//    @Value("${spring.cloud.gcp.storage.project-id}")
//    private String projectId;

    // 플로깅 사진 업로드 : ploggingUuid, plogphotoUrl, photoUuid
    @Transactional
    public Long uploadPlogphoto(Plogging plogging, PlogphotoSaveRequestDto plogphotoSaveRequestDto) throws IOException {

        // 이미지 업로드
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();

        // Google Cloud Storage에 저장될 파일 이름
        String uuid = UUID.randomUUID().toString();
        // 파일의 형식 ex) JPG
        String ext = plogphotoSaveRequestDto.getPlogphoto().getContentType();

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        String imgUrl = "https://storage.googleapis.com/" + bucketName + "/" + uuid;

        // Cloud에 이미지 업로드
        if (plogphotoSaveRequestDto.getPlogphoto().isEmpty()) {

            imgUrl = null;

            return null;

        } else {

            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                    .setContentType(ext).build();

            Blob blob = storage.create(blobInfo, plogphotoSaveRequestDto.getPlogphoto().getInputStream());

            // DB에 반영
            return plogphotosRepository.save(plogphotoSaveRequestDto.toEntity(plogging, imgUrl, uuid)).getPlogphotoUuid();

        }

    }

}
