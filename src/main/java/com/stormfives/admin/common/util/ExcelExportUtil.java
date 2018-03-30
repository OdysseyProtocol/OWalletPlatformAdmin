package com.stormfives.admin.common.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 17/8/10
 */
public class ExcelExportUtil {


    //
    public static CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName(" ");
        font.setFontHeightInPoints((short)16);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);					//

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_CENTER);					//
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//

        return style;
    }


    //
    public static CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName(" ");
        font.setFontHeightInPoints((short)12);

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_CENTER);					//
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//

        cellLine(style); //

        return style;
    }

    //
    public static CellStyle text(CellStyle style,Font font){
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_LEFT);					//
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//

        cellLine(style);  //

        return style;
    }

    //
    public static void cellLine(CellStyle style) {
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);				//
        style.setBorderLeft(CellStyle.BORDER_THIN);					//
        style.setBorderRight(CellStyle.BORDER_THIN);				//
    }



}
