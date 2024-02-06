package io.github.youngkim90.kotlinboard.controller.domain

import io.github.youngkim90.kotlinboard.Exception.PostNotUpdatableException
import io.github.youngkim90.kotlinboard.service.dto.PostUpdateRequestDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Post(
  createdBy: String,
  title: String,
  content: String,
) : BaseEntity(createdBy) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0

  var title: String = title
    protected set

  var content: String = content
    protected set

  fun update(postUpdateRequestDto: PostUpdateRequestDto) {
    if (postUpdateRequestDto.updatedBy != this.createdBy) throw PostNotUpdatableException()
    
    this.title = postUpdateRequestDto.title
    this.content = postUpdateRequestDto.content
    super.updatedBy(postUpdateRequestDto.updatedBy)
  }
}
