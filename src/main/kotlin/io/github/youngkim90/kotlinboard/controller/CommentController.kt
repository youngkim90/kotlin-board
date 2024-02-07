package io.github.youngkim90.kotlinboard.controller

import io.github.youngkim90.kotlinboard.controller.dto.CommentCreateRequest
import io.github.youngkim90.kotlinboard.controller.dto.CommentUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
class CommentController {
  @PostMapping("posts/{postId}/comments")
  fun createComment(
    @PathVariable postId: Long,
    @RequestBody commentCreateRequest: CommentCreateRequest,
  ): Long {
    return 1L
  }

  @PutMapping("comments/{commentId}")
  fun updateComment(
    @PathVariable commentId: Long,
    @RequestBody commentUpdateRequest: CommentUpdateRequest,
  ): Long {
    return commentId
  }

  @DeleteMapping("comments/{commentId}")
  fun deleteComment(@PathVariable commentId: Long, @RequestParam deletedBy: String): Long {
    return commentId
  }
}
