package io.github.youngkim90.kotlinboard.service

import io.github.youngkim90.kotlinboard.Exception.PostNotDeletableException
import io.github.youngkim90.kotlinboard.Exception.PostNotFoundException
import io.github.youngkim90.kotlinboard.repository.PostRepository
import io.github.youngkim90.kotlinboard.service.dto.PostCreateRequestDto
import io.github.youngkim90.kotlinboard.service.dto.PostUpdateRequestDto
import io.github.youngkim90.kotlinboard.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
  private val postRepository: PostRepository,
) {
  @Transactional // 함수 단위의 트랜잭션이 우선 적용
  fun createPost(requestDto: PostCreateRequestDto): Long {
    return postRepository.save(requestDto.toEntity()).id
  }

  @Transactional
  fun updatePost(id: Long, requestDto: PostUpdateRequestDto): Long {
    val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
    post.update(requestDto)
    return id
  }

  @Transactional
  fun deletePost(id: Long, deletedBy: String): Long? {
    val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
    if (post.createdBy != deletedBy) throw PostNotDeletableException()
    postRepository.delete(post)
    return id
  }
}
