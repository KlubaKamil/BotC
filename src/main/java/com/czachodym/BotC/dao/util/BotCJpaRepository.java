package com.czachodym.BotC.dao.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BotCJpaRepository<T, ID> extends JpaRepository<T, ID> {
    List<T> findByGroups_Id(long groupId);
    Optional<T> findByIdAndGroups_Id(long id, long groupId);
    List<T> findByIdInAndGroups_Id(List<Long> ids, long groupId);
}
