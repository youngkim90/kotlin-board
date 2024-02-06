package io.github.youngkim90.kotlinboard.controller

import io.github.youngkim90.kotlinboard.controller.dto.*
import io.github.youngkim90.kotlinboard.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PostController(
  private val postService: PostService,
) {

  @PostMapping("/posts")
  fun createPost(@RequestBody postCreateRequest: PostCreateRequest): Long {
    return postService.createPost(postCreateRequest.toDto())
  }

  @PutMapping("/posts/{id}")
  fun updatePost(
    @PathVariable id: Long,
    @RequestBody postUpdateRequest: PostUpdateRequest,
  ): Long {
    return postService.updatePost(id, postUpdateRequest.toDto())
  }

  @DeleteMapping("/posts/{id}")
  fun deletePost(@PathVariable id: Long, @RequestParam createdBy: String): Long {
    return postService.deletePost(id, createdBy)
  }

  @GetMapping("/posts/{id}")
  fun getPost(@PathVariable id: Long): PostDetailResponse {
    return PostDetailResponse(
      id = id,
      title = "Post",
      content = "Content",
      createdBy = "Ryan",
      createdAt = LocalDateTime.now()
    )
  }

  @GetMapping("/posts")
  fun getPosts(pageable: Pageable, postSearchRequest: PostSearchRequest): Page<PostSummaryResponse> {
    println("title: ${postSearchRequest.title}, createdBy: ${postSearchRequest.createdBy}")
    return Page.empty()
  }
}
