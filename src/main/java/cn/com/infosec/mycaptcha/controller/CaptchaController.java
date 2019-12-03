package cn.com.infosec.mycaptcha.controller;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.servlet.CaptchaServletUtil;

@Controller
public class CaptchaController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/")
	public String indexController(ModelMap modelMap) {

		logger.info("mycaptcha: /WEB-INF/jsp/mycaptcha.jsp");
		return "mycaptcha";
	}

	@ResponseBody
	@RequestMapping("captcha.do")
	public Map<String, Object> captchaController(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);

		map.put("verCode", specCaptcha.text().toLowerCase());
		map.put("verImage", specCaptcha.toBase64());

		request.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());
		logger.info("Create Code Succeed: " + specCaptcha.text().toLowerCase());
		return map;
	}

	@RequestMapping("login.do")
	public void loginController(@RequestParam(value = "verCode") String verCode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CaptchaUtil.ver(verCode, request)) {
			CaptchaUtil.clear(request);
			logger.error("Verify Code Failed: " + verCode);
			response.getWriter().write("false");
		} else {
			logger.info("Verify Code Succeed: " + verCode);
			response.getWriter().write("true");
		}
		response.getWriter().close();
	}

	@RequestMapping("simpleCaptcha.do")
	public void simpleCaptchaController(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Captcha captcha = new Captcha.Builder(200, 50).addText().addBackground().addBorder().build();
		CaptchaServletUtil.writeImage(response, captcha.getImage());

		request.getSession().setAttribute("simpleCaptcha", captcha.getAnswer());
		logger.info("Simple Captcha Succeed: " + captcha.getAnswer());

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(captcha.getAnswer().getBytes());
		logger.info("{}", md.digest());

	}

}
