package pl.snz.pubweb.user.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.user.module.user.model.DisplayLevel;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class UDisplaySettings {
    @NotNull
    private DisplayLevel nameDisplayLevel;
    @NotNull
    private DisplayLevel surnameDisplayLevel;
    @NotNull
    private DisplayLevel cityDisplayLevel;
    @NotNull
    private DisplayLevel aboutMeDisplayLevel;
}
