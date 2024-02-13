package io.github.youngkim90.kotlinboard.event.dto

data class LikeEvent(
  val postId: Long,
  val createdBy: String,
)
