import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    public void executeUpdate(User user, String sql, PreparedStatementSetter pst) throws SQLException {
        Connection con = null;
        /**
         * 왜 pstmt를 밖에서 받아오지 않지?
         * pstmt는 Connection으로부터 받아오는 거임. 그럼 외부 객체가 Connection도 알고 있어야 함.
         * 즉, 캡슐화가 약해지고 결합도가 높아짐.
         * 근데 이제 외부에서 받아와볼게, 어떻게? setter만 인자로 받게 만들어서. (pst)
         */
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
//          String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)"; 얘가 user가 아니면 어떢할거야? 외부로부터 sql 받아오면 되지~

            pstmt = con.prepareStatement(sql);
            pst.setter(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close(); // 자원 해제
            }

            if (con != null) {
                con.close(); //자원 해제
            }
        }
    }

    public Object executeQuery(String sql, PreparedStatementSetter pst, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pst.setter(pstmt);

            resultSet = pstmt.executeQuery();

            Object obj = null;
            if (resultSet.next()) {// 결과 값이 있다면
                return rowMapper.map(resultSet);
            }
            return obj;
        }finally {
            /*
             * 자원을 만나는 순서대로 해제
             */
            if(resultSet != null){
                resultSet.close();
            }
            if(pstmt != null){
                pstmt.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
}
