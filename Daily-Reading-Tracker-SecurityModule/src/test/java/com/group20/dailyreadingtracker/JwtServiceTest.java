package com.group20.dailyreadingtracker;

import com.group20.dailyreadingtracker.securitymodule.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtService, "jwtSecret", "testSecret");
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 3600000); // 1 hour
    }

    @Test
    public void testGenerateJwtToken() {
        String username = "testUser";
        String token = jwtService.generateJwtToken(username);

        assertNotNull(token);
        String extractedUsername = Jwts.parser()
                .setSigningKey("testSecret")
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testGetUsernameFromJwtToken() {
        String username = "testUser";
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 3600000))
                .signWith(SignatureAlgorithm.HS512, "testSecret")
                .compact();

        String extractedUsername = jwtService.getUsernameFromJwtToken(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testValidateJwtToken() {
        String token = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 3600000))
                .signWith(SignatureAlgorithm.HS512, "testSecret")
                .compact();

        assertTrue(jwtService.validateJwtToken(token));

        String invalidToken = token + "invalid";
        assertFalse(jwtService.validateJwtToken(invalidToken));
    }
}