package com.xumu.programmer.util;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.xumu.programmer.util.pool.SocketInfo;
import com.xumu.programmer.util.pool.SocketPool;

public class CommandSender {
	
	private static final byte[] secretLanguage = {(byte) 0x8f, (byte) 0xff}; //转发标记，放在内容前
    private final static byte[] HEAD = {(byte) 0xFE, (byte) 0xEF, (byte) 0xED, (byte) 0xFC};//帧头
    private final static byte[] TAIL = {(byte) 0xFD, (byte) 0xEC, (byte) 0xF8, (byte) 0xF1};//帧尾
    
    private static Logger logger = Logger.getLogger(CommandSender.class);
    
    private static int commIdInt = 0;
    private static byte[] commId = {(byte) 0x00, (byte) 0x00};//通讯ID
//    private static byte[] equipId = {(byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x01};//设备ID,从外部传入
	
    private SocketInfo socket;
    public CommandSender(SocketInfo socket) {
		this.socket = socket;
	}
    
    public void distorySocket() {
    	SocketPool.distorySocket(socket);
    }

    public synchronized void updateComId() {
    	if (commIdInt > 65024) {
    		commIdInt = 0;
    	}
    	
    	commId = ByteArrayParser.intToLowBeforeByteArray(commIdInt);
    }
    
    public byte[][] packByteAraysToBytes(byte[] equipId, byte[] content) {
    	byte[][] bs = {HEAD, commId, equipId, secretLanguage, content, TAIL};
    	return bs;
    }
    
	public byte[] send(byte[] equipId, byte[] content) {
		updateComId();
		byte[] commandArray = concatByteArraysToByte(packByteAraysToBytes(equipId, content));
		try {
			socket.write(commandArray);
			
			socket.getSocket().setSoTimeout(5000);
			byte[] res = new byte[600];
			socket.readByte(res);
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public byte[] concatByteArray(byte[] array1, byte[] array2) {
	       byte[] res = new byte[array1.length + array2.length];
	       System.arraycopy(array1, 0, res, 0, array1.length);
	       System.arraycopy(array2, 0, res, array1.length, array2.length);
	       return res;
    }
	
	public byte[] concatByteArraysToByte(byte[][] byteArrays) {
		byte[] temp = {};
        for (int i = 0; i < byteArrays.length; i ++) {
            temp = concatByteArray(temp, byteArrays[i]);
        }
        
        return temp;
	}
}
