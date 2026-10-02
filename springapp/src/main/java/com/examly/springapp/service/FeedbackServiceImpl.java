package com.examly.springapp.service;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.repository.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private AiService aiService;

    @Override
    public Feedback createFeedback(Feedback feedback) {
        if (feedback.getDate() == null) {
            feedback.setDate(LocalDate.now());
        }
        try {
            // AI sentiment analysis + auto-tagging; feedback is still saved if the analysis fails.
            aiService.applyAnalysis(feedback);
        } catch (Exception ignored) {
            // keep the feedback without AI attributes
        }
        return feedbackRepo.save(feedback);
    }

    @Override
    public Feedback getFeedbackById(Long feedbackId) {
        return feedbackRepo.findById(feedbackId).orElse(null);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepo.findAll();
    }

    @Override
    public Feedback deleteFeedback(Long feedbackId) {
        Optional<Feedback> feedbackOpt = feedbackRepo.findById(feedbackId);
        if (feedbackOpt.isPresent()) {
            feedbackRepo.delete(feedbackOpt.get());
            return feedbackOpt.get();
        }
        return null;
    }

    @Override
    public List<Feedback> getFeedbacksByUserId(Long userId) {
        return feedbackRepo.findByUserUserId(userId);
    }
}
