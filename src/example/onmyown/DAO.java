package example.onmyown;

import java.sql.*;
import java.util.ArrayList;

public class DAO {
    public int insert(Student3 student) {
        int result = 0;
        String sql = "INSERT INTO student VALUES (STUDENT_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, "
        + "(SELECT GRADE FROM HAKJUM WHERE ? BETWEEN LOWSCORE AND HISCORE))";
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getKor());
            pstmt.setInt(3, student.getMath());
            pstmt.setInt(4, student.getEng());
            pstmt.setInt(5, student.getTot());
            pstmt.setFloat(6, student.getAvg());
            pstmt.setFloat(7, student.getAvg());

            result = pstmt.executeUpdate();
            System.out.println("db에 반영됨 . . . . . .");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public Student3 select(int studentNumber) {
        Student3 student = null;
        String sql = "SELECT * FROM student WHERE no = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                student = new Student3();
                student.setNo(rs.getInt("no"));
                student.setName(rs.getString("name"));
                student.setKor(rs.getInt("kor"));
                student.setMath(rs.getInt("math"));
                student.setEng(rs.getInt("eng"));
                student.setTot(rs.getInt("tot"));
                student.setAvg(rs.getFloat("avg"));
                student.setGrade(rs.getString("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return student;
    }
    
    public int update(Student3 student) {
        int result = 0;
        String sql = "UPDATE student SET name = ?, kor = ?, math = ?, eng = ?, tot = ?, avg = ?, "
                   + "grade = (SELECT GRADE FROM HAKJUM WHERE ? BETWEEN LOWSCORE AND HISCORE) WHERE no = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getKor());
            pstmt.setInt(3, student.getMath());
            pstmt.setInt(4, student.getEng());
            pstmt.setInt(5, student.getKor() + student.getMath() + student.getEng()); // tot
            pstmt.setFloat(6, (float) (student.getKor() + student.getMath() + student.getEng()) / 3); // avg
            pstmt.setFloat(7, (float) (student.getKor() + student.getMath() + student.getEng()) / 3); // grade
            pstmt.setInt(8, student.getNo());

            result = pstmt.executeUpdate();
            System.out.println("데이터가 수정되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int delete(int studentNumber) {
        int result = 0;
        String sql = "DELETE FROM student WHERE no = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentNumber);

            result = pstmt.executeUpdate();
            System.out.println("데이터가 삭제되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
   
    public ArrayList<Student3> selectAll() {
        ArrayList<Student3> list = new ArrayList<>();
        String sql = "SELECT * FROM student order by no";
        
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Student3 g = new Student3();
                int no = rs.getInt("no");
                g.setNo(no);
                String name = rs.getString("name");
                g.setName(name);
                int kor = rs.getInt("kor");
                g.setKor(kor);
                int math = rs.getInt("math");
                g.setMath(math);
                int eng = rs.getInt("eng");
                g.setEng(eng);
                int tot = rs.getInt("tot");
                g.setTot(tot);
                float avg = rs.getFloat("avg");
                g.setAvg(avg);
                String grade = rs.getString("grade");
                g.setGrade(grade);

                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
