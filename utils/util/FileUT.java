package com.bz.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;



/**
 * 
 * @author THE GIFTED
 *	文件 操作类
 */
public class FileUT {
	/**
	 * 引擎读取文件	
	 */
	public static void readFile(String path,HttpServletResponse resp) {
		FileInputStream fis = null;
		ServletOutputStream toClient= null;
		try {
			// 读入到流中
			fis = new FileInputStream(new File(path));
			int i = fis.available(); // 得到文件大小  
			byte data[] = new byte[i];  
			fis.read(data); // 读数据  
			fis.close();  
			resp.setContentType("image/*"); // 设置返回的文件类型  
			toClient = resp.getOutputStream(); // 得到向客户端输出二进制数据的对象  
			toClient.write(data); // 输出数据  
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(null!=fis) {
					fis.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null!= toClient) {
					toClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 根据文件的路径下载
	 * @param path 文件的权限顶路径
	 * @param res
	 * @param req
	 * @throws IOException
	 */
	public static  void downloadByFileName(String path,HttpServletResponse res,HttpServletRequest req) throws IOException{
		//获取路径中的文件名称
		String fileName = path.substring(path.lastIndexOf("/"));
		//设置响应流中文件进行下载
		res.setHeader("Content-Disposition", "attachment;filename="+fileName);
		//把二进制流放入到响应体中.
		ServletOutputStream os = res.getOutputStream();
		//文件下载的路径
//		String path = req.getServletContext().getRealPath("files");
//		System.out.println(path);
		File file = new File(path);
		//读取文件未二进制字节
		byte[] bytes = FileUtils.readFileToByteArray(file);
		//响应字节流到前台
		os.write(bytes);
		os.flush();
		os.close();
	}
	/**
	 * 
	 * @param fileName
	 * @param res
	 * @param req
	 * @throws IOException
	 */
	public static  void downloadByFile(String fileName,HttpServletResponse res,HttpServletRequest req) throws IOException{
		//设置响应流中文件进行下载
		res.setHeader("Content-Disposition", "attachment;filename="+fileName);
		//把二进制流放入到响应体中.
		ServletOutputStream os = res.getOutputStream();
		//文件下载的路径
		String path = req.getServletContext().getRealPath("files");
		System.out.println(path);
		File file = new File(path, fileName);
		//读取文件未二进制字节
		byte[] bytes = FileUtils.readFileToByteArray(file);
		//响应字节流到前台
		os.write(bytes);
		os.flush();
		os.close();
	}
	/**
	 * 
	 * @param file
	 * @param
	 * @return //新路径
	 * @throws IOException
	 */
	public static String uploadFile(MultipartFile file) throws IOException{
		String fileName = file.getOriginalFilename();
		//获取文件名称后缀
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		//判断上传文件类型
//		if(suffix.equalsIgnoreCase(".png")){
			String uuid = UUID.randomUUID().toString();
			//复制输入流文件为新文件
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File("D:/1901/uuid/"+uuid+suffix));
			//返回新文件名称
			return "D:/1901/uuid/"+uuid+suffix;
//			return "/index.jsp";
//		}else{
//			return "error.jsp";
//		}
	}

	public static void main(String[] args) {
		System.out.println("邯郸银行".indexOf("邯郸"));
	}
}
