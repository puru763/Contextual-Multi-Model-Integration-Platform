package com.travelplatform.ai.tools;

import com.travelplatform.ai.entity.TravelBooking;
import com.travelplatform.ai.model.TravelBookingRequest;
import com.travelplatform.ai.service.TravelBookingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TravelBookingTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(TravelBookingTools.class);

    private final TravelBookingService service;

    @Tool(name = "createBooking", description = "Create a new travel booking for flights, hotels, packages, or car rentals", returnDirect = true)
    String createBooking(@ToolParam(description = "Details to create a travel booking including destination, travel type, and additional details")
            TravelBookingRequest bookingRequest, ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        LOGGER.info("Creating travel booking for user: {} with details: {}", username, bookingRequest);
        TravelBooking savedBooking = service.createBooking(bookingRequest, username);
        LOGGER.info("Booking created successfully. Booking ID: {}, Username: {}, Destination: {}", 
                savedBooking.getId(), savedBooking.getUsername(), savedBooking.getDestination());
        return "Travel booking #" + savedBooking.getId() + " created successfully for " + 
               savedBooking.getDestination() + " (Type: " + savedBooking.getTravelType() + ")";
    }

    @Tool(description = "Fetch the status of travel bookings based on a given username")
    List<TravelBooking> getBookingStatus(ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        LOGGER.info("Fetching travel bookings for user: {}", username);
        List<TravelBooking> bookings = service.getBookingsByUsername(username);
        LOGGER.info("Found {} bookings for user: {}", bookings.size(), username);
        return bookings;
    }

    @Tool(description = "Update the status of an existing travel booking")
    String updateBookingStatus(@ToolParam(description = "The booking ID to update") Long bookingId,
                              @ToolParam(description = "The new status: PENDING, CONFIRMED, CANCELLED, or COMPLETED") String status,
                              ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        LOGGER.info("Updating booking {} to status {} for user: {}", bookingId, status, username);
        TravelBooking updatedBooking = service.updateBookingStatus(bookingId, status);
        return "Booking #" + updatedBooking.getId() + " status updated to " + updatedBooking.getStatus();
    }

}
