package io.github.youngkim90.kotlinboard.service.dto

import io.github.youngkim90.kotlinboard.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponseDto(
  val id: Long,
  val title: String,
  val createdBy: String,
  val createdAt: String,
  val firstTag: String? = null,
  val likeCount: Long = 0,
)

fun Page<Post>.toSummaryResponseDto(countLike: (Long) -> Long) = PageImpl(
  this.content.map { it.toSummaryResponseDto(countLike) },
  this.pageable,
  this.totalElements
)

fun Post.toSummaryResponseDto(countLike: (Long) -> Long) = PostSummaryResponseDto(
  id = this.id,
  title = this.title,
  createdBy = this.createdBy,
  createdAt = this.createdAt.toString(),
  firstTag = this.tags.firstOrNull()?.name,
  likeCount = countLike(id)
)
