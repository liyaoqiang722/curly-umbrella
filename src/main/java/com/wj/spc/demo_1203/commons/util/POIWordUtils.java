package com.wj.spc.demo_1203.commons.util;

import com.wj.spc.demo_1203.commons.constants.POIParagraphType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyaoqiang on 17/8/18.
 */
public class POIWordUtils {

    //根据设置好Style对象的富文本paragraph列表生成word文档
    public static XWPFDocument generateWordFromRichTextParagraph(XWPFDocument doc, List<Paragraph> paragraphs) throws IOException {
        paragraphs.forEach(paragraph->{
            int a=doc.getParagraphs().size();
            XmlCursor cursor = doc.getDocument().getBody().getPArray(a-1).newCursor();
            XWPFParagraph xp = doc.insertNewParagraph(cursor);
            if(paragraph.getItems().size()>0){
                paragraph.getItems().forEach(item->{
                    POIWordUtils.makeParagraphItem(xp, item.getText(), item.getPOIStyle(),1);
                });
            }

            if("center".equals(paragraph.getPOIStyle().getTextAlign())){
                xp.setAlignment(ParagraphAlignment.CENTER);
            }else if("right".equals(paragraph.getPOIStyle().getTextAlign())){
                xp.setAlignment(ParagraphAlignment.RIGHT);
            }else if("left".equals(paragraph.getPOIStyle().getTextAlign())){
                xp.setAlignment(ParagraphAlignment.LEFT);
            }else{
                xp.setAlignment(ParagraphAlignment.LEFT);
            }

            xp.setVerticalAlignment(TextAlignment.CENTER);
            xp.setIndentationFirstLine(0);
            POIWordUtils.setLineSpacing(1.3, xp);
        });

        return doc;
    }

