package xswingver2;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;


public class ImageUtil {
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//new ImageUtil().myAdaptImages("D:\\tmp\\resources","D:\\tmp\\resources_120_100","_t");
		//new ImageUtil().myAdaptImages("D:\\tmp\\resources","D:\\tmp\\r","");
		//new ImageUtil().myGrayImages("D:\\tmp\\r","D:\\tmp","_f");
		//ImageUtil.setAlphaImage("D:\\tmp\\resources\\fujian.jpg","D:\\tmp\\fujianAlpha.jpg",100);
		//ImageUtil.setGray("D:\\tmp\\resources\\fujian.jpg","D:\\tmp\\fujianGrap.jpg");
		ImageUtil.setGray("D:\\tmp\\resources\\zhongguo.jpg","D:\\tmp\\zhongguo.jpg");
	}
	
	public void myAdaptImages(String srcDir,String dstDir,String extStr) throws IOException {
		int width = 120, height = 100;
		/*
		Calendar calc = Calendar.getInstance();
		String year = String.valueOf(calc.get(Calendar.YEAR));
		String month = String.valueOf(calc.get(Calendar.MONTH) + 1);
		*/
		Path srcPath = Paths.get(srcDir);
		try (DirectoryStream<Path> entries = Files.newDirectoryStream(srcPath, "*.jpg")) {
			for (Path entry : entries) {
				String allName = entry.toString();	
				String shortName = allName.substring(allName.lastIndexOf("\\") + 1, allName.length());
				String pureName = shortName.substring(0, shortName.lastIndexOf("."));
				String typeName = shortName.substring(shortName.lastIndexOf(".") + 1, shortName.length());
				String dst = dstDir + "\\" + pureName+extStr+ "."+typeName;
				System.out.println(allName+"------->"+dst);				
				ImageUtil.adaptToSize(allName, dst, width, height);
			}
		}
	}
	
	public  void myGrayImages(String srcDir,String dstDir,String extStr) throws IOException {
		Path srcPath = Paths.get(srcDir);
		String fullName,shortName,pureName,typeName,dstFileName;
		try (DirectoryStream<Path> entries = Files.newDirectoryStream(srcPath, "*.jpg")) {
			for (Path entry : entries) {
				fullName= entry.toString();
				shortName=fullName.substring(fullName.lastIndexOf("\\")+1,fullName.length());
				pureName = shortName.substring(0, shortName.lastIndexOf("."));
				typeName = shortName.substring(shortName.lastIndexOf(".") + 1, shortName.length());
				dstFileName=dstDir+"\\"+pureName+extStr+"."+typeName;
				System.out.println(fullName+"------->"+dstFileName);	
				ImageUtil.setGray(fullName,dstFileName);
			}
		}		
	}

