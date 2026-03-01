package org.project.event_managment.domain;

public enum Status {
    PENDING,      // créé, pas encore envoyé à RabbitMQ
    PUBLISHED,    // envoyé avec succès à RabbitMQ
    FAILED,       // échec d'envoi à RabbitMQ
    REPLAYED      // rejoué après un FAILED
}
