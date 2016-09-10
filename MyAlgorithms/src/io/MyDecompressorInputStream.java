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
		int m=0;
		// the loop reads couple of numbers - counter and the digit
		// for example 4,1
		while(m<arr.length){
			byte count = (byte) in.read();
			//System.out.print(count + " ");
			byte b =  (byte) in.read();
			//System.out.print(b + " ");
			// the loop saves into array the decompressed data
			// for example 1,1,1,1
			for (int k=0;k<count;k++){
				arr[m] = (byte) b;
				m++;
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
