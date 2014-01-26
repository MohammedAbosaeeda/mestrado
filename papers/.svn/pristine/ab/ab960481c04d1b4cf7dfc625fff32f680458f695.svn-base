package hex_parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class hex_parser {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	
	public static int littleEndian16ToInt(byte buffer[], int offset){
		return (buffer[offset+1] << 8) | buffer[offset];
	}
	
	public static long littleEndian32ToLong(byte buffer[], int offset){
		return (buffer[offset+3] << 24) | 
			   (buffer[offset+2] << 16) | 
			   (buffer[offset+1] << 8) |
			   buffer[offset];
	}
	
	
	public static void parseStats(byte buffer[]){
	/*
	unsigned int rx_packets; 2 bytes
	unsigned int tx_packets;
	unsigned int rx_bytes;
	unsigned int tx_bytes;
	unsigned long tx_time; 4 bytes
	unsigned long rx_time;
	unsigned int dropped_packets;
	unsigned int total_tx_packets;
	 */
		
	System.out.println("rx_packets = "+littleEndian16ToInt(buffer, 0));
	System.out.println("tx_packets = "+littleEndian16ToInt(buffer, 2));
	System.out.println("rx_bytes = "+littleEndian16ToInt(buffer, 4));
	System.out.println("tx_bytes = "+littleEndian16ToInt(buffer, 6));
	System.out.println("tx_time = "+littleEndian32ToLong(buffer, 8));
	System.out.println("rx_time = "+littleEndian32ToLong(buffer, 12));
	System.out.println("dropped_packets = "+littleEndian16ToInt(buffer, 16));
	System.out.println("total_tx_packets = "+littleEndian16ToInt(buffer, 18));
		
	}
	
	public static void main(String[] args) throws IOException {
		
		FileInputStream is = new FileInputStream(new File(args[0]));
				
		
		System.out.println("Address = "+is.read());
		
		byte buffer[] = new byte[20];
		is.read(buffer);
				
		parseStats(buffer);
		
	}

}
