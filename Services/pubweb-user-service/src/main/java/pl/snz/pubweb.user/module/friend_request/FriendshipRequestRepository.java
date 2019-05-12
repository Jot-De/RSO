package pl.snz.pubweb.user.module.friend_request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.snz.pubweb.user.module.friend_request.model.FriendshipRequest;

import java.util.List;
import java.util.Optional;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long>, JpaSpecificationExecutor<FriendshipRequest> {

    @Query("select fr from FriendshipRequest fr where (fr.target.id = :userId or fr.requester.id = :userId) " +
            "and fr.friendRequestStatus = 'PENDING'")
    List<FriendshipRequest> getAllPending(@Param("userId") Long userId, Pageable pageable);

    @Query("select fr from FriendshipRequest fr where fr.requester.id = :userId and fr.friendRequestStatus = 'PENDING'")
    List<FriendshipRequest> getAllSent(@Param("userId") Long userId, Pageable pageable);

    @Query("select fr from FriendshipRequest fr where fr.target.id = :userId and fr.friendRequestStatus = 'PENDING'")
    List<FriendshipRequest> getAllReceived(@Param("userId") Long userId, Pageable pageable);

    @Query("select fr from FriendshipRequest fr where fr.requester.id = :requesterId and fr.target.id = :targetId")
    Optional<FriendshipRequest> findByUsers(@Param("requesterId") Long requesterId, @Param("targetId") Long targetId);
}
