package io.github.youngkim90.kotlinboard.event

import io.github.youngkim90.kotlinboard.Exception.PostNotFoundException
import io.github.youngkim90.kotlinboard.domain.Like
import io.github.youngkim90.kotlinboard.event.dto.LikeEvent
import io.github.youngkim90.kotlinboard.repository.LikeRepository
import io.github.youngkim90.kotlinboard.repository.PostRepository
import io.github.youngkim90.kotlinboard.util.RedisUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

@Service
class LikeEventHandler(
  private val postRepository: PostRepository,
  private val likeRepository: LikeRepository,
  private val redisUtil: RedisUtil,
) {
  @Async
  @TransactionalEventListener(LikeEvent::class)
  fun handle(event: LikeEvent) {
    val post = postRepository.findByIdOrNull(event.postId) ?: throw PostNotFoundException()
    redisUtil.increment(redisUtil.getLikeCountKey(event.postId))
    likeRepository.save(Like(post, event.createdBy)).id
  }
}
