package io.github.youngkim90.kotlinboard.service

import io.github.youngkim90.kotlinboard.Exception.CommentNotDeletableException
import io.github.youngkim90.kotlinboard.Exception.CommentNotUpdatableException
import io.github.youngkim90.kotlinboard.Exception.PostNotFoundException
import io.github.youngkim90.kotlinboard.domain.Comment
import io.github.youngkim90.kotlinboard.domain.Post
import io.github.youngkim90.kotlinboard.repository.CommentRepository
import io.github.youngkim90.kotlinboard.repository.PostRepository
import io.github.youngkim90.kotlinboard.service.dto.CommentCreateRequestDto
import io.github.youngkim90.kotlinboard.service.dto.CommentUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class CommentServiceTest(
  private val commentService: CommentService,
  private val commentRepository: CommentRepository,
  private val postRepository: PostRepository,
) : BehaviorSpec({
  given("댓글 생성시") {
    postRepository.save(
      Post(
        title = "게시글 제목",
        content = "게시글 내용",
        createdBy = "게시글 작성자"
      )
    )
    When("인풋이 정상적으로 들어오면") {
      val commentId = commentService.createComment(
        1L,
        CommentCreateRequestDto(
          content = "댓글 내용",
          createdBy = "댓글 작성자"
        )
      )
      then("댓글이 정상 생성된다.") {
        commentId shouldBeGreaterThan 0L
        val comment = commentRepository.findByIdOrNull(commentId)
        comment shouldNotBe null
        comment?.content shouldBe "댓글 내용"
        comment?.createdBy shouldBe "댓글 작성자"
      }
    }

    When("게시글이 존재하지 않으면") {
      then("게시글 존재하지 않음 예외가 발생한다.") {
        shouldThrow<PostNotFoundException> {
          commentService.createComment(
            9999L,
            CommentCreateRequestDto(
              content = "댓글 내용",
              createdBy = "댓글 작성자"
            )
          )
        }
      }
    }
  }

  given("댓글 수정시") {
    val post = postRepository.save(
      Post(
        title = "게시글 제목",
        content = "게시글 내용",
        createdBy = "게시글 작성자"
      )
    )
    val comment = commentRepository.save(
      Comment(
        content = "댓글 내용",
        createdBy = "댓글 생성자",
        post = post
      )
    )
    When("인풋이 정상적으로 들어오면") {
      val updateId = commentService.updateComment(
        comment.id,
        CommentUpdateRequestDto(
          content = "수정된 댓글 내용",
          updatedBy = "댓글 생성자"
        )
      )
      then("댓글이 정상 수정된다.") {
        updateId shouldBe comment.id
        val updatedComment = commentRepository.findByIdOrNull(comment.id)
        updatedComment shouldNotBe null
        updatedComment?.content shouldBe "수정된 댓글 내용"
        updatedComment?.updatedBy shouldBe "댓글 생성자"
      }
    }
    When("작성자와 수정자가 다르면") {
      then("수정할 수 없는 댓글 예외가 발생한다.") {
        shouldThrow<CommentNotUpdatableException> {
          commentService.updateComment(
            comment.id,
            CommentUpdateRequestDto(
              content = "수정된 댓글 내용",
              updatedBy = "다른 사용자"
            )
          )
        }
      }
    }
  }

  given("댓글 삭제시") {
    val post = postRepository.save(
      Post(
        title = "게시글 제목",
        content = "게시글 내용",
        createdBy = "게시글 작성자"
      )
    )
    val comment1 = commentRepository.save(
      Comment(
        content = "댓글 내용",
        createdBy = "댓글 생성자",
        post = post
      )
    )
    val comment2 = commentRepository.save(
      Comment(
        content = "댓글 내용2",
        createdBy = "댓글 생성자2",
        post = post
      )
    )
    When("인풋이 정상적으로 들어오면") {
      val commentId = commentService.deleteComment(
        comment1.id,
        "댓글 생성자"
      )
      then("댓글이 정상 삭제된다.") {
        commentId shouldBe comment1.id
        commentRepository.findByIdOrNull(commentId) shouldBe null
      }
    }
    When("작성자와 삭제자가 다르면") {
      then("삭제할 수 없는 댓글 예외가 발생한다.") {
        shouldThrow<CommentNotDeletableException> {
          commentService.deleteComment(
            comment2.id,
            "삭제자"
          )
        }
      }
    }
  }
})
