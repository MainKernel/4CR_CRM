package com.recruiting.center.crm.repository.appuser;

import com.recruiting.center.crm.entity.appuser.AppUser;
import com.recruiting.center.crm.entity.appuser.WorkUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    List<AppUser> findByFirstName(String firstName);
    List<AppUser> findBySecondName(String secondName);
    Optional<AppUser> findByUsername(String username);
    List<AppUser> findByWorkUnit(WorkUnit workUnit);
}
