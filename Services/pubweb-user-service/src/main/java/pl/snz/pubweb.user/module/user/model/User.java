package pl.snz.pubweb.user.module.user.model;

import lombok.*;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.user.module.avatar.Avatar;
import pl.snz.pubweb.user.module.friend_request.model.FriendshipRequest;
import pl.snz.pubweb.user.module.permission_acceptance.UserPermissionAcceptance;
import pl.snz.pubweb.user.module.role.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder @NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Entity
@Table(name = "user_data")
public class User extends IdentifiableEntity<Long> {

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 50)
    private String displayName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "upi_id", nullable = false)
    private UserPersonalInformation userPersonalInformation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ups_id", nullable = false)
    private UserDisplaySettings userDisplaySettings;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserPermissionAcceptance> acceptedPermissions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "target", fetch = FetchType.LAZY)
    private Set<FriendshipRequest> receivedFriendshipRequests = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requester", fetch = FetchType.LAZY)
    private Set<FriendshipRequest> sentFriendshipRequests = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Avatar avatar;


}
