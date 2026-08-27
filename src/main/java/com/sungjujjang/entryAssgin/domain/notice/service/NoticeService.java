package com.sungjujjang.entryAssgin.domain.notice.service;

import com.sungjujjang.entryAssgin.domain.auth.entity.Member;
import com.sungjujjang.entryAssgin.domain.auth.repository.MemberRepository;
import com.sungjujjang.entryAssgin.domain.notice.dto.FileDto;
import com.sungjujjang.entryAssgin.domain.notice.dto.FileUploadResponse;
import com.sungjujjang.entryAssgin.domain.notice.dto.NoticeCreateRequest;
import com.sungjujjang.entryAssgin.domain.notice.dto.NoticeDto;
import com.sungjujjang.entryAssgin.domain.notice.dto.CommentResponse;
import com.sungjujjang.entryAssgin.domain.notice.entity.Category;
import com.sungjujjang.entryAssgin.domain.notice.entity.File;
import com.sungjujjang.entryAssgin.domain.notice.entity.Notice;
import com.sungjujjang.entryAssgin.domain.notice.entity.NoticeLike;
import com.sungjujjang.entryAssgin.domain.notice.repository.CategoryRepository;
import com.sungjujjang.entryAssgin.domain.notice.repository.FileRepository;
import com.sungjujjang.entryAssgin.domain.notice.repository.NoticeLikeRepository;
import com.sungjujjang.entryAssgin.domain.notice.repository.NoticeRepository;
import com.sungjujjang.entryAssgin.global.exception.exceptions.*;
import com.sungjujjang.entryAssgin.global.network.ClientIpUtil;
import com.sungjujjang.entryAssgin.global.s3.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sungjujjang.entryAssgin.domain.notice.Notice.noticeViewKey;
import static com.sungjujjang.entryAssgin.domain.notice.Notice.noticeViewTTL;
import static com.sungjujjang.entryAssgin.global.s3.AllowedContentType.isAllowed;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeLikeRepository noticeLikeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ClientIpUtil clientIpUtil;
    private final CommentService commentService;

    @Transactional
    public FileUploadResponse uploadFiles(List<MultipartFile> files, Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

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
                            .author(member)
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
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Long categoryId = noticeCreateRequest.categoryId();
        Category category;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
        } else {
            category = null;
        }

        Notice notice = Notice.builder()
                .title(noticeCreateRequest.title())
                .content(noticeCreateRequest.content())
                .isFixed(noticeCreateRequest.fixed())
                .member(member)
                .category(category)
                .build();

        if (noticeCreateRequest.fixed() && noticeRepository.countByIsFixedTrue() >= 3) {
            throw FixedCountExceededException.EXCEPTION;
        }

        noticeRepository.save(notice);

        List<Long> files = noticeCreateRequest.files();

        if (files != null) {
            for (Long fileId : files) {
                File file = fileRepository.findById(fileId)
                        .orElseThrow(() -> FileNotFoundException.EXCEPTION);

                if (file.getNotice() != null) {
                    throw FileAlreadyExistsException.EXCEPTION;
                }

                file.updateNotice(notice);
            }
        }

        return notice.getId();
    }

    @Transactional
    public Long updateNotice(NoticeCreateRequest noticeCreateRequest, Long memberId, Long noticeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> NoticeNotFoundException.EXCEPTION);

        if (noticeCreateRequest.files() != null) {
            List<File> originalFiles = fileRepository.findFilesByNotice(notice);
            List<Long> originalFilesId = originalFiles.stream()
                    .map(File::getId).toList();

            List<Long> removeFileTarget = new ArrayList<>(originalFilesId);
            removeFileTarget.removeAll(noticeCreateRequest.files());

            List<Long> appendFileTarget = new ArrayList<>(noticeCreateRequest.files());
            appendFileTarget.removeAll(originalFilesId);

            for (Long fileId : appendFileTarget) {
                File file = fileRepository.findById(fileId)
                        .orElseThrow(() -> FileNotFoundException.EXCEPTION);

                if (file.getNotice() != null) {
                    throw FileAlreadyExistsException.EXCEPTION;
                }

                file.updateNotice(notice);

                fileRepository.save(file);
            }

            for (Long fileId : removeFileTarget) {
                File file = fileRepository.findById(fileId)
                        .orElseThrow(() -> FileNotFoundException.EXCEPTION);

                file.updateNotice(null);

                fileRepository.save(file);
            }
        } else {
            List<File> originalFiles = fileRepository.findFilesByNotice(notice);
            for (File file : originalFiles) {
                file.updateNotice(null);
                fileRepository.save(file);
            }
        }

        Long categoryId = noticeCreateRequest.categoryId();
        Category category;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
        } else {
            category = null;
        }

        boolean wasFixedBefore = notice.isFixed();

        notice.updateNotice(
                noticeCreateRequest.title(),
                noticeCreateRequest.content(),
                category,
                noticeCreateRequest.fixed(),
                member
        );

        if (noticeCreateRequest.fixed() && !wasFixedBefore && noticeRepository.countByIsFixedTrue() >= 3) {
            throw FixedCountExceededException.EXCEPTION;
        }

        noticeRepository.save(notice);

        return notice.getId();
    }

    @Transactional
    public NoticeDto getNotice(Long memberId, Long noticeId, HttpServletRequest request) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> NoticeNotFoundException.EXCEPTION);

        if (memberId == null || !memberRepository.existsById(memberId)) {
            String ip = clientIpUtil.getClientIp(request);
            System.out.println(ip);
            addViewIp(ip, noticeId);
        } else {
            addViewMember(memberId, noticeId);
        }

        List<CommentResponse> comments = commentService.getComments(noticeId);

        return NoticeDto.from(notice, comments);
    }

    @Transactional
    public boolean updateLike(Long memberId, Long noticeId) {
        if (!memberRepository.existsById(memberId)) {
            throw UserNotFoundException.EXCEPTION;
        }
        if (!noticeRepository.existsById(noticeId)) {
            throw NoticeNotFoundException.EXCEPTION;
        }

        Optional<NoticeLike> noticeLike = noticeLikeRepository.findByIdMemberIdAndIdNoticeId(memberId, noticeId);

        if (noticeLike.isPresent()) {
            noticeLikeRepository.delete(noticeLike.get());
            noticeRepository.decreaseLikeCount(noticeId);
            return false;
        } else {
            NoticeLike newNoticeLike = new NoticeLike(memberId, noticeId);
            noticeLikeRepository.save(newNoticeLike);
            noticeRepository.increaseLikeCount(noticeId);
            return true;
        }
    }

    public void addViewMember(Long memberId, Long noticeId) {
        String key = noticeViewKey + memberId + ":" + noticeId;
        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            noticeRepository.increaseViewCount(noticeId);

            redisTemplate.opsForValue().set(
                    key,
                    "1",
                    noticeViewTTL
            );
        }
    }

    public void addViewIp(String ip, Long noticeId) {
        String key = noticeViewKey + ip + ":" + noticeId;
        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            noticeRepository.increaseViewCount(noticeId);

            redisTemplate.opsForValue().set(
                    key,
                    "1",
                    noticeViewTTL
            );
        }
    }
}
