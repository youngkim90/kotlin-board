package io.github.youngkim90.kotlinboard.service

import io.github.youngkim90.kotlinboard.Exception.PostNotFoundException
import io.github.youngkim90.kotlinboard.domain.Like
import io.github.youngkim90.kotlinboard.repository.LikeRepository
import io.github.youngkim90.kotlinboard.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LikeService(
  private val likeRepository: LikeRepository,
  private val postRepository: PostRepository,
) {
  @Transactional
  fun createLike(postId: Long, createdBy: String): Long {
    val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
    return likeRepository.save(Like(post, createdBy)).id
  }

  fun countLike(postId: Long): Long {
    return likeRepository.countByPostId(postId)
  }
}
