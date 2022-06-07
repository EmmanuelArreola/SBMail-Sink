package com.n3xgen.sink.mail.cloud.stream;

import java.util.function.Consumer;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.n3xgen.sink.mail.cloud.stream.bean.Mail;
import com.n3xgen.sink.mail.cloud.stream.bean.MailAccount;
import com.n3xgen.sink.mail.cloud.stream.service.MailService;
import com.n3xgen.sink.mail.cloud.stream.service.MailServiceImpl;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@EnableConfigurationProperties(MailAccount.class)
@Configuration
public class MailStreamCnf {

	private MailAccount mailAccount;
	
	public MailStreamCnf(MailAccount mailAccount) {
		this.mailAccount = mailAccount;
	}
	
	@Bean
    public Consumer<Flux<Mail>> mailSenderCnf() {
        
		return mailFluxCnf -> mailFluxCnf.subscribe(
				mailCnf -> {
					log.info("Mail recieved: {}", mailCnf);
					mailCnf.setMailAccount(mailAccount);
					MailService mailService = new MailServiceImpl();
			        mailService.sendMail(mailCnf);
		            log.info("Mail processed: {}", mailCnf);
				});
    }
	
}
