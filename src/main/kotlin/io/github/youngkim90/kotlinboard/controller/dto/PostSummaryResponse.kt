package io.github.youngkim90.kotlinboard.controller.dto

data class PostSummaryResponse(
  val id: Long,
  val title: String,
  val createdBy: String,
  val createdAt: String,
)
