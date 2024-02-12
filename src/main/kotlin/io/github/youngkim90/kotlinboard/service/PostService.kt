package io.github.youngkim90.kotlinboard.service

import io.github.youngkim90.kotlinboard.Exception.PostNotDeletableException
import io.github.youngkim90.kotlinboard.Exception.PostNotFoundException
import io.github.youngkim90.kotlinboard.domain.Post
import io.github.youngkim90.kotlinboard.repository.PostRepository
import io.github.youngkim90.kotlinboard.repository.TagRepository
import io.github.youngkim90.kotlinboard.service.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
  private val postRepository: PostRepository,
  private val likeService: LikeService,
  private val tagRepository: TagRepository,
) {
  private fun findById(id: Long): Post {
    return postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
  }

  @Transactional // 함수 단위의 트랜잭션이 우선 적용
  fun createPost(requestDto: PostCreateRequestDto): Long {
    return postRepository.save(requestDto.toEntity()).id
  }

  @Transactional
  fun updatePost(id: Long, requestDto: PostUpdateRequestDto): Long {
    val post = findById(id)
    post.update(requestDto)
    return id
  }

  @Transactional
  fun deletePost(id: Long, deletedBy: String): Long {
    val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
    if (post.createdBy != deletedBy) throw PostNotDeletableException()
    postRepository.delete(post)
    return id
  }

  fun getPost(id: Long): PostDetailResponseDto {
    val post = findById(id)
    val likeCount = likeService.countLike(id)
    return post.toDetailResponseDto(likeCount)
  }

  fun findPageBy(
    pageRequest: Pageable,
    postSearchRequestDto: PostSearchRequestDto,
  ): Page<PostSummaryResponseDto> {
    postSearchRequestDto.tag?.let {
      return tagRepository.findPageBy(pageRequest, it).toSummaryResponseDto(likeService::countLike)
    }
    return postRepository.findPageBy(pageRequest, postSearchRequestDto).toSummaryResponseDto(likeService::countLike)
  }
}
