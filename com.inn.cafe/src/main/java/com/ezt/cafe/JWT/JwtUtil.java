package com.ezt.cafe.JWT;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtUtil {

	// secret key for web json token generation
	private String secretKey = "F1nastra";

	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	@SuppressWarnings("deprecation")
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return ( username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}	

	public String generateToken(String username, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		return createToken(claims, username);		
	}
	
	@SuppressWarnings("deprecation")
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 36000))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}
	
}
