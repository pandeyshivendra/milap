package com.example.demo.utility;

import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;


public class SendEmail {

    //donotreply@tcdtech.org
    //dLVhnHSP82*W8gE%

    public static void mail(String[] args) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "donotreply@tcdtech.org";//
        final String password = "dLVhnHSP82*W8gE%";
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("donotreply@tcdtech.org"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("dash.deepak156@gmail.com", false));
            msg.setSubject("Hello");
            msg.setText("How are you");
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        } catch (MessagingException e) {
            System.out.println("Erreur d'envoi, cause: " + e);
        }
    }
	  
	/*  public static void main(String[] args) {
		  String fileUrl="https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/to-be-sent/IMG_20190412_164147.jpg";
		sendAttachmentMAil("dash.deepak156@gmail.com", fileUrl, "test subject", "this is test message");
	}
	  
	  
	  public static boolean sendAttachmentMAil(String to,String fileUrl,String subject,String bodyText) {
			// Recipient's email ID needs to be mentioned.
		       //to = "dash.deepak156@gmail.com";

				System.out.println("eMaling ......"+to+"  "+fileUrl);
		       String from = "info@mirrorsize.com";//change accordingly
		       final String username = "info@mirrorsize.com";//change accordingly
		       final String password = "abcd1234";//change accordingly

		       boolean bool = false;
		       // Yahoo's SMTP server
		       String host = "smtp.zoho.com";

		       Properties props = new Properties();
		       props.put("mail.smtp.auth", "true");
		      props.put("mail.smtp.starttls.enable", "true");
		      props.put("mail.smtp.host", host);
		      props.put("mail.smtp.port", "587");
		      props.put("mail.smtp.ssl.trust", host);

		      System.out.println("eMaling ....1");
		      javax.mail.Session session  = javax.mail.Session.getInstance(props,
		   		   new javax.mail.Authenticator() {
		   	      protected PasswordAuthentication getPasswordAuthentication() {
		   	         return new PasswordAuthentication(username, password);
		   	      }
		   	   });

		      System.out.println("eMaling ....2");
		      try {
		         // Create a default MimeMessage object.
		         Message message = new MimeMessage(session);

		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));

		         // Set To: header field of the header.
		         message.setRecipients(Message.RecipientType.TO,
		            InternetAddress.parse(to));

		         // Set Subject: header field
		         message.setSubject(subject);

		         // Create the message part
		         BodyPart messageBodyPart = new MimeBodyPart();

		         // Now set the actual message
		         messageBodyPart.setText(bodyText);

		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();

		         System.out.println("eMaling ....3");
		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);

		         // Part two is attachment
		         messageBodyPart = new MimeBodyPart();
		        
		         DataSource source = new FileDataSource(fileUrl);
		         messageBodyPart.setDataHandler(new DataHandler(source));
		         messageBodyPart.setFileName("billing.pdf");
		         multipart.addBodyPart(messageBodyPart);

		         // Send the complete message parts
		         message.setContent(multipart);
		        

		         // Send message
		         Transport.send(message);

		         System.out.println("Sent message successfully....");
		  
		         bool = true;
		      } catch (MessagingException e) {
		    	  bool = false;
		         throw new RuntimeException(e);
		         
		      }
		      
		      return bool;
		      
		   }*/

}
