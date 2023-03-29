import java.sql.*;

public class UserDao {
//    public void create(User user) throws SQLException {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            con = ConnectionManager.getConnection();
//            String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1, user.getUserID());
//            pstmt.setString(2, user.getPassword());
//            pstmt.setString(3, user.getName());
//            pstmt.setString(4, user.getEmail());
//
//            pstmt.executeUpdate();
//        } finally {
//            if (pstmt != null) {
//                pstmt.close(); // 자원 해제
//            }
//
//            if (con != null) {
//                con.close(); //자원 해제
//            }
//        }
//    }

//    public User findByUserId(String userId) throws SQLException {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet resultSet = null;
//
//        try {
//            con = ConnectionManager.getConnection();
//            String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1, userId);
//
//            resultSet = pstmt.executeQuery();
//
//            User user = null;
//            if (resultSet.next()) {// 결과 값이 있다면
//                user = new User(resultSet.getString("userID"),
//                        resultSet.getString("password"),
//                        resultSet.getString("name"),
//                        resultSet.getString("email"));
//            }
//            return user;
//        }finally {
//            /*
//             * 자원을 만나는 순서대로 해제
//             */
//            if(resultSet != null){
//                resultSet.close();
//            }
//            if(pstmt != null){
//                pstmt.close();
//            }
//            if(con != null){
//                con.close();
//            }
//        }
//    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
        return (User)jdbcTemplate.executeQuery(sql,
                pstmt -> pstmt.setString(1, userId),
                resultSet -> new User(resultSet.getString("userID"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email")));
    }

    public void create(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.executeUpdate(user, sql, pstmt -> {
            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }
}
