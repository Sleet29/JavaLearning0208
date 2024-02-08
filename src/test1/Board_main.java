package test1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board_main {
    static String menus[] = {
        "글쓰기",
        "수정",
        "답변달기",
        "글삭제",
        "조회",
        "페이지 선정",
        "종료"
    };

    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BoardDAO_seq dao = new BoardDAO_seq();

		do {
			int menu = menuselect(sc);
			switch (menu) {
			case 1:
				insert(sc, dao);
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
    
    
    private static int menuselect(Scanner sc) {
        int choice;
        do {
            displayMenu();
            choice = inputNumber(sc, 1, menus.length);
        } while (choice == -1); // Continue until valid choice is made
        return choice;
    }

    private static void displayMenu() {
        System.out.println("=====================================================================================");
        for (int i = 0; i < menus.length; i++) {
            System.out.println((i + 1) + "." + menus[i]);
        }
        System.out.println("=====================================================================================");
        System.out.print("메뉴를 선택하세요> ");
    }

    private static int inputNumber(Scanner sc, int start, int end) {
        int input = 0;
        while (true) {
            try {
                input = Integer.parseInt(sc.nextLine());
                if (input <= end && input >= start) {
                    break;
                } else {
                    System.out.println(start + "~" + end + "사이의 숫자를 입력하세요>");
                }
            } catch (NumberFormatException e) {
                System.out.print("숫자로 입력하세요> ");
            }
        }
        return input;
    }
    
    private static void insert(Scanner sc, BoardDAO_seq dao) {
    	Board board = new Board();
    	System.out.print("작성자를 입력하세요> ");
    	board.setBoard_name(sc.nextLine());
    	System.out.print("비밀번호를 입력하세요> ");
    	board.setBoard_pass(sc.nextLine());
    	System.out.print("제목을 입력하세요> ");
    	board.setBoard_subject(sc.nextLine());
    	System.out.print("글 내용을 입력하세요> ");
    	board.setBoard_content(sc.nextLine());
    	System.out.print("BOARD-RE을 입력하세요> ");
    	board.setBoard_content(sc.nextLine());
    	System.out.print("BOARD-SE을 입력하세요> ");
    	board.setBoard_content(sc.nextLine());
    	System.out.print("BOARD-RECO을 입력하세요> ");
    	board.setBoard_content(sc.nextLine());
    	
    	if (dao.boardInsert(board) == 1) {
    		System.out.println("글이 작성되었습니다.");
    	} else {
    		System.out.println("오류가 발생하였습니다.");
    	} 
    }
    
    private static void printTitle() {
    	System.out.printf("%s\t%s\t\t%s\t\t\t%s\t\t\t%s\t%s\t%s\t%s\n",
    			"글번호","작성자","제목","내용","ref","lev","seq","date");
    }
    
    private static void selectAll(BoardDAO_seq dao) {
    	// 1 - 페이지(page)
    	// 10 - 페이지 당 목록의 수(limit)
        List<Board> list = dao.getBoardList(1, 10);
        if (list.isEmpty()) {
            System.out.println("테이블에 데이터가 없습니다.");

        } else {
        	printTitle();
            //System.out.println("글번호\t작성자\t제목\t내용\tref\tlev\tseq\tdate");
            for (Board board : list) {
                System.out.println(board.toString()
                		/*board.getBoard_num() + "\t" + board.getBoard_name() + "\t" +
                        board.getBoard_subject() + "\t" + board.getBoard_content() + "\t" +
                        board.getBoard_re_ref() + "\t" + board.getBoard_re_lev() + "\t" +
                        board.getBoard_re_seq() + "\t" + board.getBoard_date()*/);
            }
        }
    }
}
    
    /*

    public static void select(Scanner sc, BoardDAO_seq dao) {
        System.out.print("조회할 번호를 입력하세요> ");
        int number = inputNumber(sc, 1, 200);
        Board st = dao.select(number);

        if (st != null) {
            printTitle();
            System.out.println(st.toString());
        } else {
            System.out.println("해당 번호로 조회된 학생이 없습니다.");
        }
    }

    private static void update(Scanner sc, BoardDAO_seq dao) {
        System.out.print("수정할 번호를 입력하세요> ");
        int number = inputNumber(sc, 1, 200);

        Board student = dao.select(number);

        if (student != null) {
            System.out.print("새로운 이름을 입력하세요> ");
            String name = sc.nextLine();
            System.out.print("새로운 국어점수를 입력하세요> ");
            int kor = inputNumber(sc, 0, 100);
            System.out.print("새로운 수학점수를 입력하세요> ");
            int math = inputNumber(sc, 0, 100);
            System.out.print("새로운 영어점수를 입력하세요> ");
            int eng = inputNumber(sc, 0, 100);

            student.setName(name);
            student.setKor(kor);
            student.setMath(math);
            student.setEng(eng);

            int result = dao.update(student);
            if (result == 1) {
                System.out.println("수정 되었습니다.");
            } else {
                System.out.println("수정 실패하였습니다.");
            }
        } else {
            System.out.println("해당 번호로 조회된 학생이 없습니다.");
        }
    }

    private static void delete(Scanner sc, BoardDAO_seq dao) {
        System.out.print("삭제할 번호를 입력하세요> ");
        int number = inputNumber(sc, 1, 200);

        int result = dao.delete(number);
        if (result == 1) {
            System.out.println("삭제 되었습니다.");
        } else {
            System.out.println("삭제 실패하였습니다.");
        }
    }


    private static void printTitle() {
        System.out.println("번호\t이름\t국어\t수학\t영어");
    }
}
*/