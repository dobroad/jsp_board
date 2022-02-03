# b1 데이터 베이스 생성
CREATE DATABASE b1;

# b1 사용
USE b1;

# 게시물 테이블 생성
CREATE TABLE article(
    idx INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    `body` TEXT NOT NULL, 
    nickname VARCHAR(30) NOT NULL,
    regDate DATETIME NOT NULL
);

# 게시물 등록
# 테스트 데이터 3개 등록
INSERT INTO article
SET title = '제목1',
`body` = '내용1',
nickname = '홍길동',
regDate = NOW();

INSERT INTO article
SET title = '제목2',
`body` = '내용2',
nickname = '홍길순',
regDate = NOW();

INSERT INTO article
SET title = '제목3',
`body` = '내용3',
nickname = '이순신',
regDate = NOW();

# 전체 게시물 조회
SELECT *
FROM article;

# 특정 게시물 조회
SELECT *
FROM article
WHERE idx = 3;

# 특정 게시물 수정
UPDATE article 
SET title = 'aaa',
`body` = 'bbb'
WHERE idx = 3;

# 특정 게시물 삭제
DELETE FROM article
WHERE idx = 1;


# 회원 테이블 생성
CREATE TABLE `member`(
    idx INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    loginId VARCHAR(50) NOT NULL,
    loginPw VARCHAR(50) NOT NULL, 
    nickname VARCHAR(30) NOT NULL,
    regDate DATETIME NOT NULL
);

# 회원 추가
INSERT INTO `member`
SET loginId = 'hong123',
loginPw = 'h1234',
nickname = '홍길동',
regDate = NOW();

# 회원 조회
SELECT *
FROM `member`;


#로그인 정보로 회원 조회
SELECT idx
FROM `member`
WHERE loginId = 'hong123' AND loginPw = 'h1234';
