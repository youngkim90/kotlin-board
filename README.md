## 자동배포 게시판 서비스 구축

### 목표

- Kotlin(1.9.22), SpringBoot(3.2.2) 환경에서 자동배포 게시판 서비스를 구축한다.
- Github Actions와 AWS Elastic Beanstalk 플랫폼(프리티어)을 활용하여 EC2, RDS에 자동배포가 이루어지도록 Workflow를 구성한다.

### 과정

1. 프로젝트 환경 설정(gradle, db, kotest, swagger, ..)
2. 로컬/개발(RDS) MySQL 데이터베이스 연동
3. **Github Actions**와 **Elastic Beastalk**를 활용한 CI/CD 구축
4. 게시판 API, 도메인(엔티티) 설계
5. 게시글 C,R,U,D 기능 테스트(**TDD**) 및 구현
6. 댓글 API, 도메인(엔티티) 설계
7. 댓글 C,R,U,D 기능 테스트(**TDD**) 및 구현

...ing

[자동배포 게시판 서비스 API 명세서 링크](http://kotlin-board.ap-northeast-2.elasticbeanstalk.com/swagger-ui/index.html#/)


---

참고강의 - [패스트캠퍼스 코틀린 강의](https://fastcampus.co.kr/courses/217930)
