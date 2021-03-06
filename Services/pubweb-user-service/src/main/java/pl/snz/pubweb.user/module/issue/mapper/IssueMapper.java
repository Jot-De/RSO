package pl.snz.pubweb.user.module.issue.mapper;

import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.issue.dto.IssueDto;
import pl.snz.pubweb.user.module.issue.model.Issue;

@Service
public class IssueMapper {

    public IssueDto toDto(Issue issue) {
        return IssueDto.builder()
                .id(issue.getId())
                .content(issue.getContent())
                .senderId(issue.getSender().getId())
                .sent(issue.getSent())
                .status(issue.getStatus())
                .build();
    }

}
