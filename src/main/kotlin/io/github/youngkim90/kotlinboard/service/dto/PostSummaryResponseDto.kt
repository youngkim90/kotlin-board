package io.github.youngkim90.kotlinboard.service.dto

import io.github.youngkim90.kotlinboard.controller.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponseDto(
  val id: Long,
  val title: String,
  val createdBy: String,
  val createdAt: String,
)

fun Page<Post>.toSummaryResponseDto() = PageImpl(
  this.content.map { it.toSummaryResponseDto() },
  this.pageable,
  this.totalElements
)

fun Post.toSummaryResponseDto() = PostSummaryResponseDto(
  id = this.id,
  title = this.title,
  createdBy = this.createdBy,
  createdAt = this.createdAt.toString(),
)
