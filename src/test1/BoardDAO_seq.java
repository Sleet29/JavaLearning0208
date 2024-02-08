package test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO_seq {
    public int boardInsert(Board board) {
        int result = 0;
        String sql = "INSERT INTO board (board_num, board_name, board_pass, board_subject,"
                + " board_content, board_re_ref,"
                + " board_re_lev, board_re_seq, board_readcount,"
                + " board_date) "
                + " values(board_seq.nextval,?,?,?,"
                + "         ?,  board_seq.nextval,"
                + "         ?,?,?,"
                + "         sysdate)";
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "board", "1234");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, board.getBoard_name());
            pstmt.setString(2, board.getBoard_pass());
            pstmt.setString(3, board.getBoard_subject());
            pstmt.setString(4, board.getBoard_content());
            
            // 원문의 경우 BOARD_RE_LEV, BOARD_RE_SEQ 필드 값은 0입니다.
            pstmt.setInt(5, 0); // BOARD_RE_LEV 필드
            pstmt.setInt(6, 0); // BOARD_RE_SEQ 필드
            pstmt.setInt(7, 0); // BOARD_READCOUNT 필드
            
            result = pstmt.executeUpdate();
            
            
        } catch (Exception ex) {
            System.out.println("boardInsert() 에러: "+ ex);
            ex.printStackTrace();
        } 
        return result;
    }

    
    public ArrayList<Board> selectAll() {
        ArrayList<Board> list = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM (SELECT ROWNUM rnum, b.* " +
                "      FROM (SELECT * " +
                "            FROM BOARD " +
                "            ORDER BY board_re_ref desc, board_re_seq asc) b " +
                "      WHERE ROWNUM <= ?) " +
                "WHERE rnum BETWEEN ? AND ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "board", "1234");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Board board = new Board();
                board.setBoard_num(rs.getInt("board_num"));
                board.setBoard_name(rs.getString("board_name"));
                //board.setBoard_pass(rs.getString("board_pass"));
                board.setBoard_subject(rs.getString("board_subject"));
                board.setBoard_content(rs.getString("board_content"));
                board.setBoard_re_ref(rs.getInt("board_re_ref"));
                board.setBoard_re_lev(rs.getInt("board_re_lev"));
                board.setBoard_re_seq(rs.getInt("board_re_seq"));
                //board.setBoard_readcount(rs.getInt("board_readcount"));
                board.setBoard_date(rs.getString("board_date"));
                
                list.add(board);
            }
        } catch (SQLException ex) {
            System.out.println("selectAll() 에러: " + ex);
            ex.printStackTrace();
        }
        
        return list;
    }

    
    public List<Board> getBoardList(int page, int limit) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        
        // page : 페이지
        // limit : 페이지 당 목록의 수
        // BOARD_RE_REF DESC, BOARD_RE_SEQ ASC에 의해 정렬한 것을
        // 조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문이다.
        
        String board_list_sql =
                "SELECT * "
                + "FROM "
                + "        (SELECT rownum rnum,b.* "
                + "         from (select * from board "
                + "         order by BOARD_RE_REF DESC, BOARD_RE_SEQ ASC B"
                + "         where rownum<=?) "
                + "where rnum>=? and rnum<=?";
                                 
        List<Board> list = new ArrayList<Board>();
       
        int startRow = (page - 1) * limit + 1;
        int endRow = startRow + limit - 1;


        try {
            String driver = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            conn = DriverManager.getConnection(url, "board", "1234");
            pstmt = conn.prepareStatement(board_list_sql);
            pstmt.setInt(1, endRow);
            pstmt.setInt(2, startRow);
            pstmt.setInt(3, endRow);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Board board = new Board();
                board.setBoard_num(rs.getInt("board_num"));
                board.setBoard_name(rs.getString("board_name"));
                board.setBoard_pass(rs.getString("board_pass"));
                board.setBoard_subject(rs.getString("board_subject"));
                board.setBoard_content(rs.getString("board_content"));
                board.setBoard_re_ref(rs.getInt("board_re_ref"));
                board.setBoard_re_lev(rs.getInt("board_re_lev"));
                board.setBoard_re_seq(rs.getInt("board_re_seq"));
                //board.setBoard_readcount(rs.getInt("board_readcount"));
                board.setBoard_date(rs.getString("board_date"));

                list.add(board);
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            if (conn != null)
                try {
                    conn.close(); //4단계 : DB연결을 끊는다.
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
        }
        return list;
    }
    
    public int updateBoard(int boardNum, Board newBoard) {
        int result = 0;
        String sql = "UPDATE board " +
                     "SET board_name=?, board_pass=?, board_subject=?, board_content=? " +
                     "WHERE board_num=?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "board", "1234");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newBoard.getBoard_name());
            pstmt.setString(2, newBoard.getBoard_pass());
            pstmt.setString(3, newBoard.getBoard_subject());
            pstmt.setString(4, newBoard.getBoard_content());
            pstmt.setInt(5, boardNum);
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("updateBoard() 에러: " + ex);
            ex.printStackTrace();
        }
        
        return result;
    }
}
