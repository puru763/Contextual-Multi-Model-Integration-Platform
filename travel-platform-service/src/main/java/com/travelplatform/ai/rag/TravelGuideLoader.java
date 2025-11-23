package com.travelplatform.ai.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TravelGuideLoader {

    private final VectorStore vectorStore;

    public TravelGuideLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadTravelGuideContent() {
        List<String> travelContent = List.of(
                "Paris, France is known as the City of Light and offers world-class museums like the Louvre, iconic landmarks such as the Eiffel Tower, and exquisite cuisine.",
                "Tokyo, Japan combines traditional temples with modern technology, featuring excellent public transportation, diverse neighborhoods, and unique cultural experiences.",
                "New York City offers Broadway shows, Central Park, diverse neighborhoods, world-class dining, and iconic landmarks like the Statue of Liberty.",
                "Bali, Indonesia is famous for its beautiful beaches, ancient temples, rice terraces, and vibrant culture perfect for relaxation and adventure.",
                "London, England features historic landmarks like Big Ben and the Tower of London, world-class museums, and a rich cultural heritage.",
                "Dubai, UAE is known for luxury shopping, ultramodern architecture, and vibrant nightlife, with attractions like the Burj Khalifa and Palm Jumeirah.",
                "Rome, Italy is home to ancient ruins like the Colosseum, Vatican City, incredible art, and authentic Italian cuisine.",
                "Santorini, Greece offers stunning sunsets, white-washed buildings, beautiful beaches, and excellent wine tasting experiences.",
                "Sydney, Australia features the iconic Opera House, beautiful harbor, Bondi Beach, and a vibrant food and arts scene.",
                "Barcelona, Spain combines Gothic architecture, beautiful beaches, world-renowned cuisine, and the unique works of Antoni Gaudí.",
                "Travel insurance is essential for international trips to cover medical emergencies, trip cancellations, and lost luggage.",
                "Visa requirements vary by country and nationality; always check entry requirements before booking international travel.",
                "Best time to visit Europe is typically spring (April-June) or fall (September-October) for pleasant weather and fewer crowds.",
                "Traveling during off-peak seasons can save up to 40% on flights and accommodations while avoiding tourist crowds.",
                "Packing light with versatile clothing items and following airline baggage restrictions helps avoid extra fees and travel stress.",
                "Using travel apps for currency conversion, language translation, and local recommendations enhances the travel experience.",
                "Booking flights 6-8 weeks in advance typically offers the best balance between price and availability.",
                "Hotel loyalty programs can provide free nights, room upgrades, and exclusive benefits for frequent travelers.",
                "Travel credit cards often offer rewards points, travel insurance, and airport lounge access for frequent travelers.",
                "Researching local customs, tipping practices, and cultural norms before traveling helps ensure respectful interactions."
        );

        List<Document> documents = travelContent.stream()
                .map(content -> new Document(content))
                .toList();

        TextSplitter textSplitter =
                TokenTextSplitter.builder().withChunkSize(200).withMaxNumChunks(400).build();
        vectorStore.add(textSplitter.split(documents));
    }
}

