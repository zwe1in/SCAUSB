package com.xumu.programmer.util.pool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


/*** @Desc 名字服务器连接信息
*/

public class SocketInfo {

   /**
    * socket
    */
   private Socket socket;
   
   /**
    * 是否空闲 （是：true  否：false）
    */
   private boolean isFree;
   
   /**
    * socket id
    */
   private Integer socketId;
   
   /**
    * 是否为可关闭链接 （是：true  否：false）
    */
   private boolean isClosed;

   public Socket getSocket() {
       return socket;
   }

   public void setSocket(Socket socket) {
       this.socket = socket;
   }

   public boolean isFree() {
       return isFree;
   }

   public void setFree(boolean isFree) {
       this.isFree = isFree;
   }

   public Integer getSocketId() {
       return socketId;
   }

   public void setSocketId(Integer socketId) {
       this.socketId = socketId;
   }

   public boolean isClosed() {
       return isClosed;
   }

   public void setClosed(boolean isClosed) {
       this.isClosed = isClosed;
   }

   
   public void write(byte[] msg) throws IOException {
       OutputStream out = socket.getOutputStream();
       
       if ((0 | msg.length | (msg.length - (msg.length + 0)) | (0 + msg.length)) < 0)
           throw new IndexOutOfBoundsException();

       for (int i = 0 ; i < msg.length ; i++) {
           out.write(msg[0 + i]);
       }
       
       socket.shutdownOutput();//关闭输出流，不关闭socket
   }
   
   public byte[] readByte(byte[] bytes) throws IOException {
       InputStream in = socket.getInputStream();
       in.read(bytes, 0, bytes.length);
       socket.shutdownInput();//关闭输入流，不关闭socket
       return bytes;
   }

}
