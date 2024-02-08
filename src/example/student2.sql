drop table hakjum purge;
create table hakjum(
    LOWSCORE NUMBER(3),
    HISCORE NUMBER(5, 2),
    GRADE VARCHAR2(3) PRIMARY KEY
);

INSERT INTO hakjum VALUES(0, 59.99, 'F');
INSERT INTO hakjum VALUES(60, 64.99, 'D0');
INSERT INTO hakjum VALUES(65, 69.99, 'D+');
INSERT INTO hakjum VALUES(70, 74.99, 'C0');
INSERT INTO hakjum VALUES(75, 79.99, 'C+');
INSERT INTO hakjum VALUES(80, 84.99, 'B0');
INSERT INTO hakjum VALUES(85, 89.99, 'B+');
INSERT INTO hakjum VALUES(90, 94.99, 'A0');
INSERT INTO hakjum VALUES(95, 100, 'A+');


select * from hakjum;

drop table student purge;

CREATE TABLE student(
    NO NUMBER PRIMARY KEY,
    NAME VARCHAR2(21) NOT NULL,
    KOR NUMBER(3) NOT NULL CHECK(KOR BETWEEN 0 AND 100),
    MATH NUMBER(3) NOT NULL CHECK(MATH >= 0 AND MATH <= 100),
    ENG NUMBER(3) NOT NULL CHECK(ENG >= 0 AND ENG <= 100),
    TOT NUMBER(3),
    AVG NUMBER(5, 2),
    GRADE VARCHAR2(3) REFERENCES hakjum(GRADE)
);

--시퀀스 생성

select * from student;

DROP SEQUENCE STUDENT_SEQ;

CREATE SEQUENCE STUDENT_SEQ;