HTTP协议要求浏览器连接服务端后应当发送一个请求，因此本版本实现读取请求并输出到控制台来了解请求
的格式和内容。

实现:
由于服务端可以同时接收多客户端的连接，因此与聊天室相同，主线程仅负责接受客户端的连接，一旦一个
客户端连接后则启动一个线程来处理。
1:在com.webserver.core下新建类:com.webserver.core.ClientHandler(实现Runnable接口)，作为线程任务。
  工作是负责与连接的客户端进行HTTP交互
2:WebServerApplication主线程接收连接后启动线程执行ClientHandler这个任务处理客户端交互
3:在ClientHandler中读取客户端发送过来的内容(请求内容)并打桩输出
