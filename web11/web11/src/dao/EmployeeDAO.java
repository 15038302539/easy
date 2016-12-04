package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import entity.Employee;
//test
public class EmployeeDAO {
	public void save(Employee e) throws Exception{
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = DBUtil.getConnection();
			prep = conn.prepareStatement(
					"INSERT INTO emp(name,salary,age) " +
					"VALUES(?,?,?)");
			prep.setString(1, e.getName());
			prep.setDouble(2, e.getSalary());
			prep.setInt(3, e.getAge());
			prep.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}finally{
			DBUtil.close(conn);
		}
	}
	
	public void delete(int id) throws Exception{
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = DBUtil.getConnection();
			prep = conn.prepareStatement(
					"DELETE FROM emp WHERE id=?");
			prep.setInt(1, id);
			prep.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(conn);
		}
	}
	
	public Employee findById(int id) throws Exception{
		Employee e = null;
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rst = null;
		try {
			conn = DBUtil.getConnection();
			prep = conn.prepareStatement(
					"SELECT * FROM emp WHERE id=?");
			prep.setInt(1, id);
			rst = prep.executeQuery();
			if(rst.next()){
				String name = rst.getString("name");
				double salary = rst.getDouble("salary");
				int age = rst.getInt("age");
				e = new Employee();
				e.setId(id);
				e.setName(name);
				e.setSalary(salary);
				e.setAge(age);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}finally{
			DBUtil.close(conn);
		}
		return e;
	}
	
	public void modify(Employee e) throws Exception{
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = DBUtil.getConnection();
			prep = conn.prepareStatement(
					"UPDATE emp SET name=?," +
					"salary=?,age=? WHERE id=?");
			prep.setString(1, e.getName());
			prep.setDouble(2, e.getSalary());
			prep.setInt(3, e.getAge());
			prep.setInt(4, e.getId());
			prep.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}finally{
			DBUtil.close(conn);
		}
	}
	
	public List<Employee> findAll() throws Exception{
		List<Employee> employees = new ArrayList<Employee>();
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rst = null;
		try {
			conn = DBUtil.getConnection();
			prep = conn.prepareStatement("SELECT * FROM emp");
			rst = prep.executeQuery();
			while(rst.next()){
				int id = rst.getInt("id");
				String name = rst.getString("name");
				double salary = rst.getDouble("salary");
				int age = rst.getInt("age");
				Employee e = new Employee();
				e.setId(id);
				e.setName(name);
				e.setSalary(salary);
				e.setAge(age);
				employees.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(conn);
		}
		return employees;
	}
}
