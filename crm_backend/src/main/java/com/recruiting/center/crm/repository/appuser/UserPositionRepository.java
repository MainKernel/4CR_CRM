package com.recruiting.center.crm.repository.appuser;

import com.recruiting.center.crm.entity.appuser.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPositionRepository extends JpaRepository<UserPosition, Long> {
}
