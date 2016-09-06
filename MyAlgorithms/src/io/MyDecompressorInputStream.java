package io;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

	InputStream in;
	 
	public MyDecompressorInputStream(InputStream in) {
		super();
		this.in = in;
	}

	// returns the number of bytes we read from the file
	@Override
	public int read(byte[] arr) throws IOException {
		for (int i=0;i<6;i++)
		{
			byte p = (byte) in.read();
			arr[i] = p;
		}
		
		int m=6;
		// the loop reads couple of numbers - counter and the digit
		// for example 4,1
		while(m<arr.length){
			byte count = (byte) in.read();
			byte b = (byte) in.read();
			
			// the loop saves into array the decompressed data
			// for example 1,1,1,1
			for (int k=0;k<count;k++){
				arr[m++] = b;
			}
		}
		return arr.length;
	}

	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return in.read();
	}

}
