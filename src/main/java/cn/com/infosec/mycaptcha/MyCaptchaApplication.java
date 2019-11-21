package cn.com.infosec.mycaptcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyCaptchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCaptchaApplication.class, args);
	}

	/*
	 * @Bean public ServletRegistrationBean<CaptchaServlet> captchaServlet() {
	 * return new ServletRegistrationBean<CaptchaServlet>(new CaptchaServlet(),
	 * "/captcha.do"); }
	 */
}