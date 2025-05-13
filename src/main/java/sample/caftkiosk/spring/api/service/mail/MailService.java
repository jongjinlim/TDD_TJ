package sample.caftkiosk.spring.api.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.caftkiosk.spring.client.mail.MailSendClient;
import sample.caftkiosk.spring.domain.history.mail.MailSendHistory;
import sample.caftkiosk.spring.domain.history.mail.MailSendHistoryRepository;

@RequiredArgsConstructor
@Service
public class MailService {

	private final MailSendClient mailSendClient;
	private final MailSendHistoryRepository mailSendHistoryRepository;

	public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
		boolean result = mailSendClient.sendMail(fromEmail, toEmail, subject, content);
		if(result) {
			mailSendHistoryRepository.save(
					MailSendHistory.builder()
							.fromEmail(fromEmail)
							.toEmail(toEmail)
							.subject(subject)
							.content(content)
							.build()
			);
			return true;
		}
		return false;
	}
}
