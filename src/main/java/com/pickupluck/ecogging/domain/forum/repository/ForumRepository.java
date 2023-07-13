package com.pickupluck.ecogging.domain.forum.repository;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.entity.QForum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum,Long> {

}
