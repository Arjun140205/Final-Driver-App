package com.examly.springapp.sprint;

import com.examly.springapp.mapper.ApiMapper;
import com.examly.springapp.model.DriverRequest;
import com.examly.springapp.model.Feedback;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RelationshipRegressionSprintTest {
    @Test
    void manyToOneRelationshipsKeepDefaultNoCascadeBehavior() throws Exception {
        assertEquals(0, DriverRequest.class.getDeclaredField("user").getAnnotation(ManyToOne.class).cascade().length);
        assertEquals(0, DriverRequest.class.getDeclaredField("driver").getAnnotation(ManyToOne.class).cascade().length);
        assertEquals(0, Feedback.class.getDeclaredField("user").getAnnotation(ManyToOne.class).cascade().length);
        assertEquals(0, Feedback.class.getDeclaredField("driver").getAnnotation(ManyToOne.class).cascade().length);
    }

    @Test
    void relationshipResponseMappingPreservesNestedSafeSummaries() {
        DriverRequest request = new DriverRequest();
        request.setUser(new com.examly.springapp.model.User(2L, "customer@example.com", "hashed-password", "customer", "9876543210", "Customer"));
        request.setDriver(new com.examly.springapp.model.Driver());
        request.getDriver().setDriverId(8L);
        request.getDriver().setDriverName("Asha Driver");

        var response = new ApiMapper().toDTO(request);
        assertNotNull(response.getUser());
        assertEquals(2L, response.getUser().getUserId());
        assertEquals(8L, response.getDriver().getDriverId());
    }
}
