package com.example.hellojdbc.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class StudentRepository {

    private final DataSource dataSource;

    public StudentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Student save(Student student) throws SQLException {
        String sql = "insert into student(student_id, student_name) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = dataSource.getConnection();
            log.info("get connection={}, class={}", con, con.getClass());

            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, student.getStudentId());
            pstmt.setString(2, student.getStudentName());
            pstmt.executeUpdate();
            return student;
        } catch (SQLException e) {
            log.error("db error: ", e);
            throw e;
        } finally {
            JdbcUtils.closeStatement(pstmt);
            JdbcUtils.closeConnection(con);
        }
    }

    public Student findById(Long studentId) throws SQLException {
        String sql = "select * from student where student_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, studentId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                Student student = new Student(rs.getLong("student_id"), rs.getString("student_name"));
                return student;
            } else {
                throw new NoSuchElementException("member not found student_id=" + studentId);
            }
        } catch (SQLException e) {
            log.error("db error: " + e);
            throw e;
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
            JdbcUtils.closeConnection(con);
        }
    }
}
