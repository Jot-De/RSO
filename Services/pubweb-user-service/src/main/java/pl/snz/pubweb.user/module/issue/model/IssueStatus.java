package pl.snz.pubweb.user.module.issue.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.snz.pubweb.commons.util.Enums;

@ToString
@AllArgsConstructor
public enum IssueStatus {

    PENDING("pending"),
    RESOLVED("resolved"),
    CANCELLED("cancelled");

    @Getter(onMethod = @__(@JsonValue))
    private String value;

    @JsonCreator
    public static IssueStatus fromValue(String val) {
        return Enums.enumFromFieldValue(IssueStatus.class, IssueStatus::getValue, val).orElseThrow(() -> new EnumConstantNotPresentException(IssueStatus.class, val));
    }

}
