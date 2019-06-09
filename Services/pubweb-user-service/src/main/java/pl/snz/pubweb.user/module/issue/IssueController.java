package pl.snz.pubweb.user.module.issue;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.util.Just;
import pl.snz.pubweb.user.module.issue.dto.AddIssueCommentRequest;
import pl.snz.pubweb.user.module.issue.dto.AddIssueRequest;
import pl.snz.pubweb.user.module.issue.dto.CommentDto;
import pl.snz.pubweb.user.module.issue.dto.IssueDto;
import pl.snz.pubweb.user.module.issue.mapper.CommentMapper;
import pl.snz.pubweb.user.module.issue.mapper.IssueMapper;
import pl.snz.pubweb.user.module.issue.model.*;
import pl.snz.pubweb.user.module.user.UserRepository;
import pl.snz.pubweb.user.security.AdminApi;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;
import pl.snz.pubweb.user.security.SecurityService;
import pl.snz.pubweb.user.security.UserPrincipal;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("issue")
@RestController
@RequiredArgsConstructor
public class IssueController {

    private final IssueSpecifications issueSpecifications;
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    private final RequestSecurityContextProvider requestSecurityContextProvider;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @GetMapping
    public Page<IssueDto> browseIssues(@RequestParam(required = false, defaultValue = "0") int page,
                             @RequestParam(required = false, defaultValue = "20") int size,
                             @RequestParam(required = false) LocalDateTime since,
                             @RequestParam(required = false) Long sender,
                             @RequestParam(required = false) List<IssueStatus> statuses) {

        final UserPrincipal principal = requestSecurityContextProvider.getPrincipal();
        if(!principal.isAdmin()) sender = principal.getId();

        return issueRepository.findAll(issueSpecifications.search(sender, since, statuses),
                PageRequest.of(page, size, Sort.by(Issue_.SENT).descending()))
                .map(issueMapper::toDto);
    }

    @AdminApi
    @GetMapping("{id}")
    public IssueDto getOneIssue(@PathVariable Long id) {
        final Issue issue = issueRepository.findOrThrow(id);
        securityService.requireSelfOdAdmin(id);

        return Just.of(issue).map(issueMapper::toDto).val();
    }

    @PostMapping
    public IssueDto addIssue(@RequestBody AddIssueRequest request) {
        Issue issue = Issue.builder()
                .content(request.getContent())
                .sender(userRepository.getOne(requestSecurityContextProvider.principalId()))
                .sent(LocalDateTime.now())
                .status(IssueStatus.PENDING)
                .build();

        return Just.of(issue).map(issueRepository::save).map(issueMapper::toDto).val();
    }

    @GetMapping("/{issueId}/comments")
    public Page<CommentDto> getComments(@RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "20") int size,
                                        @PathVariable Long issueId) {

        Issue issue = issueRepository.findOrThrow(issueId);
        securityService.requireSelfOdAdmin(issue.getSender().getId());

        return commentRepository.findByIssueId(issueId, PageRequest.of(page, size, Sort.by(Comment_.CREATE_DATE).descending()))
                .map(commentMapper::toDto);
    }

    @PostMapping("/{id}/comments")
    public CommentDto commentOnIssue(@PathVariable Long id, @RequestBody AddIssueCommentRequest request) {
        Issue issue = issueRepository.findOrThrow(id);
        securityService.requireSelfOdAdmin(issue.getSender().getId());
        final Long userId = requestSecurityContextProvider.getPrincipal().getId();

        Comment comment = new Comment();
        comment.setAuthorId(userId);
        comment.setContent(request.getComment());
        comment.setCreateDate(LocalDateTime.now());
        comment.setIssue(issue);

        issue.getComments().add(comment);
        issueRepository.save(issue);

        return commentMapper.toDto(comment);
    }

    @AdminApi
    @PostMapping("/{id}/accept")
    public IssueDto accept(@PathVariable Long id) {
        final Issue issue = issueRepository.findOrThrow(id);
        issue.setStatus(IssueStatus.RESOLVED);

        return Just.of(issue).map(issueRepository::save).map(issueMapper::toDto).val();
    }

    @AdminApi
    @PostMapping("/{id}/reject")
    public IssueDto reject(@PathVariable Long id) {
        final Issue issue = issueRepository.findOrThrow(id);
        issue.setStatus(IssueStatus.CANCELLED);

        return Just.of(issue).map(issueRepository::save).map(issueMapper::toDto).val();
    }

}
