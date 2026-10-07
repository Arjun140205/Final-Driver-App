package com.examly.springapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/** API representation of feedback with only the related safe DTOs. */
public class FeedbackDTO {
    private Long feedbackId;
    @NotBlank @Size(max = 255) private String feedbackText;
    private LocalDate date;
    @NotNull private UserDTO user;
    private DriverDTO driver;
    @NotBlank private String category;
    @NotNull @Min(1) @Max(5) private Integer rating;
    private String sentiment;
    private Double sentimentScore;
    private String aiTags;

    public Long getFeedbackId() { return feedbackId; }
    public void setFeedbackId(Long feedbackId) { this.feedbackId = feedbackId; }
    public String getFeedbackText() { return feedbackText; }
    public void setFeedbackText(String feedbackText) { this.feedbackText = feedbackText; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
    public DriverDTO getDriver() { return driver; }
    public void setDriver(DriverDTO driver) { this.driver = driver; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }
    public Double getSentimentScore() { return sentimentScore; }
    public void setSentimentScore(Double sentimentScore) { this.sentimentScore = sentimentScore; }
    public String getAiTags() { return aiTags; }
    public void setAiTags(String aiTags) { this.aiTags = aiTags; }
}
