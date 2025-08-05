package com.example.mywebiste.service;




import com.example.mywebiste.Model.ScheduleRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class ZoomService {

    @Value("${zoom.api.key}")
    private String zoomApiKey;

    @Value("${zoom.api.secret}")
    private String zoomApiSecret;

    @Value("${zoom.user.id}")
    private String zoomUserId;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Generates a JWT token for Zoom API authentication.
     * The token is valid for 1 hour.
     * @return JWT token string
     */
    private String generateJwtToken() {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); // Token valid for 1 hour

        // Use Keys.hmacShaKeyFor to correctly derive the key from the secret
        // This is more robust than manual SecretKeySpec creation for HS256
        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setIssuer(zoomApiKey)
                .setExpiration(expiration)
                .setIssuedAt(now)
                .signWith(Keys.hmacShaKeyFor(zoomApiSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Creates a Zoom meeting using the Zoom API.
     * @param meetingForm Data from the meeting scheduling form
     * @return The join_url of the created meeting, or null if creation fails
     */
    public String createZoomMeeting(ScheduleRequest meetingForm) {
        String jwtToken = generateJwtToken();
        String zoomApiUrl = "https://api.zoom.us/v2/users/" + zoomUserId + "/meetings";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwtToken);

        // Construct meeting details payload
        Map<String, Object> meetingDetails = new HashMap<>();
        meetingDetails.put("topic", "Meeting with " + meetingForm.getName());
        meetingDetails.put("type", 2); // Scheduled meeting

        // Ensure the date and time are correctly formatted for Zoom API
        // Zoom expects ISO 8601 format, e.g., "YYYY-MM-DDTHH:MM:SSZ"
        // Adjust timezone as needed. For simplicity, we'll assume UTC for Z.
        String dateTime = meetingForm.getMeetingDate() + "T" + meetingForm.getMeetingTime() + ":00Z";
        meetingDetails.put("start_time", dateTime);

        meetingDetails.put("duration", 60); // minutes
        meetingDetails.put("timezone", "America/New_York"); // IMPORTANT: Set correct timezone
        meetingDetails.put("password", generateMeetingPassword()); // Optional: set a password
        meetingDetails.put("agenda", "Discussion related to portfolio contact.");

        Map<String, Object> settings = new HashMap<>();
        settings.put("host_video", false);
        settings.put("participant_video", false);
        settings.put("cn_meeting", false);
        settings.put("in_meeting", false);
        settings.put("join_before_host", true);
        settings.put("mute_participants_upon_entry", true);
        settings.put("watermark", false);
        settings.put("use_pmi", false);
        settings.put("approval_type", 0); // 0: Automatically approve, 1: Manually approve, 2: No registration required
        settings.put("audio", "both"); // voip, telephone, both
        settings.put("auto_recording", "none"); // local, cloud, none
        settings.put("enforce_login", false);
        settings.put("waiting_room", false);
        meetingDetails.put("settings", settings);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(meetingDetails, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    zoomApiUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
                return (String) response.getBody().get("join_url");
            } else {
                System.err.println("Failed to create Zoom meeting. Status: " + response.getStatusCode() + ", Body: " + response.getBody());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error calling Zoom API: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private String generateMeetingPassword() {
        // Generate a simple password (e.g., 6 random digits)
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}
