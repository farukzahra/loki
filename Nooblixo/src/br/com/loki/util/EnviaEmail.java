package br.com.loki.util;

import java.util.Properties;

import javax.faces.application.ProjectStage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviaEmail {
    public static void enviaEmail(String to, String subject, String content) throws AddressException, MessagingException {
        enviaEmail(null, to, subject, content);
    }

    public static void enviaEmail(String from, String to, String subject, String content) throws AddressException, MessagingException {
        enviaEmail(from, to, subject, content, null, Mensagens.getMensagem("email.smtpPort"));
    }

    public static void enviaEmailOffLine(String from, String to, String subject, Object content) throws AddressException, MessagingException {
        enviaEmail(from, to, subject, content, Mensagens.getMensagemOffLine("email.smtpHost.prod"), Mensagens.getMensagemOffLine("email.smtpPort"));
    }
    
    public static void enviaEmailOffLine(String from, String to, String subject, Object content, String host) throws AddressException, MessagingException {
        enviaEmail(from, to, subject, content, host, Mensagens.getMensagemOffLine("email.smtpPort"));
    }
    
    public static void main(String[] args) {
        try {
            String emailAcompanhamento = Mensagens.getMensagemOffLine("emaildealer.acompanhamento"); 
            EnviaEmail.enviaEmailOffLine("isvendas@lumetec.com.br", "faruk.zahra@lumetec.com.br;"+emailAcompanhamento, "teste", "teste",  Mensagens.getMensagemOffLine("email.smtpHost.homolog"));
            //EnviaEmail.enviaEmailOffLine("isvendas@lumetec.com.br", "faruk.zahra@lumetec.com.br", "teste", "teste",  Mensagens.getMensagemOffLine("email.smtpHost.homolog"));
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void enviaEmail(String from, String to, String subject, Object content, String host, String smtpPort) throws AddressException,
            MessagingException {
        Properties props = new Properties();
        if (host == null) {
            if (ProjectStage.Development == JSFHelper.getProjectStage()) {
                props.put("mail.smtp.host", Mensagens.getMensagem("email.smtpHost.homolog"));
            } else {
                props.put("mail.smtp.host", Mensagens.getMensagem("email.smtpHost.prod"));
            }
        }else{
            props.put("mail.smtp.host", host);
        }
        props.put("mail.smtp.port", smtpPort);
        Session session = Session.getDefaultInstance(props, null);
        Message msg = new MimeMessage(session);
        if (from == null)
            msg.setFrom(new InternetAddress(Mensagens.getMensagem("email.from")));
        else
            msg.setFrom(new InternetAddress(from));        
        if (to.contains(";")) {
            String[] split = to.split(";");
            InternetAddress[] emails = new InternetAddress[split.length];
            for (int i = 0; i < split.length; i++) {
                emails[i] = new InternetAddress(split[i]);
            }
            msg.addRecipients(Message.RecipientType.TO, emails);
        }
        else {
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        }
        msg.setSubject(subject);
        //msg.setText(content);
        msg.setContent(content, "text/html; charset=utf-8");
        Transport.send(msg);
    }
    
    
}
