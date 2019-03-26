package com.ansel.util;/**
 * Created by 860618058 on 2019/3/25.
 */

import com.ansel.bean.Employee;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * @author 860618058
 * @version 1.0
 * @description TODO  发送邮件
 * @date 2019/3/25 10:00
 **/

public class SendMailUtils {

    @Test
    public void SendMailUtils() throws Exception {
        //1. 构造SMTP邮件服务器的基本环境
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);
        //获取邮件模版
        MimeMessage mimeMessage = getMimeMessageType3(session);
        //3. 发送邮件
        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "1397754801@qq.com", "ezmploesftmlbaba");
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());//发送邮件，第二个参数为收件人
        transport.close();
    }

    /**
     * @param session
     * @return javax.mail.internet.MimeMessage
     * @description //2. 构造简单邮件
     * @author chenshuai
     * @date 2019/3/25
     */
    public MimeMessage getMimeMessageType(Session session) throws Exception {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.addRecipients(Message.RecipientType.TO, "15893630801@163.com");//设置收信人
//        mimeMessage.addRecipients(Message.RecipientType.CC, "15893630801@163.com");//抄送
        mimeMessage.setFrom(new InternetAddress("1397754801@qq.com"));//邮件发送人
        mimeMessage.setSubject("测试邮件主题");//邮件主题
        mimeMessage.setContent("Hello,这是一封测试邮件", "text/html;charset=utf-8");//正文
        return mimeMessage;
    }

    /**
     * @param df, employee
     * @return void
     * @description 封装职工对象
     * @author chenshuai
     * @date 2019/3/25
     */
    public void getEmployee(DateTimeFormatter df, Employee employee) {
        employee.setEmployeeCode("KF0001");
//        employee.setContractTerm(3);
//        LocalDate date = LocalDate.now();
//        employee.setBeginContract(date);
//        String localTimeStart = df.format(date);
//        employee.setBeginContractStr(localTimeStart);
//        date = date.plusYears(3);
//        employee.setEndContract(date);
//        String localTimeEnd = df.format(date);
//        employee.setEndContractStr(localTimeEnd);
        employee.setDepartment("后台JAVA开发");
        employee.setPosition("实习生");
        employee.setEmployeeName("chenss");
    }

    /**
     * @param session
     * @return javax.mail.internet.MimeMessage
     * @description //2. 构造复杂邮件
     * @author chenshuai
     * @date 2019/3/25
     */
    public MimeMessage getMimeMessageType2(Session session) throws Exception {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.addRecipients(Message.RecipientType.TO, "15893630801@163.com");//抄送
        mimeMessage.setFrom(new InternetAddress("1397754801@qq.com"));//邮件发送人
        mimeMessage.setSubject("测试复杂邮件主题");//邮件主题

        MimeMultipart mixed = new MimeMultipart("mixed");
        mimeMessage.setContent(mixed);//设置整封邮件的MIME消息体为混合的组合关系

        MimeBodyPart attach1 = new MimeBodyPart();//创建附件1
        MimeBodyPart attach2 = new MimeBodyPart();//创建附件2
        MimeBodyPart content = new MimeBodyPart();//创建邮件正文

        mixed.addBodyPart(attach1);//将附件一添加到MIME消息体中
        mixed.addBodyPart(attach2);//将附件二添加到MIME消息体中
        mixed.addBodyPart(content);//将正文添加到消息体中

        FileDataSource fds1 = new FileDataSource(new File("D:\\mail_test\\test01.png"));//构造附件一的数据源
        DataHandler dh1 = new DataHandler(fds1);//数据处理
        attach1.setDataHandler(dh1);//设置附件一的数据源
        attach1.setFileName("test01.png");//设置附件一的文件名

        //附件二的操作与附件一类似，这里就不一一注释了
        FileDataSource fds2 = new FileDataSource(new File("D:\\mail_test\\note.docx"));
        DataHandler dh2 = new DataHandler(fds2);
        attach2.setDataHandler(dh2);
        attach2.setFileName(MimeUtility.encodeText("note.docx"));//设置文件名时，如果有中文，可以通过MimeUtility类中的encodeText方法进行编码，避免乱码

        MimeMultipart bodyMimeMultipart = new MimeMultipart("related");//设置正文的MIME类型
        content.setContent(bodyMimeMultipart);//将bodyMimeMultipart添加到正文消息体中

        MimeBodyPart bodyPart = new MimeBodyPart();//正文的HTML部分
        bodyPart.setContent("<h1>Hello大家好，这是一封测试邮件<img src='cid:test02.png'/></h1>", "text/html;charset=utf-8");

        MimeBodyPart picPart = new MimeBodyPart();//正文的图片部分
        DataHandler dataHandler = new DataHandler(new FileDataSource("D:\\mail_test\\test02.png"));
        picPart.setDataHandler(dataHandler);
        picPart.setContentID("test02.png");

        //将正文的HTML和图片部分分别添加到bodyMimeMultipart中
        bodyMimeMultipart.addBodyPart(bodyPart);
        bodyMimeMultipart.addBodyPart(picPart);

        mimeMessage.saveChanges();
        return mimeMessage;
    }

    /**
     * @param session
     * @return javax.mail.internet.MimeMessage
     * @description //3. 构造 FreeMarker模版邮件
     * @author chenshuai
     * @date 2019/3/25
     */
    public MimeMessage getMimeMessageType3(Session session) throws Exception {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.addRecipients(Message.RecipientType.TO, "15893630801@163.com");//设置收信人
        //mimeMessage.addRecipients(Message.RecipientType.CC, "15893630801@163.com");//抄送
        mimeMessage.setFrom(new InternetAddress("1397754801@qq.com"));
        mimeMessage.setSubject("测试FreeMarker模版邮件主题");//邮件主题
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "ftl");
        Template emailTemplate = cfg.getTemplate("email.ftl");
        freeMarkerConfigurer.setConfiguration(cfg);
        StringWriter out = new StringWriter();
        //封装对象
        Employee employee = new Employee();
        getEmployee(df, employee);
        emailTemplate.process(employee, out);
        mimeMessage.setContent(out.toString(), "text/html;charset=utf-8");//正文
        return mimeMessage;
    }
}
