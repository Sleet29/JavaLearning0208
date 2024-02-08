package example.onmyown;

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

    private static void insert(Scanner sc, DAO dao) {
        System.out.print("이름을 입력하세요> ");
        String name = sc.nextLine();
        System.out.print("국어점수를 입력하세요> ");
        int kor = inputNumber(sc, 0, 100);
        System.out.print("수학점수를 입력하세요> ");
        int math = inputNumber(sc, 0, 100);
        System.out.print("영어점수를 입력하세요> ");
        int eng = inputNumber(sc, 0, 100);

        Student3 student = new Student3(name, kor, math, eng);

        int result = dao.insert(student);
        if (result == 1) {
            System.out.println("삽입 되었습니다.");
        } else {
            System.out.println("삽입 실패하였습니다.");
        }
    }

    public static void select(Scanner sc, DAO dao) {
        System.out.print("조회할 번호를 입력하세요> ");
        int number = inputNumber(sc, 1, 200);
        Student3 st = dao.select(number);

        if (st != null) {
            printTitle();
            System.out.println(st.toString());
        } else {
            System.out.println("해당 번호로 조회된 학생이 없습니다.");
        }
    }

    private static void update(Scanner sc, DAO dao) {
        System.out.print("수정할 번호를 입력하세요> ");
        int number = inputNumber(sc, 1, 200);

        Student3 student = dao.select(number);

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

    private static void delete(Scanner sc, DAO dao) {
        System.out.print("삭제할 번호를 입력하세요> ");
        int number = inputNumber(sc, 1, 200);

        int result = dao.delete(number);
        if (result == 1) {
            System.out.println("삭제 되었습니다.");
        } else {
            System.out.println("삭제 실패하였습니다.");
        }
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

    private static void printTitle() {
        System.out.println("번호\t이름\t국어\t수학\t영어");
    }
}
