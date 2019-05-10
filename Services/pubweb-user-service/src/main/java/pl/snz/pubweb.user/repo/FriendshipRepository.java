package pl.snz.pubweb.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.snz.pubweb.user.model.friend.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long>, JpaSpecificationExecutor<Friendship> {
    @Query("select f from Friendship f where f.user.id = :id or f.user2.id = :id")
    List<Friendship> getForUser(@Param("id") Long id);

    @Query("select f from Friendship f where (f.user.id = :u1 and f.user2.id = :u2) or (f.user.id = :u2 and f.user2.id = :u1)  ")
    Optional<Friendship> findBetween(@Param("u1") Long u1, @Param("u2") Long u2);

}
