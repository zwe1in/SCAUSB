package com.xumu.programmer.util.pool;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
* @Desc 分布式名字服务器socket链接池
*/

public class SocketPool {
   
   private static Logger logger = Logger.getLogger(SocketPool.class);//日志类
   
   /**
    * socketMap
    */
   public static ConcurrentHashMap<Integer, SocketInfo> socketMap = new ConcurrentHashMap<Integer, SocketInfo>();
   
   private static SocketPool instance = new SocketPool();
   
   private SocketPool(){}
   
   public static SocketPool getInstance(){
       if(instance == null){
           synchronized (SocketPool.class) {
               if(instance == null){
                   instance = new SocketPool();
               }
           }
       }
       return instance;
   }
   
   static {
       instance.initSocket(true);
   }
   
   /**
    * @Desc 初始化链接池
    * @param isAllReInit  是否全部重新初始化
    * @return void
    */
   public void initSocket(boolean isAllReInit){
       int defaultCount = ConfigConst.SOCKET_DEFAULT_COUNT;
       logger.info("nameserver:initSocket 开始初始化分布式名字服务器连接数：" + defaultCount);
       for (int i = 0; i < defaultCount; i++) {                    
           
           if(isAllReInit){
               socketMap.put(i, setSocketInfo( i, true, false));
           } else {
               if(socketMap.get(i) == null || socketMap.get(i).isClosed()){
                   socketMap.put(i, setSocketInfo( i, true, false));
               }
           }   
       }
       
       logger.info("nameserver:initSocket完成初始化分布式名字服务器连接数");
       new CheckSocketThread().start();
       
   }
   
   /**
    * @Desc 设置socketInfo值
    * @param socket
    * @param key
    * @param isFree
    * @param isClosed
    * @return SocketInfo
    */
   private static SocketInfo setSocketInfo(Integer key, boolean isFree, boolean isClosed){
       SocketInfo socketInfo = new SocketInfo();
       Socket socket = createSocket();
       socketInfo.setFree(isFree);
       socketInfo.setSocket(socket);
       socketInfo.setSocketId(key);
       socketInfo.setClosed(isClosed);     
       return socketInfo;
   }
   
   /**
    * @Desc 获取名字服务器链接
    * @return
    * SocketInfo
    */
   public SocketInfo getSocketInfo(){
       
       SocketInfo socketInfo = null;
       
       if(socketMap.size() < ConfigConst.SOCKET_DEFAULT_COUNT){
           initSocket(false);
       }       
   
       if(socketMap.size() > 0){
           for (Map.Entry<Integer, SocketInfo> entry : socketMap.entrySet()) {
               socketInfo = entry.getValue();
               if(socketInfo.isFree() && ! socketInfo.getSocket().isClosed()){
                   socketInfo.setFree(false);
                   return socketInfo;
               }
           }
       } else {
           logger.info("nameserver:socketInfo false 名字服务器socket连接池数量为零。");
           return null;
       } 
       
       logger.info("nameserver:socketInfo 所有名字服务器socket链接都忙，创建临时链接。");
               
       socketInfo = setSocketInfo(-1, true, true);
       logger.info("nameserver:socketInfo 成功创建服务器socket临时链接。"); 
       return socketInfo;

   }
   
   /**
    * 释放socket
    * @param socketId
    */
   public static void distorySocket(Integer socketId){
       
       logger.debug("nameserver:distorySocket 释放名字服务器socket链接。");
       SocketInfo socketInfo = socketMap.get(socketId);
       socketInfo.setFree(true);
       
   }
   
   /**
    * @Desc 释放socket
    * @param socketInfo
    * void
    */
   public static void distorySocket(SocketInfo socketInfo){
      
       if(socketInfo == null) return;
       
       if( ! socketInfo.isClosed()){
           logger.debug("nameserver:distorySocket 链接池socket，标记为可用。");
           distorySocket(socketInfo.getSocketId());
           return;
       }
       
       logger.debug("nameserver:distorySocket 可关闭临时链接，关闭socket");
       try {
           if(socketInfo.getSocket() != null){
               socketInfo.getSocket().close();
           }
       } catch (IOException e) {
           logger.error("nameserver:distorySocket 关闭名字服务器socket链接失败", e);
       }
       socketInfo = null;
       
   }
   
   /**
    * @Desc 创建socket
    * @return
    * Socket
    */
   public static Socket createSocket(){
       
       String nameServerip = ConfigConst.SERVER_IP;
       int namServerport = ConfigConst.SERVER_PORT;
       
       Socket socket = null;
       
       
       try {// 尝试通过ip1建立连接
           socket = new Socket(nameServerip, namServerport);
           socket.setSoTimeout(ConfigConst.TIME_OUT);
           logger.info("nameserver:login nameServerip:" + nameServerip + ", namServerport:" + namServerport);
       } catch (IOException e) {           
           logger.error("nameserver:login first link fail nameServerip:" + nameServerip + ", namServerport:" + namServerport, e);
       }
       return socket;
   }
   
   class CheckSocketThread extends Thread{
       @Override
       public void run() {
           while (true) {
               logger.debug("nameserver:checkSocket 开始检测分布式链接状态。");
               if(socketMap.size() < ConfigConst.SOCKET_DEFAULT_COUNT){
                   logger.info("nameserver:checkSocket 分布式名字服务器socket链接小于默认链接数，增加socket链接。");
                   initSocket(false);
               }
               
               for (Map.Entry<Integer, SocketInfo> entry : socketMap.entrySet() ) {
                   SocketInfo socketInfo = entry.getValue();
                   if(socketInfo.getSocket() == null || socketInfo.getSocket().isClosed()){
                       logger.error("nameserver:checkSocket 第"+ entry.getKey()+"个socket链接已关闭，重新连接分布式。",null);
                       socketInfo.setSocket(createSocket());
                   } else {
//                       if(socketInfo.isFree()){
//                           boolean flag = socketInfo.isFree();
//                           if( ! flag ){
//                               logger.error("nameserver:checkSocket 第"+ entry.getKey()+"个socket链接失败，重新连接分布式。",null);
//                               socketInfo.setSocket(createSocket());
//                               continue;
//                           }
//                       }
                       logger.debug("nameserver:checkSocket 第"+ entry.getKey()+"个socket链接正常。");
                   }
               }
               
               try {
                   sleep(Long.valueOf(ConfigConst.SOCKET_CHECK_TIME));
               } catch (Exception e) {
               } 
           }
       }
   }
   
}
