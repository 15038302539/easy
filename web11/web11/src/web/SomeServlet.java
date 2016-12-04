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
			//���������Դ·��
			String uri = request.getRequestURI();
			System.out.println("uri:" + uri);
			String action = uri.substring(
					uri.lastIndexOf("/"),uri.lastIndexOf("."));
			System.out.println("action:" + action);
			if("/list".equals(action)){
				try {
					EmployeeDAO dao = new EmployeeDAO();
					List<Employee> employees = dao.findAll();
					//��Ϊservlet�����ó����ɸ��ӵ�ҳ�棬����
					//ת����jsp����ɡ�
					//step1,�����ݵ�request
					request.setAttribute("employees", employees);
					//step2,���ת����
					RequestDispatcher rd = 
						request.getRequestDispatcher("listEmp.jsp");
					//step3,ת��
					rd.forward(request, response);
				}catch(Exception e){
					e.printStackTrace();
					throw  new ServletException(e);
				}
			}else if("/add".equals(action)){
				String name = request.getParameter("name");
				String salary = request.getParameter("salary");
				String age = request.getParameter("age");
				//��Ա����Ϣ���뵽���ݿ�
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
				//ɾ�����ݿ���ָ��id��Ա��
				try {
					EmployeeDAO dao = new EmployeeDAO();
					dao.delete(id);
					//�ض���Ա���б�
					response.sendRedirect("list.do");
				}catch(Exception e){
					e.printStackTrace();
					throw new ServletException(e);
				}
			}else if("/load".equals(action)){
				int id = Integer.parseInt(request.getParameter("id"));
				//����id����Ա����Ϣ
				try {
					EmployeeDAO dao = new EmployeeDAO();
					Employee e = dao.findById(id);
					//ת����jsp�������޸�ҳ��
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
				// ������ֵ�����...
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
