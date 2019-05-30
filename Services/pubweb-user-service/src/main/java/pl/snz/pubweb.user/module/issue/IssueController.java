package pl.snz.pubweb.user.module.issue;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.commons.util.Just;
import pl.snz.pubweb.user.module.issue.dto.AddIssueRequest;
import pl.snz.pubweb.user.module.issue.dto.IssueDto;
import pl.snz.pubweb.user.module.issue.model.Issue;
import pl.snz.pubweb.user.module.issue.model.IssueStatus;
import pl.snz.pubweb.user.module.user.UserRepository;
import pl.snz.pubweb.user.security.AdminApi;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;

import java.time.LocalDateTime;

@RequestMapping("issue")
@RestController
@RequiredArgsConstructor
public class IssueController {

    private final IssueSpecifications issueSpecifications;
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    private final RequestSecurityContextProvider requestSecurityContextProvider;
    private final UserRepository userRepository;

    @AdminApi
    @GetMapping
    public Page<IssueDto> getIssues(@RequestParam(required = false, defaultValue = "0") int page,
                             @RequestParam(required = false, defaultValue = "20") int size,
                             @RequestParam(required = false) LocalDateTime since,
                             @RequestParam(required = false) Long sender,
                             @RequestParam(required = false) IssueStatus status) {

        return issueRepository.findAll(issueSpecifications.search(sender, since, status), PageRequest.of(page, size))
                .map(issueMapper::toDto);
    }

    @AdminApi
    @GetMapping("{id}")
    public IssueDto getOneIssue(@PathVariable Long id) {
        return issueRepository.findById(id).map(issueMapper::toDto).orElseThrow(NotFoundException.ofMessage("issue.not.found", "id", id));
    }

    @PostMapping
    public IssueDto addIssue(@RequestBody AddIssueRequest request) {
        Issue issue = Issue.builder()
                .content(request.getContent())
                .sender(userRepository.getOne(requestSecurityContextProvider.principalId()))
                .sent(LocalDateTime.now())
                .build();

        return Just.of(issue).map(issueRepository::save).map(issueMapper::toDto).val();
    }

    @AdminApi
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        issueRepository.findById(id).ifPresent(issueRepository::delete);
        return ResponseEntity.ok().build();
    }

}
