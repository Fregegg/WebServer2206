重构代码
将ClientHandler中发送响应的工作拆分出去

实现:
1:在com.webserver.http包中新建类:com.webserver.http.HttpServletResponse 响应对象
2:在响应对象中定义对应的属性来保存响应内容并定义response方法来发送响应.
3:修改ClientHandler,使用响应对象完整响应的发送