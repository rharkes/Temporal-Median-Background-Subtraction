package nl.nkiavl.rharkes;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class testMedianHistogram {

	@Test
	public void testMediaHistogramBasic(){
		//generate {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22} median=11; aux=1;
		int values = 23; 
		Short[] data = new Short[values];
		Short[] dataQS = new Short[values];
		MedianHistogram medFind = new MedianHistogram(23, 22);
		for (int i = 0;i<data.length;i++) {
			data[i]=(short)i;
			medFind.add(data[i]);
		}
		System.arraycopy(data,0,dataQS,0,values);
		short M2 = quickSelect.select(dataQS, dataQS.length/2); //first copy array since quickselect changes the order of the data
		assertEquals("quickSelect Fails",(short)11,M2);
		assertEquals("medianFindingHistogram Fails",(short)11,medFind.get());
		assertEquals("medianFindingHistogram aux test fails",(short)1,medFind.getaux());
		data[0]=data[values-1]; //remove a 0, add a 22
		M2 = quickSelect.select(data, data.length/2);
		assertEquals("quickSelect Fails",(short)12,M2);
		medFind.add((short)22); //remove 0
		assertEquals("medianFindingHistogram Fails",(short)12,medFind.get());
		assertEquals("medianFindingHistogram aux test fails",(short)1,medFind.getaux());
		
		//generate {22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0} median=11; aux=1;
		MedianHistogram medFind2 = new MedianHistogram(23, 22);
		for (int i = 0;i<data.length;i++) {
			data[i]=(short) (values-i-1);
			medFind2.add(data[i]);
		}
		System.arraycopy(data,0,dataQS,0,values);
		M2 = quickSelect.select(dataQS, dataQS.length/2); 
		assertEquals("quickSelect Fails",(short)11,M2);
		assertEquals("medianFindingHistogram aux test fails",(short)1,medFind2.getaux());
		assertEquals("medianFindingHistogram Fails",(short)11,medFind2.get());
		
		System.out.print(String.format("replace %d with %d", data[0],data[22]));
		data[0]=data[values-1]; //remove a 22, add a 0
		System.arraycopy(data,0,dataQS,0,values);
		M2 = quickSelect.select(dataQS, dataQS.length/2);
		assertEquals("quickSelect Fails",(short)10,M2);
		medFind2.add((short)0); //remove 22, add 0
		assertEquals("medianFindingHistogram aux test fails",(short)1,medFind2.getaux());
		assertEquals("medianFindingHistogram Fails",(short)10,medFind2.get());
	}
	@Test
	public void testMedianHistogram1(){ //1000 initializations, no replacement in each
		for (int repeats = 0;repeats<1000;repeats++) {
			//initialize histogram
			int values = 1000;
			int dataLength = 30001;
			Random generator=new Random();
			Short[] data = new Short[dataLength];
			MedianHistogram medFind = new MedianHistogram(dataLength, values);			
			for (int i = 0;i<data.length;i++) {
				data[i] = (short) generator.nextInt(values);
				medFind.add(data[i]);
			}
			short M = quickSelect.select(data, data.length/2);
			assertEquals("failed after initialization",M,medFind.get());
		}
	}
	@Test
	public void testMedianHistogram2(){ //one initialization, 1000 replacements
		//initialize histogram
		int values = 20000;
		int dataLength = 3001;
		Random generator=new Random();
		Short[] data = new Short[dataLength];
		Short[] dataQS = new Short[dataLength];
		MedianHistogram medFind = new MedianHistogram(dataLength, values);
		for (int i = 0;i<data.length;i++) {
			data[i] = (short) generator.nextInt(values);
			medFind.add(data[i]);
		}
		System.arraycopy(data,0,dataQS,0,dataLength);
		short M = quickSelect.select(dataQS, dataQS.length/2);
		assertEquals("failed after initialization",M,medFind.get());
		for (int repeats = 0;repeats<1000;repeats++) {
			//add and remove value and recalculate median
			short addValue = (short) generator.nextInt(values);
			medFind.add(addValue);
			data[repeats%dataLength] = (Short) addValue;
			System.arraycopy(data,0,dataQS,0,dataLength);
			short M2 = quickSelect.select(dataQS, dataQS.length/2);
			assertEquals(String.format("failed after %d replacements", repeats+1),M2,medFind.get());
		}
	}
	@Test
	public void timeMedianHistogram3() {
		int values = 1000;      //unique intensities
		int histLength = 50000; //frames
		short window = 501;     //window size
		Random generator=new Random();
		//generate data
		Short[] data = new Short[histLength];
		for (int i = 0;i<data.length;i++) {
			data[i] = (short) generator.nextInt(values);
		}
		short[] medians1 = new short[histLength-window];
		short[] medians2 = new short[histLength-window];
		//Time quickSelect
		long start = System.currentTimeMillis();
		Short[] dataWindow = new Short[window]; 
		for (int i = 0;i<medians1.length;i++) {
			for (int j = i;j<(i+window);j++) {
				dataWindow[j-i]=data[j];
			}
			medians1[i]=quickSelect.select(dataWindow, dataWindow.length/2);
		}
		long end = System.currentTimeMillis();
		long runTimeQuickSelect = end-start;
		System.out.println("DEBUG: quickSelect took " + runTimeQuickSelect + " MilliSeconds");
		//Time medianFindingHistogram
		start = System.currentTimeMillis();
		MedianHistogram medFind = new MedianHistogram(window, values);
		for (int i = 0;i<histLength;i++) {
			if (i>=window) {medians2[i-window]=medFind.get();}
			medFind.add(data[i]);
		}
		end = System.currentTimeMillis();
		long runTimeMedianFindingHistogram = end-start;
		System.out.println("DEBUG: medianFindingHistogram took " + runTimeMedianFindingHistogram+ " MilliSeconds");
		System.out.println("DEBUG: medianFindingHistogram " + runTimeQuickSelect/runTimeMedianFindingHistogram+ " times faster");
		
		assertArrayEquals(medians1,medians2);
	}

}
