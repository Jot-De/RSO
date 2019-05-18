package pl.snz.pubweb.user.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.snz.pubweb.user.module.role.Role;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.module.permission_acceptance.UserPermissionAcceptance;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor @AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private  Long id;
    private String login;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    private Set<UserPermissionAcceptance> permissions;

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                authorities,
                Collections.unmodifiableSet(user.getAcceptedPermissions())
        );
    }

    public Long getId() {
        return id;
    }

    public Set<UserPermissionAcceptance> getAcceptedPermissions() {
        return permissions;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
