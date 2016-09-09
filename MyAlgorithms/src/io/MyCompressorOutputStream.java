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
		byte lastByte;
		//int k;
		
//		for (k=0;k<9;k++)
//		{
//			lastByte = arr[k];
//			out.write(lastByte);
//			System.out.print(lastByte  + " ");
//		}
		
		int count = 1;
		lastByte = arr[0];
		for (int i=1;i<arr.length;i++){
			if (arr[i]!=lastByte)
			{
				while (count>=255){
					out.write(255);
					out.write(lastByte);
					count-=255;
				}
				out.write(count);
				System.out.print(count + " ");
				out.write(lastByte);
				System.out.print(lastByte + " ");
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
