	package example;
import java.util.ArrayList;
import java.util.Scanner;
public class CRUD {
    static String menus[] = {
        "입력",
        "조회",
        "수정",
        "삭제",
        "모두조회",
        "종료"
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DAO dao = new DAO();

        do {
            int menu = menuselect(sc);
            switch (menu) {
                case 1:
                    insert(sc, dao);
                    break;
                case 2:
                    select(sc, dao);
                    break;
                case 3:
                    update(sc, dao);
                    break;
                case 4:
                    delete(sc, dao);
                    break;
                case 5:
                    selectAll(dao);
                    break;
                case 6:
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
        System.out.println("================================================");
        for (int i = 0; i < menus.length; i++) {
            System.out.println((i + 1) + "." + menus[i]);
        }
        System.out.println("================================================");
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
                System.out.println("숫자로 입력하세요> ");
            }
        }
        return input;
    }

    private static void insert(Scanner sc, DAO dao) {
        System.out.print("이름을 입력하세요> ");
        String name = sc.nextLine();
        System.out.print("국어점수를 입력하세요> ");
        int kor = inputNumber(sc,0,100);
        System.out.print("수학점수를 입력하세요> ");
        int math = inputNumber(sc,0,100);
        System.out.print("영어점수를 입력하세요> ");
        int eng = inputNumber(sc,0,100);
        
        Student3 student = new Student3(name,kor,math,eng);
        
        int result = dao.insert(student);
        if (result ==1) {
        	System.out.println("삽입 되었습니다.");
        } else {
        	System.out.println("삽입 실패하였습니다.");
        }
    }

    private static void select(Scanner sc, DAO dao) {
        // Implement your select logic here
        System.out.println("조회 메소드 완료");
    }

    private static boolean isExist(int no, DAO dao) {
    	return dao.select(no) !=null; 	// null이 아니면 true 반환
    	
    }
    
    private static void update(Scanner sc, DAO dao) {
    	System.out.println("수정할 번호를 입력하세요> ");
    	int no = inputNumber(sc,1,200);
    	
    	if(isExist(no,dao)) {
    		System.out.println("이름을 입력하세요> ");
    		String name = sc.nextLine();
            System.out.print("국어점수를 입력하세요> ");
            int kor = inputNumber(sc,0,100);
            System.out.print("수학점수를 입력하세요> ");
            int math = inputNumber(sc,0,100);
            System.out.print("영어점수를 입력하세요> ");
            int eng = inputNumber(sc,0,100);
            
            Student3 student3 = new Student3(name,kor,math,eng);
            student3.setNo(no);
            
            int result = dao.update(student3);
            if (result == 1)
            	System.out.println("수정이 되었습니다.");
            else {
            	System.out.println("수정 중 오류 발생");
            }
    	} else {
    		System.out.println("테이블에 없는 번호 입니다.");
    	}
        System.out.println("수정 메소드 완료");
    }

    private static void delete(Scanner sc, DAO dao) {
        // Implement your delete logic here
        System.out.println("삭제 메소드 완료");
    }

    private static void selectAll(DAO dao) {
        ArrayList<Student3> list = dao.selectAll();
        if (list.isEmpty()) {
            System.out.println("테이블이 비어있습니다.");
        } else {
            for (Student3 emp : list) {
                System.out.println(emp); 
            }
        }
    }
}

	
	