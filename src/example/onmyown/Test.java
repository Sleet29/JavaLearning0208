package example.onmyown;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:xe";

            conn = DriverManager.getConnection(url, "SCOTT", "tiger");
            stmt = conn.createStatement();

            String createSequenceSQL = "CREATE SEQUENCE student_seq START WITH 1 INCREMENT BY 1";
            stmt.executeUpdate(createSequenceSQL);
            
            System.out.println("시퀀스가 성공적으로 생성되었습니다.");

            String createHakjumTableSQL 
            = "CREATE TABLE hakjum (" 
            + "LOWSCORE NUMBER(3)," 
            + "HISCORE NUMBER(5,2)," 
            + "GRADE VARCHAR2(3) PRIMARY KEY)";
            
            stmt.executeUpdate(createHakjumTableSQL);
            System.out.println("학점 테이블이 성공적으로 만들어졌습니다.");

            
            String insertHakjumDataSQL 
            = "INSERT INTO hakjum (LOWSCORE, HISCORE, GRADE) "
            		+ "VALUES " 
            		+ "(0, 59.99, 'F')," 
            		+ "(60, 64.99, 'D0')," 
            		+ "(65, 69.99, 'D+')," 
            		+ "(70, 74.99, 'C0')," 
            		+ "(75, 79.99, 'C+')," 
            		+ "(80, 84.99, 'B0')," 
            		+ "(85, 89.99, 'B+')," 
            		+ "(90, 94.99, 'A0')," 
            		+ "(95, 100, 'A+')";
            
            stmt.executeUpdate(insertHakjumDataSQL);
            System.out.println("데이터가 학점 테이블에 성공적으로 투입되었습니다.");

            String createStudentTableSQL 
            = "CREATE TABLE student (" 
            + "NO NUMBER PRIMARY KEY," 
            + "NAME VARCHAR2(21) NOT NULL," 
            + "KOR NUMBER(3) NOT NULL CHECK (KOR >= 0 AND KOR <= 100)," 
            + "MATH NUMBER(3) NOT NULL CHECK (MATH >= 0 AND MATH <= 100)," 
            + "ENG NUMBER(3) NOT NULL CHECK (ENG >= 0 AND ENG <= 100)," 
            + "TOT NUMBER(3)," 
            + "AVG NUMBER(5,2)," 
            + "GRADE VARCHAR2(3) REFERENCES hakjum(GRADE))";

            stmt.executeUpdate(createStudentTableSQL);
            System.out.println("학생 테이블이 성공적으로 만들어졌습니다.");

            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

