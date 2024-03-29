CREATE TABLE BOARD(
    BOARD_NUM NUMBER(5), --글 번호(기본 키)
    BOARD_NAME VARCHAR2(30), --작성자
    BOARD_PASS VARCHAR2(30), --비밀번호
    BOARD_SUBJECT VARCHAR2(300), --제목
    BOARD_CONTENT VARCHAR2(4000), --내용
    BOARD_FILE VARCHAR2(50), --첨부될 파일 명(시스템에 저장되는 파일명)
    BOARD_ORIGINAL VARCHAR2(50), --첨부될 파일 명(시스템에 저장된 파일의 원래 파일명)
    BOARD_RE_REF NUMBER(5), --답변 글 작성시 참조되는 글의 번호
    BOARD_RE_LEV NUMBER(5), --답변 글의 깊이(원문 글:0, 답변:1, 답변의 답변2)
    BOARD_RE_SEQ NUMBER(5), --답변 글의 순서(원문 글 기준으로 보여주는 순서)
    BOARD_READCOUNT NUMBER(5), --글의 조회수
    BOARD_DATE DATE, --글의 작성 날짜
    PRIMARY KEY(BOARD_NUM)
);

CREATE SEQUENCE board_seq
    START WITH 1
    INCREMENT BY 1;

SELECT * FROM BOARD;

DROP TABLE BOARD PURGE;




