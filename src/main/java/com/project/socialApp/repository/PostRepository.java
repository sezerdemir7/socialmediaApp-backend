package com.project.socialApp.repository;

import com.project.socialApp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Transactional
    List<Post> findByUserId(Long aLong);
    @Query(value = "select id from post  where user_id= :userId order by create_date desc limit 5"
            ,nativeQuery = true)
    List<Long> findTopByUserId(@Param("userId") long userId);
}
