package com.sungjujjang.entryAssgin.domain.notice.service;

import com.sungjujjang.entryAssgin.domain.auth.entity.Member;
import com.sungjujjang.entryAssgin.domain.auth.repository.MemberRepository;
import com.sungjujjang.entryAssgin.domain.notice.dto.CommentCreateRequest;
import com.sungjujjang.entryAssgin.domain.notice.dto.CommentResponse;
import com.sungjujjang.entryAssgin.domain.notice.entity.Comment;
import com.sungjujjang.entryAssgin.domain.notice.entity.Notice;
import com.sungjujjang.entryAssgin.domain.notice.repository.CommentRepository;
import com.sungjujjang.entryAssgin.domain.notice.repository.NoticeRepository;
import com.sungjujjang.entryAssgin.global.exception.exceptions.NoticeNotFoundException;
import com.sungjujjang.entryAssgin.global.exception.exceptions.UserNotFoundException;
import com.sungjujjang.entryAssgin.global.exception.exceptions.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createComment(Long noticeId, Long memberId, CommentCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> NoticeNotFoundException.EXCEPTION);

        Comment comment = Comment.builder()
                .content(request.content())
                .member(member)
                .notice(notice)
                .build();

        commentRepository.save(comment);

        return comment.getId();
    }

    @Transactional
    public void updateComment(Long commentId, Long memberId, CommentCreateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.EXCEPTION);

        if (comment.getMember().getId() != memberId) {
            throw com.sungjujjang.entryAssgin.global.exception.exceptions.ForbiddenException.EXCEPTION;
        }

        comment.updateContent(request.content());
    }

    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.EXCEPTION);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        boolean isOwner = comment.getMember().getId() == memberId;
        boolean isAdmin = member.getRole() == com.sungjujjang.entryAssgin.domain.auth.enums.Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw com.sungjujjang.entryAssgin.global.exception.exceptions.ForbiddenException.EXCEPTION;
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long noticeId) {
        return commentRepository.findByNoticeId(noticeId).stream()
                .map(CommentResponse::from)
                .toList();
    }
}
