package mx.com.sixdelta.cloud.stream.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "mail")
public class MailAccount {

	private String host = "smtp-mail.outlook.com";
	private String port = "587";
	private String user = "your.mail@mail.com";
	private String password = null;
	
}
