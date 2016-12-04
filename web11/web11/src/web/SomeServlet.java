package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDAO;
import entity.Employee;

public class SomeServlet extends HttpServlet {

	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			//获得请求资源路径
			String uri = request.getRequestURI();
			System.out.println("uri:" + uri);
			String action = uri.substring(
					uri.lastIndexOf("/"),uri.lastIndexOf("."));
			System.out.println("action:" + action);
			if("/list".equals(action)){
				try {
					EmployeeDAO dao = new EmployeeDAO();
					List<Employee> employees = dao.findAll();
					//因为servlet不反擅长生成复杂的页面，所以
					//转发给jsp来完成。
					//step1,绑订数据到request
					request.setAttribute("employees", employees);
					//step2,获得转发器
					RequestDispatcher rd = 
						request.getRequestDispatcher("listEmp.jsp");
					//step3,转发
					rd.forward(request, response);
				}catch(Exception e){
					e.printStackTrace();
					throw  new ServletException(e);
				}
			}else if("/add".equals(action)){
				String name = request.getParameter("name");
				String salary = request.getParameter("salary");
				String age = request.getParameter("age");
				//将员工信息插入到数据库
				try {
					EmployeeDAO dao = new EmployeeDAO();
					Employee e = new Employee();
					e.setName(name);
					e.setSalary(Double.parseDouble(salary));
					e.setAge(Integer.parseInt(age));
					dao.save(e);
					response.sendRedirect("list.do");  
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServletException(e);
				}
			}else if("/del".equals(action)){
				int id = Integer.parseInt(request.getParameter("id"));
				//删除数据库中指定id的员工
				try {
					EmployeeDAO dao = new EmployeeDAO();
					dao.delete(id);
					//重定向到员工列表
					response.sendRedirect("list.do");
				}catch(Exception e){
					e.printStackTrace();
					throw new ServletException(e);
				}
			}else if("/load".equals(action)){
				int id = Integer.parseInt(request.getParameter("id"));
				//依据id查找员工信息
				try {
					EmployeeDAO dao = new EmployeeDAO();
					Employee e = dao.findById(id);
					//转发给jsp来生成修改页面
					request.setAttribute("e", e);
					RequestDispatcher rd = 
						request.getRequestDispatcher("updateEmp.jsp");
					rd.forward(request, response);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServletException(e);
			}
			}else if("/modify".equals(action)){
				int id = Integer.parseInt(request.getParameter("id"));
				String name = request.getParameter("name");
				String salary = request.getParameter("salary");
				String age = request.getParameter("age");
				// 检查参数值的语句...
				try {
					EmployeeDAO dao = new EmployeeDAO();
					Employee e = new Employee();
					e.setId(id);
					e.setName(name);
					e.setSalary(Double.parseDouble(salary));
					e.setAge(Integer.parseInt(age));
					dao.modify(e);
					response.sendRedirect("list.do");
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServletException(e);
				}
			}
	}

}
