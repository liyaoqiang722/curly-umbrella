package com.wj.spc.demo_1203.servuice.impl;

import com.wj.spc.demo_1203.commons.constants.EmailType;
import com.wj.spc.demo_1203.commons.util.POIParagraph;
import com.wj.spc.demo_1203.commons.util.POIWordUtils;
import com.wj.spc.demo_1203.commons.util.Paragraph;
import com.wj.spc.demo_1203.dao.mapper.aMapper.AMapper;
import com.wj.spc.demo_1203.dao.mapper.aMapper.BaseRegionAMapper;
import com.wj.spc.demo_1203.dao.mapper.bMapper.BMapper;
import com.wj.spc.demo_1203.dao.mapper.bMapper.BaseRegionBMapper;
import com.wj.spc.demo_1203.dao.redis.TestDao;
import com.wj.spc.demo_1203.domain.Province;
import com.wj.spc.demo_1203.domain.User;
import com.wj.spc.demo_1203.servuice.TestService;
import javafx.concurrent.Task;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    private AMapper aMapper;

    @Resource
    private BaseRegionAMapper baseRegionAMapper;

    @Resource
    private BMapper bMapper;

    @Resource
    private BaseRegionBMapper baseRegionBMapper;

    @Autowired
    private TestDao testDao;

    @Override
    public List<User> dataSource1() {

        List<User> users = new ArrayList<>();

        long startTime = System.currentTimeMillis();    //获取开始时间

        try {
            users = testDao.getUsers();
            if (users == null || users.size() == 0) {
                users = aMapper.selectAllUser();
                testDao.saveUsers(users);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();    //获取结束时间
        log.info("程序运行时间：" + (endTime - startTime) + "ms");
        return users;
    }

    @Override
    public User dataSource2() {
        return bMapper.selectAllUser();
    }

    @Override
    public List<Province> dataSource3() {

        long startTime = System.currentTimeMillis();    //获取开始时间

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Task task = new Task();
        executorService.submit(task);
        executorService.shutdown();

        List<Province> provinces = baseRegionAMapper.getProvince();

        long endTime = System.currentTimeMillis();    //获取结束时间
        log.info("程序运行时间：" + (endTime - startTime) + "ms");
        return provinces;
    }

    private class Task implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(6000);
                baseRegionBMapper.getProvince();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void enumSetTest01() {
        EnumSet enumSet = EnumSet.allOf(EmailType.class);
        int i = 1;
        Iterator setIt = enumSet.iterator();
        while (setIt.hasNext()){
            log.info("第"+ i + "个元素为：" + setIt.next().toString());
            i++;
        }
    }

    @Override
    public ByteArrayOutputStream getWordForJudgement() throws Exception {
        XWPFDocument doc = new XWPFDocument(this.getClass().getResourceAsStream("/downloadTemplate/template.docx"));

        List<POIParagraph> paragraphs = getPOIParagraphs();
        POIWordUtils.generateWordFromParagraph(doc, paragraphs);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        doc.write(byteArrayOutputStream);
        byteArrayOutputStream.close();

        return byteArrayOutputStream;
    }

    private List<POIParagraph> getPOIParagraphs() {
        List<POIParagraph> paragraphs = new ArrayList<>();
        //todo 根据generateWordFile中逻辑在POIWordUtils增加相应的方法。生成paragraph并添加到paragraphs中
        POIWordUtils.addTitleParagraph(paragraphs, "word测试0103");
        //文书副标题－文书类型
        POIWordUtils.addSubtitleParagraph(paragraphs, "民事判决书");
        //正文
        StringBuffer judgmentDate = new StringBuffer();
        StringBuffer secretary = new StringBuffer();
        List<String> trialMen = new ArrayList<>();
        //输出正文，拼接出落款所需内容
            for (int i = 0; i < 10; i++) {
                String text = "测试啊啊啊啊啊啊啊" + i;
                POIWordUtils.addBodyParagraph(paragraphs, text);
            }

        POIWordUtils.addEndingParagraph(paragraphs, "\r");
        POIWordUtils.addEndingParagraph(paragraphs, "\r");
        POIWordUtils.addEndingParagraph(paragraphs, "\r");

        if (trialMen.size() > 0) {
            trialMen.forEach(trialMan -> {
                try {
                    POIWordUtils.addEndingParagraph(paragraphs, trialMan);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        POIWordUtils.addEndingParagraph(paragraphs, judgmentDate.toString());
        POIWordUtils.addEndingParagraph(paragraphs, secretary.toString());
        return paragraphs;
    }

    @Override
    public void patternTest01() {
        String text = "2013年1月，被申请人黄某某从礼来（中国）研发公司的服务2013年12月1日";

        //Pattern p = Pattern.compile("(\\d{4})年(\\d{2})月(\\d{2})日");
        Pattern p = Pattern.compile("((([0-9]{4})年([0-9]{2}|[1-9]))月([0-9]{2}|[1-9]))日|" +
                "(([0-9]{4})年([0-9]{2}|[1-9]))月");
        Matcher matcher = p.matcher(text);

        if (matcher.find()) {  //判断文本是否找到符合规则字符串，并提取
            String result =  matcher.group(0);
            log.info("找到符合筛选规则的数据===" + result);
            log.info("找到符合筛选规则的数据===" + matcher.group(1));
        }
        log.info("123");
    }
}
