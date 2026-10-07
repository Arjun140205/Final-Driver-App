package com.examly.springapp.service;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.dto.FeedbackDTO;
import com.examly.springapp.mapper.ApiMapper;
import com.examly.springapp.repository.FeedbackRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepo feedbackRepo;
    private final AiService aiService;
    private final ApiMapper mapper;

    public FeedbackServiceImpl(FeedbackRepo feedbackRepo, AiService aiService, ApiMapper mapper) {
        this.feedbackRepo = feedbackRepo;
        this.aiService = aiService;
        this.mapper = mapper;
    }

    @Override
    public FeedbackDTO createFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = mapper.toEntity(feedbackDTO);
        if (feedback.getDate() == null) {
            feedback.setDate(LocalDate.now());
        }
        try {
            // AI sentiment analysis + auto-tagging; feedback is still saved if the analysis fails.
            aiService.applyAnalysis(feedback);
        } catch (Exception ignored) {
            // keep the feedback without AI attributes
        }
        return mapper.toDTO(feedbackRepo.save(feedback));
    }

    @Override
    public FeedbackDTO getFeedbackById(Long feedbackId) {
        return feedbackRepo.findById(feedbackId).map(mapper::toDTO).orElse(null);
    }

    @Override
    public List<FeedbackDTO> getAllFeedbacks() {
        return mapper.toFeedbackDTOs(feedbackRepo.findAll());
    }

    @Override
    public FeedbackDTO deleteFeedback(Long feedbackId) {
        Optional<Feedback> feedbackOpt = feedbackRepo.findById(feedbackId);
        if (feedbackOpt.isPresent()) {
            feedbackRepo.delete(feedbackOpt.get());
            return mapper.toDTO(feedbackOpt.get());
        }
        return null;
    }

    @Override
    public List<FeedbackDTO> getFeedbacksByUserId(Long userId) {
        return mapper.toFeedbackDTOs(feedbackRepo.findByUserUserId(userId));
    }
}
