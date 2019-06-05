package pl.snz.pubweb.user.module.issue.webSupport;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.user.module.issue.model.IssueStatus;

@Component
public class IssueStatusRequestParamConverter implements Converter<String, IssueStatus> {
    @Override
    public IssueStatus convert(String source) {
        return IssueStatus.fromValue(source.toLowerCase());
    }
}
