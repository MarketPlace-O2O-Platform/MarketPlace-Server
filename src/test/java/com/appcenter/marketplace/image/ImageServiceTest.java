package com.appcenter.marketplace.image;

import com.appcenter.marketplace.domain.image.ImageRepository;
import com.appcenter.marketplace.domain.image.service.impl.ImageServiceImpl;
import com.appcenter.marketplace.domain.market.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @InjectMocks
    ImageServiceImpl imageService;

    @Mock
    ImageRepository imageRepository;

    @Mock
    private Market market;

    @TempDir
    Path tempDir;

    private String uploadFolder;

    @BeforeEach
    void setUp() {
        uploadFolder = tempDir.toString() + "/";
        ReflectionTestUtils.setField(imageService, "uploadFolder", uploadFolder);
    }

    @Test
    @DisplayName("이미지 저장 테스트")
    public void saveImageListTest() throws Exception{
        // given
        List<MultipartFile> multipartFileList = new ArrayList<>();
        MockMultipartFile file1 = new MockMultipartFile("image1", "test1.jpg", "image/jpeg", "dummy".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("image2", "test2.jpg", "image/jpeg", "dummy".getBytes());
        multipartFileList.add(file1);
        multipartFileList.add(file2);

        // Spy를 사용하여 실제 메서드 호출을 추적
        MockMultipartFile spyFile1 = Mockito.spy(file1);
        MockMultipartFile spyFile2 = Mockito.spy(file2);

        // when
        imageService.createImage(market, List.of(spyFile1, spyFile2));

        // then
        // imageRepository가 save 메서드를 2번 호출되었는지 검증
        verify(imageRepository, times(2)).save(any());

        // transferTo가 실제로 호출되었는지 확인 (파일이 실제로 저장되진 않지만, 이 메서드가 호출됐는지 검증)
        verify(spyFile1, times(1)).transferTo(any(File.class));
        verify(spyFile2, times(1)).transferTo(any(File.class));

        // 첫 번째 파일명을 정확하게 검증
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(market, times(1)).updateThumbnailPath(captor.capture());
        String capturedThumbnailPath = captor.getValue();
        assertTrue(capturedThumbnailPath.contains("test1.jpg"), "첫 번째 이미지가 썸네일로 설정되어야 합니다.");
    }


}
