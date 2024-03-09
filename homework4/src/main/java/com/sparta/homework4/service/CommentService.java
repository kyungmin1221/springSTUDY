package com.sparta.homework4.service;


import com.sparta.homework4.domain.CommentEntity;
import com.sparta.homework4.domain.CourseEntity;
import com.sparta.homework4.domain.UserEntity;
import com.sparta.homework4.dto.CommentDto;
import com.sparta.homework4.exception.CustomException;
import com.sparta.homework4.exception.ErrorCode;
import com.sparta.homework4.repository.CommentRepository;
import com.sparta.homework4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.PanelUI;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CourseService courseService;
    private final UserRepository userRepository;
    private final UserService userService;

    // 선택한 강의 댓글 등록
    @Transactional
    public CommentDto.CommentResponseDto createComment(Long userId, Long courseId, CommentDto.CommentRequestDto requestDto) {
        CourseEntity course = courseService.findCourseById(courseId);
        UserEntity user = userService.findUserById(userId);

        CommentEntity comment = new CommentEntity();
        comment.setContent(requestDto.getContent());
        comment.setUser(user);
        comment.setCourse(course);
        commentRepository.save(comment);

        return new CommentDto.CommentResponseDto(comment);

    }

    // 선택한 강의 댓글 수정
    @Transactional
    public CommentDto.CommentResponseDto updateComment(Long userId,Long commentId, CommentDto.CommentPatchDto patchDto) {
        UserEntity user = userService.findUserById(userId);
        CommentEntity comment = findCommentById(commentId);
        if(!comment.getUser().getId().equals(user.getId())){
            throw new IllegalStateException("해당 댓글의 작성자가 아닙니다.");
        }
        comment.setContent(patchDto.getContent());
        commentRepository.save(comment);

        return new CommentDto.CommentResponseDto(comment);
    }

    // 선택한 강의 댓글 삭제
    @Transactional
    public String deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return "선택한 댓글이 삭제 되었습니다.";
    }



    public CommentEntity findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
