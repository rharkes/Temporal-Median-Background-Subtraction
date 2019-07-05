package nl.nkiavl.rharkes;
import static org.junit.Assert.*;

import org.junit.Test;
import io.scif.img.IO;
import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import nl.nkiavl.rharkes.TemporalMedian;
import nl.nkiavl.rharkes.TemporalMedian.RankMap;

public class testTemporalMedian {
	TemporalMedian TM = new TemporalMedian(); 
	@Test
	public void testRankMap() {
		Img< UnsignedShortType > img = IO.openImgs( System.getProperty("user.dir")+"\\src\\test\\java\\testfile.tif", new ArrayImgFactory<>( new UnsignedShortType() ) ).get( 0 );
		final RankMap rankmap = RankMap.build(img);
		short in;
		for (int i=0;i<1000;i++) {
			in = (short) i;
			assertEquals(in+1,rankmap.fromRanked(in));
		}
	}
	@Test
	public void testSubtractMedian() {
		Img< UnsignedShortType > img = IO.openImgs( System.getProperty("user.dir")+"\\src\\test\\java\\testfile.tif", new ArrayImgFactory<>( new UnsignedShortType() ) ).get( 0 );
		Img< UnsignedShortType > res = IO.openImgs( System.getProperty("user.dir")+"\\src\\test\\java\\resultfile.tif", new ArrayImgFactory<>( new UnsignedShortType() ) ).get( 0 );
		short window = 101;
		short offset = 100;
		long start = System.currentTimeMillis();
		TemporalMedian.run(img, window, offset);
		long end = System.currentTimeMillis();
		long RunTime = end-start;
		System.out.println("DEBUG: subtractMedian took "+RunTime+" ms");
		//check equality
		final Cursor< UnsignedShortType > curI = img.cursor();
		final Cursor< UnsignedShortType > curR = res.cursor();
		
		while ( curI.hasNext() )
        {
			curI.fwd();
            curR.fwd();
            assertEquals(curR.get().getShort(),curI.get().getShort());
        }
	}
}
