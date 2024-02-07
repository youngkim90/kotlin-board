package io.github.youngkim90.kotlinboard.controller.domain

import jakarta.persistence.*

@Entity
class Comment(
  content: String,
  post: Post,
  createdBy: String,
) : BaseEntity(createdBy) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L

  var content: String = content
    protected set

  @ManyToOne(fetch = FetchType.LAZY)
  var post: Post = post
    protected set
}
