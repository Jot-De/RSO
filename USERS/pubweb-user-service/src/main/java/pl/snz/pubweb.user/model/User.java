package pl.snz.pubweb.user.model;

import lombok.*;
import pl.snz.pubweb.user.model.display.UserDisplaySettings;
import pl.snz.pubweb.user.model.friend.FriendshipRequest;
import pl.snz.pubweb.user.model.permission.UserPermissionAcceptance;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder @NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Entity
@Table(name = "user")
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserPermissionAcceptance> acceptedPermissions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "target", fetch = FetchType.LAZY)
    private Set<FriendshipRequest> receivedFriendshipRequests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requester", fetch = FetchType.LAZY)
    private Set<FriendshipRequest> sentFriendshipRequests;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Avatar avatar;


}
