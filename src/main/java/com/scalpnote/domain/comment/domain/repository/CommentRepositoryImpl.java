package com.scalpnote.domain.comment.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.scalpnote.domain.comment.domain.Comment;
import com.scalpnote.domain.comment.domain.QComment;
import com.scalpnote.domain.comment.dto.CommentRes;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.scalpnote.domain.comment.domain.QComment.comment;
import static com.scalpnote.domain.comment.dto.CommentRes.*;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentRes> findByBoardId(Long id) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.board.id.eq(id))
                .orderBy(comment.parent.id.asc().nullsFirst(),
                        comment.createdAt.asc())
                .fetch();

        List<CommentRes> commentResList = new ArrayList<>();
        Map<Long, CommentRes> commentResHashMap = new HashMap<>();

        comments.forEach(c -> {
            CommentRes commentRes = toDto(c);
            commentResHashMap.put(commentRes.getId(), commentRes);
            if (c.getParent() != null) commentResHashMap.get(c.getParent().getId()).getChildren().add(commentRes);
            else commentResList.add(commentRes);
        });

        return commentResList;
    }
}
