package io.github.youngkim90.kotlinboard.service

import io.github.youngkim90.kotlinboard.Exception.PostNotDeletableException
import io.github.youngkim90.kotlinboard.Exception.PostNotFoundException
import io.github.youngkim90.kotlinboard.Exception.PostNotUpdatableException
import io.github.youngkim90.kotlinboard.domain.Comment
import io.github.youngkim90.kotlinboard.domain.Post
import io.github.youngkim90.kotlinboard.repository.CommentRepository
import io.github.youngkim90.kotlinboard.repository.PostRepository
import io.github.youngkim90.kotlinboard.repository.TagRepository
import io.github.youngkim90.kotlinboard.service.dto.PostCreateRequestDto
import io.github.youngkim90.kotlinboard.service.dto.PostSearchRequestDto
import io.github.youngkim90.kotlinboard.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
  private val postService: PostService,
  private val postRepository: PostRepository,
  private val commentRepository: CommentRepository,
  private val tagRepository: TagRepository,
) : BehaviorSpec({ // given, when, then 구조의 테스트
  /**
   * kotest TDD 테스트코드 작성 방법
   * 1. 테스트 하려는 기능을 설명한다.(given)
   * 2. 기능의 실행 조건과 실행 결과를 설명한다.(when, then)
   * 3. 실행 조건과 검증 로직을 작성한다.
   * 4. 서비스 비즈니스 로직을 작성한다.
   * 5. 테스트를 실행한다.
   */
  beforeSpec {
    postRepository.saveAll(
      listOf(
        Post(title = "title1", content = "content", createdBy = "Ryan1", tags = listOf("태그1", "태그2")),
        Post(title = "title2", content = "content", createdBy = "Ryan2", tags = listOf("태그1", "태그2")),
        Post(title = "title3", content = "content", createdBy = "Ryan3", tags = listOf("태그1", "태그2")),
        Post(title = "title14", content = "content", createdBy = "Ryan1", tags = listOf("태그1", "태그5")),
        Post(title = "title15", content = "content", createdBy = "Ryan1", tags = listOf("태그1", "태그5")),
        Post(title = "title16", content = "content", createdBy = "Ryan1", tags = listOf("태그1", "태그5")),
        Post(title = "title17", content = "content", createdBy = "Ryan1", tags = listOf("태그1", "태그5")),
        Post(title = "title18", content = "content", createdBy = "Ryan8", tags = listOf("태그1", "태그5"))
      )
    )
  }
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
    When("태그를 추가하면") {
      val postId = postService.createPost(
        PostCreateRequestDto(
          title = "제목",
          content = "내용",
          createdBy = "Ryan",
          tags = listOf("태그1", "태그2")
        )
      )
      then("태그가 정상적으로 추가된다.") {
        val tags = tagRepository.findByPostId(postId)
        tags.size shouldBe 2
        tags[0].name shouldBe "태그1"
        tags[1].name shouldBe "태그2"
      }
    }
  }

  given("게시글 수정시") {
    val postId =
      postRepository.save(Post(title = "제목", content = "내용", createdBy = "Ryan", tags = listOf("태그1", "태그2"))).id
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
    When("태그를 수정하였을 때") {
      val updatedId = postService.updatePost(
        postId,
        PostUpdateRequestDto(
          title = "updated Title",
          content = "updated Content",
          updatedBy = "Ryan",
          tags = listOf("태그1", "태그2", "태그3")
        )
      )
      then("태그가 정상적으로 수정된다.") {
        val tags = tagRepository.findByPostId(updatedId)
        tags.size shouldBe 3
        tags[2].name shouldBe "태그3"
      }
      then("태그 순서가 변경되었을때 정상적으로 변경됨을 확인한다.") {
        postService.updatePost(
          postId,
          PostUpdateRequestDto(
            title = "updated Title",
            content = "updated Content",
            updatedBy = "Ryan",
            tags = listOf("태그3", "태그2", "태그1")
          )
        )

        val tags = tagRepository.findByPostId(updatedId)
        tags.size shouldBe 3
        tags[2].name shouldBe "태그1"
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

  given("게시글 상세조회시") {
    val saved =
      postRepository.save(
        Post(
          title = "title",
          content = "content",
          createdBy = "Ryan",
          tags = listOf("태그1", "태그2", "태그3")
        )
      )
    When("정상 조회시") {
      val post = postService.getPost(saved.id)
      then("게시글의 내용이 정상적으로 반환됨을 확인한다.") {
        post.id shouldBe saved.id
        post.title shouldBe "title"
        post.content shouldBe "content"
        post.createdBy shouldBe "Ryan"
      }
      then("태그가 정상적으로 조회된다.") {
        post.tags.size shouldBe 3
        post.tags[0] shouldBe "태그1"
        post.tags[1] shouldBe "태그2"
        post.tags[2] shouldBe "태그3"
      }
    }
    When("게시글이 없을 때") {
      then("게시글을 찾을 수 없다라는 예외가 발생한다.") {
        shouldThrow<PostNotFoundException> {
          postService.getPost(9999L)
        }
      }
    }
    When("댓글 추가시") {
      commentRepository.saveAll(
        listOf(
          Comment(content = "댓글 내용1", post = saved, createdBy = "Ryan"),
          Comment(content = "댓글 내용2", post = saved, createdBy = "Gosling"),
          Comment(content = "댓글 내용3", post = saved, createdBy = "Bob")
        )
      )
      val post = postService.getPost(saved.id)
      then("댓글이 함께 조회된다.") {
        post.comments.size shouldBe 3
        post.comments[0].content shouldBe "댓글 내용1"
        post.comments[1].content shouldBe "댓글 내용2"
        post.comments[2].content shouldBe "댓글 내용3"
        post.comments[0].createdBy shouldBe "Ryan"
        post.comments[1].createdBy shouldBe "Gosling"
        post.comments[2].createdBy shouldBe "Bob"
      }
    }
  }

  given("게시글 목록조회시") {
    When("정상 조회시") {
      val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto())
      then("게시글 페이지가 반환된다.") {
        postPage.number shouldBe 0
        postPage.size shouldBe 5
        postPage.content.size shouldBe 5
        postPage.content[0].title shouldContain "title"
        postPage.content[0].createdBy shouldContain "Ryan"
      }
    }
    When("타이틀로 검색") {
      then("타이틀에 해당하는 게시글이 반환된다.") {
        val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(title = "title1"))
        postPage.number shouldBe 0
        postPage.size shouldBe 5
        postPage.content.size shouldBe 5
        postPage.content[0].title shouldContain "title1"
        postPage.content[0].createdBy shouldContain "Ryan"
      }
    }
    When("작성자로 검색") {
      val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(createdBy = "Ryan1"))
      then("작성자에 해당하는 게시글이 반환된다.") {
        postPage.number shouldBe 0
        postPage.size shouldBe 5
        postPage.content.size shouldBe 5
        postPage.content[0].title shouldContain "title1"
        postPage.content[0].createdBy shouldBe "Ryan1"
      }
      then("첫번째 태그가 함께 조회된다.") {
        postPage.content.forEach {
          it.firstTag shouldBe "태그1"
        }
      }
    }
    When("태그로 검색") {
      val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(tag = "태그5"))
      then("태그에 해당하는 게시글이 반환된다.") {
        postPage.number shouldBe 0
        postPage.size shouldBe 5
        postPage.content.size shouldBe 5
        postPage.content[0].title shouldContain "title18"
        postPage.content[1].title shouldContain "title17"
        postPage.content[2].title shouldContain "title16"
        postPage.content[3].title shouldContain "title15"
        postPage.content[4].title shouldContain "title14"
      }
    }
  }
})