public static void setAlphaImage(String srcPath,String dstPath,int alph) throws IOException {
	//BufferedImage bfImage = ImageIO.read(Paths.get(srcPath).toFile());
	int alpha=alph;
	ImageIcon icon=new ImageIcon(srcPath);
	BufferedImage bfImage = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_4BYTE_ABGR);
	Graphics2D g2D = (Graphics2D) bfImage.getGraphics();
	g2D.drawImage(icon.getImage(), 0, 0, icon.getImageObserver());
	int pixel;
	int rgb[]=new int [3];
	for(int i=bfImage.getMinY();i<bfImage.getHeight();i++) {
		for(int j=bfImage.getMinX();j<bfImage.getWidth();j++) {
			pixel=bfImage.getRGB(j,i);
		//	rgb[0]=(pixel & 0xff0000) >> 16;
		   // rgb[1]=(pixel & 0xff00) >> 8;
		  //  rgb[2] = (pixel & 0xff);
		    pixel= ((alpha+1) << 24) | (pixel & 0x00ffffff);
		    bfImage.setRGB(j, i, pixel);
		}
	}
	g2D.drawImage(bfImage, 0, 0,icon.getImageObserver());
	ImageIO.write(bfImage, "jpg", Paths.get(dstPath).toFile());	
}

 public static void setGray(String srcPath,String dstPath) throws IOException {
	 BufferedImage bfImage = ImageIO.read(Paths.get(srcPath).toFile());	 
	 ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
	 ColorConvertOp cop = new ColorConvertOp(cs,null);
	 bfImage=cop.filter(bfImage,null);
	 ImageIO.write(bfImage,"JPEG", Paths.get(dstPath).toFile());	 
 }

	
	public static void cutImage(String srcPath, String desPath, int xPos, int yPos, int width, int height)
			throws IOException {
		Thumbnails.of(srcPath).sourceRegion(xPos, yPos, width, height).size(width, height).keepAspectRatio(false)
				.outputFormat("png").toFile(desPath);
	}

	/**
	 * 
	 * 
	 * @throws IOException
	 */
	public static void scaleToSize(String sourcePath, String desPath, int width, int height) throws IOException {
		Thumbnails.of(sourcePath).size(width, height).toFile("desPath");
		Thumbnails.of("images/test.jpg").size(2560, 2048).toFile("C:/image_2560x2048.jpg");
	}

	public static void adaptToSize(String sourcePaht, String desPath, int width, int heigth) throws IOException {
		/**
		 * keepAspectRatio(false) 榛樿鏄寜鐓ф瘮渚嬬缉鏀剧殑
		 */
		Thumbnails.of(sourcePaht).size(width, heigth).keepAspectRatio(false).toFile(desPath);
		System.out.println(sourcePaht+"*********>"+desPath);
		
	}

	/**
	 * 鎸夌収姣斾緥杩涜缂╂斁
	 * 
	 * @throws IOException
	 */
	public static void scaleToSize(String sourcePath, String desPath, double scale) throws IOException {
		/**
		 * scale(姣斾緥)
		 */
		Thumbnails.of("images/test.jpg").scale(0.25f).toFile("C:/image_25%.jpg");
		Thumbnails.of("images/test.jpg").scale(1.10f).toFile("C:/image_110%.jpg");
	}

	/**
	 * 鏃嬭浆
	 * 
	 * @throws IOException
	 */
	public static void rotateImage(String sourcePath, String desPath, double ang) throws IOException {
		/**
		 * rotate(瑙掑害),姝ｆ暟锛氶『鏃堕拡 璐熸暟锛氶�嗘椂閽�
		 */
		Thumbnails.of("images/test.jpg").size(1280, 1024).rotate(90).toFile("C:/image+90.jpg");
		Thumbnails.of("images/test.jpg").size(1280, 1024).rotate(-90).toFile("C:/iamge-90.jpg");
	}

	/**
	 * 姘村嵃
	 * 
	 * @throws IOException
	 */
	public static void addWatermark(String sourcePath, String desPath, String waterPath, float alpha)
			throws IOException {
		Thumbnails.of(sourcePath).size(1280, 1024)
				.watermark(Positions.BOTTOM_CENTER, ImageIO.read(new File("images/watermark.png")), 0.5f)
				.outputQuality(0.8f).toFile(desPath);
	}

	/**
	 * 杞寲鍥惧儚鏍煎紡
	 * 
	 * @throws IOException
	 */
	public static void translateFormat(String sourcePath, String desPath, String format) throws IOException {
		/**
		 * outputFormat(鍥惧儚鏍煎紡)
		 */
		Thumbnails.of("images/test.jpg").size(1280, 1024).outputFormat("png").toFile("C:/image_1280x1024.png");
		Thumbnails.of("images/test.jpg").size(1280, 1024).outputFormat("gif").toFile("C:/image_1280x1024.gif");
	}

	/**
	 * 杈撳嚭鍒癘utputStream
	 * 
	 * @throws IOException
	 */
	public static void outputToStream(String sourcePath, OutputStream ops) throws IOException {
		/**
		 * toOutputStream(娴佸璞�)
		 */
		OutputStream os = new FileOutputStream("C:/image_1280x1024_OutputStream.png");
		Thumbnails.of("images/test.jpg").size(1280, 1024).toOutputStream(os);
	}

	/**
	 * 杈撳嚭鍒癇ufferedImage
	 * 
	 * @throws IOException
	 */
	public static void outputToBufferedImage(String sourcePath, BufferedImage bi) throws IOException {
		/**
		 * asBufferedImage() 杩斿洖BufferedImage
		 */
		BufferedImage thumbnail = Thumbnails.of("images/test.jpg").size(1280, 1024).asBufferedImage();
		ImageIO.write(thumbnail, "jpg", new File("C:/image_1280x1024_BufferedImage.jpg"));
	}
}
