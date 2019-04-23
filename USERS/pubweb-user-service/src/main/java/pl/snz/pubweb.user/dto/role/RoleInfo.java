package pl.snz.pubweb.user.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class RoleInfo {
    private long id;
    private String name;
}
