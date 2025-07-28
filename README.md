# 보드 


## 권한
1. SuperAdmin - 통계 및 어드민 페이지 접근
2. SubAdmin - 전체 게시판 및 신고 관리
3. BoardManager - 하나의 게시판 및 신고 관리
4. User - 일반 유저

권한은 다대다 관계

## 로그인
- 사용자가 정보 입력 시 확인 후 UserPrincipal 객체 생성
- UserPrincipal HttpSession 유저에 저장
- Resolver를 통해 객체 찾아 세션 주입
- 이를 통해 각 컨트롤러에서 HttpSession을 통해서 getId를 받아 유저를 찾고 그 유저에서 권한을 찾는 DB 조회를 줄일 수 있음 (권한은 초기에 UserPrincipal 메소드로 찾아서 담아놓음)
- 로그인 성공 시 세션은 레디스에 저장 됨, 로그아웃 시 세션 삭제

## 보드 (각 게시글의 카테고리, 게시판)
- 이 보드 번호를 통해 권한으로 관리
- 초기 게시판 생성, Initializer 등록

## 포스트 (각 게시판의 글)
- QueryDsl 조회 구현, N+1 문제 해결
- boardId + createdAt 복합 인덱스, userId + createdAt 복합 인덱스 + title 인덱스 사용
- 좋아요, 조회수 테이블 분리


## 포스트 좋아요
- postLike 테이블 (로그), post 내 like 변수를 두어 조회 시 select count하지 않도록 설계

## 포스트 조회수
- 