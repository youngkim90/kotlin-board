package io.github.youngkim90.kotlinboard.service

import io.github.youngkim90.kotlinboard.Exception.CommentNotDeletableException
import io.github.youngkim90.kotlinboard.Exception.CommentNotFoundException
import io.github.youngkim90.kotlinboard.Exception.PostNotFoundException
import io.github.youngkim90.kotlinboard.repository.CommentRepository
import io.github.youngkim90.kotlinboard.repository.PostRepository
import io.github.youngkim90.kotlinboard.service.dto.CommentCreateRequestDto
import io.github.youngkim90.kotlinboard.service.dto.CommentUpdateRequestDto
import io.github.youngkim90.kotlinboard.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
data class CommentService(
  private val commentRepository: CommentRepository,
  private val postRepository: PostRepository,
) {
  @Transactional
  fun createComment(postId: Long, createRequestDto: CommentCreateRequestDto): Long {
    val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
    return commentRepository.save(createRequestDto.toEntity(post)).id
  }

  @Transactional
  fun updateComment(id: Long, updateRequestDto: CommentUpdateRequestDto): Long {
    val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
    comment.update(updateRequestDto)
    return comment.id
  }

  @Transactional
  fun deleteComment(id: Long, deletedBy: String): Long {
    val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
    if (comment.createdBy != deletedBy) throw CommentNotDeletableException()
    commentRepository.delete(comment)
    return comment.id
  }
}
