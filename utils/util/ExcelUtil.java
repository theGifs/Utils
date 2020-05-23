package com.bz.util;

import com.bz.pojo.TBank;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 2003/2006 导入导出
 * @Author: THE GIFTED
 * @CreateTime 2019年07月30日 10:57
 */
public class ExcelUtil {



    /**
     *  //模板开发XSSF
     * @param dataList 集合数据
     * @param request
     * @param response
     * @param path 模板文件所在的绝对路径
     * @throws IOException
     */
    public static void print(List<TBank> dataList, HttpServletRequest request, HttpServletResponse response,String path) throws IOException {
        InputStream is = new FileInputStream(new File(path));
        Workbook wb = new XSSFWorkbook(is);		//打开一个模板文件，工作簿 2007以上版本
        Sheet sheet = wb.getSheetAt(0);			//获取到第一个工作表
        Row nRow = null;
        Cell nCell = null;
        int rowNo = 0;							//行号
        int colNo = 0;							//列号 ( 注意:空出来的第0列是为了打印机)
        //获取模板上的单元格样式   字段都写在列表头
        nRow = sheet.getRow(0);
        //银行卡号的样式
        nCell = nRow.getCell(0);
        CellStyle cartNumStyle = nCell.getCellStyle();
        //订单号的样式
        nCell = nRow.getCell(1);
        CellStyle typeStyle = nCell.getCellStyle();

        //银行的样式
        nCell = nRow.getCell(2);
        CellStyle bankStyle = nCell.getCellStyle();

        //开户人的样式
        nCell = nRow.getCell(3);
        CellStyle personStyle = nCell.getCellStyle();

        //余额的样式
        nCell = nRow.getCell(4);
        CellStyle moneyStyle = nCell.getCellStyle();

        //日期的样式
        nCell = nRow.getCell(5);
        CellStyle dateStyle = nCell.getCellStyle();

//        //贸易条款的样式
//        nCell = nRow.getCell(8);
//        CellStyle tradeStyle = nCell.getCellStyle();


//        //处理大标题
//        nRow = sheet.getRow(rowNo++);			//获取一个行对象
//        nCell = nRow.getCell(colNo);			//获取一个单元格对象
//        nCell.setCellValue(inputDate.replaceFirst("-0", "-").replaceFirst("-", "年") + "月份出货表");		//yyyy-MM
//
        rowNo++;								//跳过静态表格头

        //处理内容
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
       for(int j=0;j<dataList.size();j++){
            colNo = 0;				//初始化 第零列开始,
           TBank bank = dataList.get(j);
//            创建 一行
            nRow = sheet.createRow(rowNo++);
//            设置行高
//            nRow.setHeightInPoints(24);

            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(bank.getBankNo());
            nCell.setCellStyle(cartNumStyle);

            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(bank.getBankType()==1?"信用卡":"储蓄卡");
            nCell.setCellStyle(typeStyle);
/*银行-------------------------------------------------*/
            nCell = nRow.createCell(colNo++);
            String str = "";
            switch (bank.getBankBelong()){
                case 1:str = "中国银行";break;
                case 2:str = "澳洲银行";break;
                case 3:str = "工商银行";break;
                case 4:str = "瑞士银行";break;
           }
            nCell.setCellValue(str);
            nCell.setCellStyle(bankStyle);
//开户人
            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(bank.getBankName());
            nCell.setCellStyle(personStyle);

            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(bank.getBankMoney()+" $");
            nCell.setCellStyle(moneyStyle);

            nCell = nRow.createCell(colNo++);

            nCell.setCellValue(sim.format(bank.getBankDate()));
            nCell.setCellStyle(dateStyle);

        }

//		OutputStream os = new FileOutputStream("c:\\outproduct.xls");
//		wb.write(os);
//
//		os.flush();
//		os.close();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);

        DownloadUtil downloadUtil = new DownloadUtil();				//直接弹出下载框，用户可以打开，可以保存
        downloadUtil.download(os, response, "1001.xlsx");

        os.flush();
        os.close();
    }



}
