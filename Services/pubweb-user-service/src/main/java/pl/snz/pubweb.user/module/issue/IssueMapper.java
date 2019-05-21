package pl.snz.pubweb.user.module.issue;

import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.issue.dto.IssueDto;
import pl.snz.pubweb.user.module.issue.model.Issue;

@Service
public class IssueMapper {

    public IssueDto toDto(Issue issue) {
        return IssueDto.builder()
                .content(issue.getContent())
                .senderId(issue.getSender().getId())
                .sent(issue.getSent())
                .build();
    }

}
