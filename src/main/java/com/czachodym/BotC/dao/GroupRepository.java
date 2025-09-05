package com.czachodym.BotC.dao;

import com.czachodym.BotC.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
