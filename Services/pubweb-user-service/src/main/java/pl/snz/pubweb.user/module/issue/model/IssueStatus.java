package pl.snz.pubweb.user.module.issue.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum IssueStatus {

    PENDING("pending"),
    RESOLVED("resolved"),
    CANCELLED("cancelled");

    @Getter(onMethod = @__(@JsonValue))
    private String value;



}
