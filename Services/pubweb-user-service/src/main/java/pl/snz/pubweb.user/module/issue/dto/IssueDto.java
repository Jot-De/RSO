package pl.snz.pubweb.user.module.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.user.module.issue.model.IssueStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {

    private Long id;
    private Long senderId;
    private String content;
    private LocalDateTime sent;
    private IssueStatus status;
}
