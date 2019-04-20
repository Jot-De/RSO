package pl.snz.pubweb.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.user.model.display.DisplayLevel;

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
}
