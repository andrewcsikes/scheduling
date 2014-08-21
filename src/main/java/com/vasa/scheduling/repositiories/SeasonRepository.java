package com.vasa.scheduling.repositiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.enums.Status;

@Repository
@Transactional(readOnly = true)
public interface SeasonRepository extends JpaRepository<Season, Integer>{

	List<Season> findByStatus(Status status);

}