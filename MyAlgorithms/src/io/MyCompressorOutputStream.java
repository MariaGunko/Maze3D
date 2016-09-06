package io;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	

	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = out;
	}


	@Override
	public void write(int fileLength) throws IOException {
		out.write(fileLength);
	}
	
	@Override
	public void write(byte [] arr) throws IOException {
		byte lastByte = arr[0];
		int k;
		for (k=0;k<9;k++)
		{
			out.write(lastByte);
			lastByte = arr[k+1];
		}
		int count = 1;
		
		for (int i=k;i<arr.length;i++){
			if (arr[i]!=lastByte)
			{
				while (count>=255){
					out.write(255);
					out.write(lastByte);
					count-=255;
				}
				out.write(count);
				out.write(lastByte);
				lastByte = arr[i];
				count=1;
			}
			else
			{
				count++;
			}
		}
		out.write(count);
		out.write(lastByte);
	}
}
