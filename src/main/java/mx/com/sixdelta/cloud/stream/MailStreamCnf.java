package mx.com.sixdelta.cloud.stream;

import java.util.function.Consumer;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;
import mx.com.sixdelta.cloud.stream.bean.Mail;
import mx.com.sixdelta.cloud.stream.bean.MailAccount;
import mx.com.sixdelta.cloud.stream.service.MailService;
import mx.com.sixdelta.cloud.stream.service.MailServiceImpl;
import reactor.core.publisher.Flux;

@Log4j2
@EnableConfigurationProperties(MailAccount.class)
@Configuration
public class MailStreamCnf {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MailStreamCnf.class);
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
