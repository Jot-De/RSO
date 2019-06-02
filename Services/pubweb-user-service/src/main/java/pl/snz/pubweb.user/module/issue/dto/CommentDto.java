package pl.snz.pubweb.user.module.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime added;
    private Long authorId;
    private Long issueId;
}
