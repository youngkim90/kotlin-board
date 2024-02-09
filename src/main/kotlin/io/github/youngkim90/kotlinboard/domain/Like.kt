package io.github.youngkim90.kotlinboard.domain

import jakarta.persistence.*

@Entity
@Table(name = "likes")
class Like(
  post: Post,
  createdBy: String,
) : BaseEntity(createdBy) {
  @Id
  @GeneratedValue
  val id: Long = 0

  @ManyToOne(fetch = FetchType.LAZY)
  var post: Post = post
    protected set
}
