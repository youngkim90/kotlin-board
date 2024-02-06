package io.github.youngkim90.kotlinboard.service

import io.github.youngkim90.kotlinboard.Exception.PostNotDeletableException
import io.github.youngkim90.kotlinboard.Exception.PostNotFoundException
import io.github.youngkim90.kotlinboard.Exception.PostNotUpdatableException
import io.github.youngkim90.kotlinboard.controller.domain.Post
import io.github.youngkim90.kotlinboard.repository.PostRepository
import io.github.youngkim90.kotlinboard.service.dto.PostCreateRequestDto
import io.github.youngkim90.kotlinboard.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
  private val postService: PostService,
  private val postRepository: PostRepository,
) : BehaviorSpec({ // given, when, then 구조의 테스트
  /**
   * kotest TDD 테스트코드 작성 방법
   * 1. 테스트 하려는 기능을 설명한다.(given)
   * 2. 기능의 실행 조건과 실행 결과를 설명한다.(when, then)
   * 3. 실행 조건과 검증 로직을 작성한다.
   * 4. 서비스 비즈니스 로직을 작성한다.
   * 5. 테스트를 실행한다.
   */
  given("게시글 생성시") {
    When("게시글 생성") {
      val postId = postService.createPost(
        PostCreateRequestDto(
          title = "제목",
          content = "내용",
          createdBy = "Ryan"
        )
      )
      then("게시글이 정상적으로 생성됨을 확인한다.") {
        postId shouldBeGreaterThan 0L
        val post = postRepository.findByIdOrNull(postId)
        post shouldNotBe null
        post?.title shouldBe "제목"
        post?.content shouldBe "내용"
        post?.createdBy shouldBe "Ryan"
      }
    }
  }

  given("게시글 수정시") {
    val postId = postRepository.save(Post(title = "제목", content = "내용", createdBy = "Ryan")).id
    When("정상 수정시") {
      val updatedId = postService.updatePost(
        postId,
        PostUpdateRequestDto(
          title = "updated Title",
          content = "updated Content",
          updatedBy = "Ryan"
        )
      )
      then("게시글이 정상적으로 수정됨을 확인한다.") {
        postId shouldBe updatedId
        val updated = postRepository.findByIdOrNull(updatedId)
        updated shouldNotBe null
        updated?.title shouldBe "updated Title"
        updated?.content shouldBe "updated Content"
      }
    }
    When("게시글이 없을 때") {
      then("게시글을 찾을 수 없다라는 예외가 발생한다.") {
        shouldThrow<PostNotFoundException> {
          postService.updatePost(
            9999L,
            PostUpdateRequestDto(
              title = "updated Title",
              content = "updated Content",
              updatedBy = "Ryan"
            )
          )
        }
      }
    }
    When("작성자가 동일하지 않으면") {
      then("수정할 수 없는 게시물입니다 예외가 발생한다.") {
        shouldThrow<PostNotUpdatableException> {
          postService.updatePost(
            1L,
            PostUpdateRequestDto(
              title = "updated Title",
              content = "updated Content",
              updatedBy = "Gosling"
            )
          )
        }
      }
    }
  }

  given("게시글 삭제시") {
    val postId = postRepository.save(Post(title = "제목", content = "내용", createdBy = "Ryan")).id
    When("정상 삭제시") {
      val deletedId = postService.deletePost(postId, "Ryan")
      then("게시글이 정상적으로 삭제됨을 확인한다.") {
        postId shouldBe deletedId
        postRepository.findByIdOrNull(deletedId) shouldBe null
      }
    }
    When("작성자가 동일하지 않으면") {
      then("삭제할 수 없는 게시물입니다 예외가 발생한다.") {
        val postId2 = postRepository.save(Post(title = "제목", content = "내용", createdBy = "Ryan")).id
        shouldThrow<PostNotDeletableException> {
          postService.deletePost(postId2, "Gosling")
        }
      }
    }
  }
})
