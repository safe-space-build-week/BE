package com.lambdaschool.safespace;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// @EnableWebMvc
@EnableJpaAuditing
@SpringBootApplication
public class SafeSpaceApplication
{
    public static final String ACCOUNT_SID =
            "ACc7137b08d56cd6633e621af07777ae5e";
    public static final String AUTH_TOKEN =
            "a90118bb06f653946cf34fca9a54b80d";

    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(SafeSpaceApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

        // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //
        // Message message = Message
        //         .creator(new PhoneNumber("+17742696689"), // to
        //                 new PhoneNumber("+17622526232"), // from
        //                 "TESTING")
        //         .create();
        //
        // System.out.println(message.getSid());
    }
}
