package com.project.socialApp.repository;

import com.project.socialApp.dto.responses.LikeResponse;
import com.project.socialApp.entities.Comment;
import com.project.socialApp.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    List<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserId(Long userId);

    List<Like> findByPostId(Long postId);


    @Query(value = 	"select 'begendi', l.post_id, u.avatar, u.user_name from "
            + "p_like l left join users u on u.id = l.user_id "
            + "where l.post_id in :postIds limit 5", nativeQuery = true)
    List<Object> findUserLikesByPostId(@Param("postIds") List<Long> postIds);

}
