package pl.snz.pubweb.user.module.issue.mapper;

import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.issue.dto.CommentDto;
import pl.snz.pubweb.user.module.issue.model.Comment;

@Service
public class CommentMapper {

    public CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .added(comment.getCreateDate())
                .authorId(comment.getAuthorId())
                .content(comment.getContent())
                .issueId(comment.getIssue().getId())
                .build();
    }
}
