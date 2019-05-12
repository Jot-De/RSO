package pl.snz.pubweb.user.module.permission_acceptance;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PermissionAcceptanceRepository extends JpaRepository<UserPermissionAcceptance, Long>, JpaSpecificationExecutor<UserPermissionAcceptance> {

    @Query("select upa from UserPermissionAcceptance upa where upa.user.id = :userId and upa.permission.id = :permId")
    Optional<UserPermissionAcceptance> findByUserAndPerm(@Param("userId") Long userId, @Param("permId") Long permId);

    @Query("select upa from UserPermissionAcceptance upa where upa.user.id = :userId")
    List<UserPermissionAcceptance> findByUser(@Param("userId") Long userId);
}
