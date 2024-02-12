package io.github.youngkim90.kotlinboard.domain

import jakarta.persistence.*

@Entity
@Table(name = "likes", indexes = [Index(name = "idx_post_id", columnList = "post_id")])
class Like(
  post: Post,
  createdBy: String,
) : BaseEntity(createdBy) {
  @Id
  @GeneratedValue
  val id: Long = 0

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
  var post: Post = post
    protected set
}
