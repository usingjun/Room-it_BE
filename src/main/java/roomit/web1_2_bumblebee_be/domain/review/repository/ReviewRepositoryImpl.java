package roomit.web1_2_bumblebee_be.domain.review.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import roomit.web1_2_bumblebee_be.domain.review.dto.request.ReviewSearch;
import roomit.web1_2_bumblebee_be.domain.review.entity.QReview;
import roomit.web1_2_bumblebee_be.domain.review.entity.Review;

import java.util.Collections;
import java.util.List;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public ReviewRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }




    @Override
    public List<Review> getList(ReviewSearch reviewSearch) {
        QReview review = QReview.review;
        if (reviewSearch.getLastId() != null) {
            boolean exists = jpaQueryFactory.selectOne()
                    .from(review)
                    .where(review.reviewId.eq(reviewSearch.getLastId()))
                    .fetchFirst() != null;

            // lastId가 존재하지 않으면 빈 리스트 반환
            if (!exists) {
                return Collections.emptyList();
            }
        }
        BooleanExpression cursorCondition = reviewSearch.getLastId() == null
                ? null
                : review.reviewId.lt(reviewSearch.getLastId());

        return jpaQueryFactory.selectFrom(review)
                .where(cursorCondition)
                .limit(reviewSearch.getSize())
                .orderBy(review.reviewId.desc()) // 최신순으로 정렬
                .fetch();
    }
}