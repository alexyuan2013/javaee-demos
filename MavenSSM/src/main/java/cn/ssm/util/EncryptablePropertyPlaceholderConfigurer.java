package cn.ssm.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 自定义的取加密信息的类
 * 
 * @author Mintie
 *
 */
public class EncryptablePropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	private static final String key = "0002000200020002";

	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		try {
			DesUtil des = new DesUtil();
			String username = props.getProperty("jdbc_username");
			if (username != null) {
				props.setProperty("jdbc_username",
						des.Decrypt(username, des.hex2byte(key)));
			}

			String password = props.getProperty("jdbc_password");
			if (password != null) {
				props.setProperty("jdbc_password",
						des.Decrypt(password, des.hex2byte(key)));
			}

			String url = props.getProperty("jdbc_url");
			if (url != null) {
				props.setProperty("jdbc_url",
						des.Decrypt(url, des.hex2byte(key)));
			}

			String driverClassName = props.getProperty("jdbc_driverClassName");
			if (driverClassName != null) {
				props.setProperty("jdbc_driverClassName",
						des.Decrypt(driverClassName, des.hex2byte(key)));
			}

			super.processProperties(beanFactory, props);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BeanInitializationException(e.getMessage());
		}
	}
}
