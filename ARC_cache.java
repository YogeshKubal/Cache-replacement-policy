import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class ARC_cache 
{
	public static int i_cacheSize = 0;
	public static int i_cacheHit=0;
	public static int i_cacheMiss=0;
	public static int i_p=0;
	public static int i_del1, i_del2;
	public static int i_blockNumber;

	static LinkedList<Integer> T1 = new LinkedList<Integer>();
	static LinkedList<Integer> T2 = new LinkedList<Integer>();
	static LinkedList<Integer> B1 = new LinkedList<Integer>();
	static LinkedList<Integer> B2 = new LinkedList<Integer>();

	public static void main(String[] args)throws IOException 
	{
		String str_fileName;
		String str_line = null;
		String []str_fields;
		int i_startingBlock;
		int i_numberOfBlocks;
		boolean b_flag = true;

		try 
		{
			//Scanner read = new Scanner(System.in);
			//System.out.println("Enter cache size =");
			//i_cacheSize = read.nextInt();
			i_cacheSize = Integer.parseInt(args[0]);
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
				//System.out.println("Enter File name =");
				//str_fileName = readFile.nextLine();
				str_fileName = args[1];
				FileReader fileRead = new FileReader(str_fileName);
				BufferedReader bufferedReader = new BufferedReader(fileRead);

				while((str_line = bufferedReader.readLine()) != null)
				{
					str_fields = str_line.split(" ");
					i_startingBlock = Integer.parseInt(str_fields[0]);
					i_numberOfBlocks = Integer.parseInt(str_fields[1]);

					for(int i=0; i<i_numberOfBlocks; i++)
					{
						i_blockNumber = i_startingBlock + i;

						if(!(T1.contains(i_blockNumber))&&!(T2.contains(i_blockNumber)))
						{
							i_cacheMiss++;
							if(!(B1.contains(i_blockNumber))&&!(B2.contains(i_blockNumber)))
							{
								if((T1.size()+B1.size())==i_cacheSize)
								{
									if(T1.size()<i_cacheSize)
									{
										B1.removeLast();
										replaceBlock();
									}
									else
									{
										T1.removeLast();
									}
								}

								else if((T1.size()+B1.size())<i_cacheSize)
								{
									if((T1.size()+T2.size()+B1.size()+B2.size())>=i_cacheSize)
									{
										if((T1.size()+T2.size()+B1.size()+B2.size())==2*i_cacheSize)
										{
											B2.removeLast();
										}
										replaceBlock();
									}
								}
								T1.addFirst(i_blockNumber);
							}
							else if(B1.contains(i_blockNumber)&&!(B2.contains(i_blockNumber)))
							{
								if(B1.size()>=B2.size())
								{
									i_del1=1;
								}
								else{
									i_del1=(B2.size()/B1.size());
								}

								if((i_p+i_del1)<=i_cacheSize)
								{
									i_p = (i_p+i_del1);
								}
								else{
									i_p = i_cacheSize;
								}

								replaceBlock();
								B1.remove(B1.indexOf(i_blockNumber));
								T2.addFirst(i_blockNumber);
							}
							else if(!(B1.contains(i_blockNumber))&&(B2.contains(i_blockNumber)))
							{
								if(B2.size()>=B1.size())
								{
									i_del2=1;
								}
								else{
									i_del2=(B1.size()/B2.size());
								}

								if((i_p-i_del2)>=0)
								{
									i_p = (i_p-i_del2);
								}
								else{
									i_p = 0;
								}
								replaceBlock();
								B2.remove(B2.indexOf(i_blockNumber));
								T2.addFirst(i_blockNumber);
							}
						}

						else if((T1.contains(i_blockNumber))||(T2.contains(i_blockNumber)))
						{
							i_cacheHit++;
							if(T1.contains(i_blockNumber))
							{
								T1.remove(T1.indexOf(i_blockNumber));
								T2.addFirst(i_blockNumber);
							}
							else
							{
								T2.remove(T2.indexOf(i_blockNumber));
								T2.addFirst(i_blockNumber);
							}
						}
					}
				}

				System.out.println("Cache Replacement Policy : ARC");
				System.out.println("Cache Size = "+i_cacheSize);
				System.out.println("File Name = "+str_fileName);
				System.out.println("Cache Hits = "+i_cacheHit); 
				System.out.println("Cache Miss = "+i_cacheMiss);
				float hitRatio = ((float)i_cacheHit*100)/((float)i_cacheHit+(float)i_cacheMiss);
				System.out.println("Hit Ratio = " +hitRatio );
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


	public static void replaceBlock() 
	{
		if((!(T1.size()==0))&&(T1.size()>i_p||(B2.contains(i_blockNumber)&&T1.size()==i_p)))
		{
			B1.addFirst(T1.getLast());
			T1.removeLast();
		}
		else
		{
			B2.addFirst(T2.getLast());
			T2.removeLast();
		}
	}
}

