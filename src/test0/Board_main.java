package test0;

import java.util.Scanner;

public class Board_main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BoardDAO_seq dao = new BoardDAO_seq();

        do {
            int menu = menuselect(sc);
            switch (menu) {
                case 1:
                    insert(sc, dao);
                    break;
                case 2:
                    update(sc, dao);
                    break;
                case 5:
                    selectAll(dao);
                    break;
                case 7:
                    sc.close();
                    return;
            }
        } while (true);
    }

    public static int menuselect(Scanner sc) {
        // 메뉴 출력
        System.out.println("================ 출력 결과 ================================");
        System.out.println("1.글쓰기\n2.수정\n3.답변달기\n4.글삭제\n5.조회\n6.페이지 선정\n7.종료");
        System.out.println("===========================================================");

        return inputNumber(sc, 1, 7); // 메뉴 선택
    }

    public static int inputNumber(Scanner sc, int start, int end) {
        int number;
        do {
            System.out.print("메뉴를 선택하세요>");
            String input = sc.next();
            try {
                number = Integer.parseInt(input);
                if (number >= start && number <= end) {
                    break;
                } else {
                    System.out.println(start + "~" + end + "사이의 숫자를 입력하세요>");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력하세요>");
            }
        } while (true);
        return number;
    }

    private static void insert(Scanner sc, BoardDAO_seq dao) {
        System.out.print("작성자>");
        String board_name = sc.next();
        System.out.print("비밀번호>");
        String board_pass = sc.next();
        System.out.print("제목>");
        String board_subject = sc.next();
        System.out.print("글 내용>");
        String board_content = sc.next();

        Board board = new Board();
        board.setBoard_name(board_name);
        board.setBoard_pass(board_pass);
        board.setBoard_subject(board_subject);
        board.setBoard_content(board_content);

        int result = dao.boardInsert(board);
        if (result == 1) {
            System.out.println("글이 작성되었습니다.");
        } else {
            System.out.println("오류가 발생되었습니다.");
        }
    }

    private static void selectAll(BoardDAO_seq dao) {
        dao.getBoardList(1, 10); // 첫 페이지에 보여줄 10개의 목록을 가져옴
    }

    private static void update(Scanner sc, BoardDAO_seq dao) {
        System.out.print("수정할 글 번호를 입력하세요>");
        int num = inputNumber(sc); // 글번호를 입력 받음

        Board board = select(dao, num); // 글번호에 해당하는 글을 가져옴
        if (board != null) { // 가져온 글이 있으면
            // 수정을 위한 제목, 글 내용, 비밀번호를 입력 받음
            System.out.print("제목>");
            String board_subject = sc.next();
            System.out.print("글 내용>");
            String board_content = sc.next();
            System.out.print("비밀번호>");
            String board_pass = sc.next();

            // 입력 받은 데이터(제목, 글 내용)는 board의 setter를 이용해서 저장
            board.setBoard_subject(board_subject);
            board.setBoard_content(board_content);

            if (board_pass.equals(board.getBoard_pass())) { // 비밀번호가 일치하는 경우
                int result = dao.boardModify(board);
                if (result == 1) {
                    System.out.println("수정에 성공했습니다.");
                } else {
                    System.out.println("수정에 실패했습니다.");
                }
            } else {
                System.out.println("비밀번호가 다릅니다.");
            }
        }
    }

    private static Board select(BoardDAO_seq dao, int num) {
        Board board = dao.getDetail(num); // 글번호에 해당하는 글을 가져옴
        if (board != null) {
            System.out.println(board.toString()); // 가져온 글을 출력
        } else {
            System.out.println("해당 글이 존재하지 않습니다.");
        }
        return board;
    }
}
