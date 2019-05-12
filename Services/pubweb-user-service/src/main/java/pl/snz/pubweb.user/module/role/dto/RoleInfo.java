package pl.snz.pubweb.user.module.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class RoleInfo {
    private long id;
    private String name;
}
