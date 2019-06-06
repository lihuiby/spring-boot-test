package com.py.web.print;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.*;

/**
 * @Description:
 * @Author: zhenghui.li
 * @Date: Created in 2019/06/05
 */
public class JWTTest {

	private static String security = "34e8a099a6bf25c73363017ab542e862";

	@Test
	public void test01() {

		String token1 = createToken();
		System.out.println("token1 = " + token1);
		String nodeToken =
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoibmFtZS1saXNpIiwidXNlcklEIjozMywiaWF0IjoxNTU5NzE1NTE2LCJleHAiOjE1NTk3MjI3MTZ9.HByyDw-cYqUpKqyybya2Rom6XvcTzNh7rUKjBmCO6ow"
				;

		verifyToken(token1);
		verifyToken(nodeToken);
	}

	private static String createToken() {

		Algorithm algorithm = Algorithm.HMAC256(security);
		Map<String, Object> map = new HashMap<String, Object>();
		Date nowDate = new Date();
		Date expireDate = getAfterDate(nowDate,0,0,0,2,0,0);//2小过期
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String token = JWT.create()
				/*设置头部信息 Header*/
				.withHeader(map)
				/*设置 载荷 Payload*/
				.withClaim("name", "name")
				.withClaim("userID", 3)
//				.withIssuer("SERVICE")//签名是有谁生成 例如 服务器
//				.withSubject("this is test token")//签名的主题
				//.withNotBefore(new Date())//定义在什么时间之前，该jwt都是不可用的.
//				.withAudience("APP")//签名的观众 也可以理解谁接受签名的
//				.withIssuedAt(nowDate) //生成签名的时间
				.withExpiresAt(expireDate)//签名过期的时间
				/*签名 Signature */
				.sign(algorithm);
		return token;
	}

	public static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second){
		if(date == null){
			date = new Date();
		}

		Calendar cal = new GregorianCalendar ();

		cal.setTime(date);
		if(year != 0){
			cal.add(Calendar.YEAR, year);
		}
		if(month != 0){
			cal.add(Calendar.MONTH, month);
		}
		if(day != 0){
			cal.add(Calendar.DATE, day);
		}
		if(hour != 0){
			cal.add(Calendar.HOUR_OF_DAY, hour);
		}
		if(minute != 0){
			cal.add(Calendar.MINUTE, minute);
		}
		if(second != 0){
			cal.add(Calendar.SECOND, second);
		}
		return cal.getTime();
	}

	public static void verifyToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(security);
		JWTVerifier verifier = JWT.require(algorithm)
//			.withIssuer("SERVICE")
				.build(); //Reusable verifier instance
		DecodedJWT jwt = verifier.verify(token);
		String subject = jwt.getSubject();
		Map<String, Claim> claims = jwt.getClaims();
		Claim claim = claims.get("name");
		Claim userId = claims.get("userID");
		System.out.println("name = " + claim.asString());
		System.out.println("userID = " + userId.asInt());
		List<String> audience = jwt.getAudience();
//		System.out.println(subject);
//		System.out.println(audience.get(0));
	}
}
