package com.sungjujjang.entryAssgin.domain.notice.service;

import com.sungjujjang.entryAssgin.domain.auth.entity.Member;
import com.sungjujjang.entryAssgin.domain.auth.repository.MemberRepository;
import com.sungjujjang.entryAssgin.domain.notice.dto.FileDto;
import com.sungjujjang.entryAssgin.domain.notice.dto.FileUploadResponse;
import com.sungjujjang.entryAssgin.domain.notice.dto.NoticeCreateRequest;
import com.sungjujjang.entryAssgin.domain.notice.entity.Category;
import com.sungjujjang.entryAssgin.domain.notice.entity.File;
import com.sungjujjang.entryAssgin.domain.notice.entity.Notice;
import com.sungjujjang.entryAssgin.domain.notice.repository.CategoryRepository;
import com.sungjujjang.entryAssgin.domain.notice.repository.FileRepository;
import com.sungjujjang.entryAssgin.global.exception.exceptions.InvaildFileTypeException;
import com.sungjujjang.entryAssgin.global.s3.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sungjujjang.entryAssgin.global.s3.AllowedContentType.isAllowed;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public FileUploadResponse uploadFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            String contentType = file.getContentType();

            if (!isAllowed(contentType)) {
                throw InvaildFileTypeException.EXCEPTION;
            }
        }

        List<File> fileList = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = fileService.upload(file);

            File savedFile = fileRepository.save(
                    File.builder()
                            .objectKey(fileName)
                            .OriginalName(file.getOriginalFilename())
                            .build()
            );

            fileList.add(savedFile);
        }

        List<FileDto> fileDtoList = fileList
                .stream().map(FileDto::from).toList();

        return new FileUploadResponse(fileDtoList);
    }

    @Transactional
    public Long createNotice(NoticeCreateRequest noticeCreateRequest, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> );

        Category category = categoryRepository.findById(noticeCreateRequest.categoryId())
                .orElseThrow(() -> );

        Notice notice = Notice.builder()
                .title(noticeCreateRequest.title())
                .content(noticeCreateRequest.content())
                .isFixed(noticeCreateRequest.fixed())
                .member(member)
                .category(category)
                .build();
    }
}