    //根据设置好Style对象的paragraph列表生成word文档
    public static XWPFDocument generateWordFromParagraph(XWPFDocument doc, List<POIParagraph> paragraphs) throws IOException {
        paragraphs.forEach(paragraph->{

            if(paragraph.getType() == POIParagraphType.WORD.getType()){
                //段落类型：文字
                int a=doc.getParagraphs().size();
                XmlCursor cursor = doc.getDocument().getBody().getPArray(a-1).newCursor();
                XWPFParagraph xp = doc.insertNewParagraph(cursor);
                if(paragraph.getItems().size()>0){
                    paragraph.getItems().forEach(item->{
                        POIWordUtils.makeParagraphItem(xp, item.getText(), item.getStyle(),2);
                    });
                }

                if("center".equals(paragraph.getStyle().getTextAlign())){
                    xp.setAlignment(ParagraphAlignment.CENTER);
                }else if("right".equals(paragraph.getStyle().getTextAlign())){
                    xp.setAlignment(ParagraphAlignment.RIGHT);
                }else if("left".equals(paragraph.getStyle().getTextAlign())){
                    xp.setAlignment(ParagraphAlignment.LEFT);
                }else{
                    xp.setAlignment(ParagraphAlignment.LEFT);
                }

                xp.setVerticalAlignment(TextAlignment.CENTER);
                xp.setIndentationFirstLine(paragraph.getStyle().getIndentationFirstLine());
                POIWordUtils.setLineSpacing(1.3, xp);
            } else if(paragraph.getType() == POIParagraphType.BLANK_PAGE.getType()){

                //插入空页
                XWPFParagraph xp= doc.createParagraph();
                XWPFRun run = xp.createRun();
                run.addBreak(BreakType.PAGE);

            }else if(paragraph.getType() == POIParagraphType.IMAGE.getType()){

                //段落类型：图片
                XWPFParagraph xp= doc.createParagraph();
                XWPFRun run = xp.createRun();
                int format = XWPFDocument.PICTURE_TYPE_JPEG;

                try {
                    run.addPicture(paragraph.getImageInputStream(), format, "", Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if("center".equals(paragraph.getStyle().getTextAlign())){
                    xp.setAlignment(ParagraphAlignment.CENTER);
                }else if("right".equals(paragraph.getStyle().getTextAlign())){
                    xp.setAlignment(ParagraphAlignment.RIGHT);
                }else if("left".equals(paragraph.getStyle().getTextAlign())){
                    xp.setAlignment(ParagraphAlignment.LEFT);
                }else{
                    xp.setAlignment(ParagraphAlignment.LEFT);
                }

                xp.setVerticalAlignment(TextAlignment.CENTER);

            }else if(paragraph.getType() == POIParagraphType.WORD_UNDER_IMAGE.getType()){
                //段落类型：图片下的文字
                XWPFParagraph xp = doc.createParagraph();
                if(paragraph.getItems().size()>0){
                    paragraph.getItems().forEach(item->{
                        POIWordUtils.makeParagraphItem(xp, item.getText(), item.getStyle(),2);
                    });
                }

                if("center".equals(paragraph.getStyle().getTextAlign())){
                    xp.setAlignment(ParagraphAlignment.CENTER);
                }else if("right".equals(paragraph.getStyle().getTextAlign())){
                    xp.setAlignment(ParagraphAlignment.RIGHT);
                }else if("left".equals(paragraph.getStyle().getTextAlign())){
                    xp.setAlignment(ParagraphAlignment.LEFT);
                }else{
                    xp.setAlignment(ParagraphAlignment.LEFT);
                }

                xp.setVerticalAlignment(TextAlignment.CENTER);
                xp.setIndentationFirstLine(0);
                POIWordUtils.setLineSpacing(1.3, xp);

            }
        });

        return doc;
    }

    //输出文本段落内的文字及样式
    private static void makeParagraphItem(XWPFParagraph xp, String text, POIStyle style, int paragraphType) {

        XWPFRun run = xp.createRun();
        run.setText(cleanText(text));

        if(paragraphType==1){
            //富文本段落的字号转为word时缩小四个号
            run.setFontSize(Integer.parseInt(style.getFontSize().substring(0, style.getFontSize().length() - 2)) - 4);
        }else if(paragraphType==2){
            //正常段落字号不变
            run.setFontSize(Integer.parseInt(style.getFontSize().substring(0, style.getFontSize().length() - 2)));
        }

        run.setBold(style.isBold());
        run.setItalic(style.isItalic());
        if(style.isUnderLine()){
            run.setUnderline(UnderlinePatterns.SINGLE);
        }
        run.setColor(style.getColor());
        run.setFontFamily("华文细黑");
    }

    public static void createBlankPage(XWPFDocument document) {
        XWPFParagraph xp = document.createParagraph();
        XWPFRun run = xp.createRun();
        run.addBreak(BreakType.PAGE);
    }

    //规范化文本，目前没有用处
    private static String cleanText(String text){
        return text==null?"":text;
    }

    //设置行间距
    private static void setLineSpacing(double timeNumber, XWPFParagraph xp) {
        if (timeNumber != 0) {
            int lineSpacing = (int) (timeNumber * 240);
            CTPPr ppr = xp.getCTP().getPPr();
            if (ppr == null) ppr = xp.getCTP().addNewPPr();
            CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
            spacing.setAfter(BigInteger.valueOf(0));
            spacing.setBefore(BigInteger.valueOf(0));
            spacing.setLineRule(STLineSpacingRule.AUTO);
            spacing.setLine(BigInteger.valueOf(lineSpacing));
        }
    }

    //生成并添加标题paragraph
    public static void addTitleParagraph(List<POIParagraph> paragraphs,String content){
        if(StringUtils.isNotBlank(content)){
            POIParagraph poiParagraph = new POIParagraph();

            POIStyle poiStyle = new POIStyle();
            poiStyle.setBold(true);
            poiStyle.setColor("006699");
            poiStyle.setFontSize("18px");
            poiStyle.setTextAlign("center");
            poiParagraph.setStyle(poiStyle);

            POIItem poiItem = new POIItem();
            poiItem.setText(content);
            poiItem.setStyle(poiStyle);
            setSingleItemToParagraph(poiParagraph,poiItem);


            paragraphs.add(poiParagraph);
        }
    }
    //生成并添加副标题paragraph
    public static void addSubtitleParagraph(List<POIParagraph> paragraphs,String content){
        if(StringUtils.isNotBlank(content)){
            POIParagraph poiParagraph = new POIParagraph();

            POIStyle poiStyle = new POIStyle();
            poiStyle.setFontSize("13px");
            poiStyle.setColor("000000");
            poiStyle.setTextAlign("center");
            poiParagraph.setStyle(poiStyle);

            POIItem poiItem = new POIItem();
            poiItem.setText(content);
            poiItem.setStyle(poiStyle);
            setSingleItemToParagraph(poiParagraph,poiItem);

            paragraphs.add(poiParagraph);
        }
    }
    //生成并添加案号paragraph
    public static void addCaseNumberParagraph(List<POIParagraph> paragraphs,String content){
        if(StringUtils.isNotBlank(content)){
            POIParagraph poiParagraph = new POIParagraph();

            POIStyle poiStyle = new POIStyle();
            poiStyle.setFontSize("13px");
            poiStyle.setColor("000000");
            poiStyle.setTextAlign("right");
            poiParagraph.setStyle(poiStyle);

            POIItem poiItem = new POIItem();
            poiItem.setText(content);
            poiItem.setStyle(poiStyle);
            setSingleItemToParagraph(poiParagraph,poiItem);

            paragraphs.add(poiParagraph);
        }
    }
    //生成并添加正文paragraph
    public static void addBodyParagraph(List<POIParagraph> paragraphs,String content){
        if(StringUtils.isNotBlank(content)){
            POIParagraph poiParagraph = new POIParagraph();

            POIStyle poiStyle = new POIStyle();
            poiStyle.setFontSize("13px");
            poiStyle.setColor("000000");
            poiStyle.setTextAlign("left");
            poiStyle.setIndentationFirstLine(500);
            poiParagraph.setStyle(poiStyle);

            POIItem poiItem = new POIItem();
            poiItem.setText(content);
            poiItem.setStyle(poiStyle);
            setSingleItemToParagraph(poiParagraph,poiItem);

            paragraphs.add(poiParagraph);
        }
    }
    //生成并添加Ending paragraph
    public static void addEndingParagraph(List<POIParagraph> paragraphs,String content){
        POIParagraph poiParagraph = new POIParagraph();

        POIStyle poiStyle = new POIStyle();
        poiStyle.setFontSize("13px");
        poiStyle.setColor("000000");
        poiStyle.setTextAlign("right");
        poiParagraph.setStyle(poiStyle);

        POIItem poiItem = new POIItem();
        poiItem.setText(content);
        poiItem.setStyle(poiStyle);
        setSingleItemToParagraph(poiParagraph,poiItem);

        paragraphs.add(poiParagraph);

    }
    //生成并添加二维码图片 paragraph
    public static void addQrCodePageParagraph(List<POIParagraph> paragraphs,String content,String hanukkahHostUrl,String judgementId,String logoPath) throws Exception {
        addBlankPageParagraph(paragraphs);
        addQrCodeImgParagraph(paragraphs,hanukkahHostUrl,judgementId,logoPath);
        addImgDescParagraph(paragraphs, content);
    }
    //生成并添加BlankPage paragraph
    public static void addBlankPageParagraph(List<POIParagraph> paragraphs){
        POIParagraph poiParagraph = new POIParagraph();
        poiParagraph.setType(2);
        paragraphs.add(poiParagraph);
    }
    //生成并添加QrCodeImg paragraph
    public static void addQrCodeImgParagraph(List<POIParagraph> paragraphs,String hanukkahHostUrl,String judgementId,String logoPath) throws Exception {
        POIParagraph poiParagraph = new POIParagraph();
        poiParagraph.setType(3);
        POIStyle poiStyle = new POIStyle();
        poiStyle.setTextAlign("center");
        poiParagraph.setStyle(poiStyle);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //QRCodeUtil.encode(hanukkahHostUrl + "detail?judgementId=" + judgementId,logoPath, byteArrayOutputStream,true,true);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        poiParagraph.setImageInputStream(byteArrayInputStream);
        paragraphs.add(poiParagraph);
    }
    //生成并添加图片描述ImgDesc paragraph
    public static void addImgDescParagraph(List<POIParagraph> paragraphs,String content){
        if(StringUtils.isNotBlank(content)){
            POIParagraph poiParagraph = new POIParagraph();
            poiParagraph.setType(4);
            POIStyle poiStyle = new POIStyle();
            poiStyle.setBold(true);
            poiStyle.setFontSize("18px");
            poiStyle.setColor("006699");
            poiStyle.setTextAlign("center");
            poiParagraph.setStyle(poiStyle);

            POIItem poiItem = new POIItem();
            poiItem.setText(content);
            poiItem.setStyle(poiStyle);
            setSingleItemToParagraph(poiParagraph,poiItem);

            paragraphs.add(poiParagraph);
        }
    }
    //获取法庭全名称getFullCourtName
    public static String getFullCourtName(String province,String court){
        if(StringUtils.isBlank(province)){
            return court;
        }
        if(province.equals("新疆维吾尔")){
            province="新疆维吾尔自治区";
        }
        String fullCourtName="";
        //铁路运输，海事直接区法院名称
        if(court.contains("铁路运输")||court.contains(("海事"))){
            if(province.equals("新疆维吾尔自治区")&&court.startsWith("新疆")){
                fullCourtName = court.substring(2);
            }else{
                fullCourtName = court;
            }
            //最高人民法院前补上中华人民共和国
        }else if(court.equals("最高人民法院")){
            fullCourtName="中华人民共和国"+court;
        }else{
            //直辖市
            if("北京市".equals(province)||"天津市".equals(province)||"上海市".equals(province)||"重庆市".equals(province)){
                if(province.substring(0,2).equals(court.substring(0,2))){
                    fullCourtName = court;
                }else{
                    fullCourtName = province + court;
                }
            }else {//非直辖市
                if(court.contains("高级人民法院")){
                    fullCourtName = court;
                }else if(province.equals("新疆维吾尔自治区")&&court.startsWith("新疆生产建设兵团")){
                    fullCourtName=court;
                }
                else if(province.equals("新疆维吾尔自治区")&&court.contains("垦区")){
                    fullCourtName = "新疆生产建设兵团" + court;
                }
                else{
                    if(province.equals("新疆维吾尔自治区")&& court.startsWith("新疆")){
                        fullCourtName = province+court.substring(2);
                    }else{
                        fullCourtName = province+court;
                    }


                }
            }
        }

        if(StringUtils.isBlank(fullCourtName)){
            fullCourtName=court;
        }

        return fullCourtName;
    }
    private static void setSingleItemToParagraph(POIParagraph poiParagraph,POIItem poiItem){
        List<POIItem> poiItems = new ArrayList<>();
        poiItems.add(poiItem);

        poiParagraph.setItems(poiItems);
    }
}
