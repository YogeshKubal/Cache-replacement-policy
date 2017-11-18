import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class LRU_cache
{
	public static int i_cacheSize = 0;
	public static int i_cacheHit=0;
	public static int i_cacheMiss=0;

	public static void main(String[] args)throws IOException 
	{
		LinkedList<Integer> list = new LinkedList<Integer>();
		String str_fileName = null;
		String str_line = null;
		String []str_fields;
		int i_startingBlock;
		int i_numberOfBlocks;
		int i_blockNumber;
		boolean b_flag = true;

		try 
		{
			//Scanner read = new Scanner(System.in);
			//System.out.println("Enter cache size =");
			//i_cacheSize = read.nextInt();
			i_cacheSize = Integer.parseInt(args[0]);
			//b_flag2=false;
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			System.out.println("Please enter integer value for cache size");
			b_flag = false;				
		}

		while(b_flag)
		{

			try{
				//Scanner readFile = new Scanner(System.in);
				//System.out.println("Enter File name along with the file path:");
				//str_fileName = readFile.nextLine();
				str_fileName = args[1];
				FileReader fileRead = new FileReader(str_fileName);
				BufferedReader bufferedReader = new BufferedReader(fileRead);

				while((str_line = bufferedReader.readLine()) != null){
					str_fields = str_line.split(" ");
					i_startingBlock = Integer.parseInt(str_fields[0]);
					i_numberOfBlocks = Integer.parseInt(str_fields[1]);

					for(int i=0; i<i_numberOfBlocks; i++)
					{
						i_blockNumber = i_startingBlock + i;
						if(list.contains(i_blockNumber))
						{
							i_cacheHit++;
							list.remove(list.indexOf(i_blockNumber));
							list.addFirst(i_blockNumber);
						}
						else
						{	
							i_cacheMiss++;
							if(list.size()<=i_cacheSize)
							{
								list.addFirst(i_blockNumber);
							}
							else 
							{
								list.removeLast();
								list.addFirst(i_blockNumber);
							}
						}
					}
				}

				System.out.println("Cache Replacement Policy : LRU");
				System.out.println("Cache Size = "+i_cacheSize);
				System.out.println("File Name = "+str_fileName);
				System.out.println("Cache Hits = "+i_cacheHit); 
				System.out.println("Cache Miss = "+i_cacheMiss);
				float hitRatio = ((float)i_cacheHit*100)/((float)i_cacheHit+(float)i_cacheMiss);
				System.out.println("Hit Ratio = "+hitRatio);
				System.out.print("Hit Ratio after rounding up to 2 decimals: ");
				System.out.printf("%.2f", hitRatio);
				System.out.println(" ");
				b_flag = false;
			}
			catch(IOException e){
				//e.printStackTrace();
				System.out.println(e.toString()+"\n");
				b_flag = false;				
			}
		}
	}
}