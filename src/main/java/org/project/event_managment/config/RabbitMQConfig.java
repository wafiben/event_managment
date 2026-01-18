package org.project.event_managment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // Exchange name
    public static final String EVENT_EXCHANGE = "user.event.exchange";
    // Queue name
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    // Routing key
    public static final String USER_REGISTERED_KEY = "user.registered";

    // ===== OUTGOING (sends to another service)
    public static final String ANALYTICS_EXCHANGE = "notification.exchange";
    public static final String NOTIFICATION_SENT_KEY = "user.notification.sent";

    /**
     * Create the Topic Exchange
     */
    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange(EVENT_EXCHANGE, true, false);
    }

    /**
     * Create the Queue
     */
    @Bean
    public Queue notificationQueue() {
        return QueueBuilder
                .durable(NOTIFICATION_QUEUE)
                .build();
    }

    /**
     * JSON Message Converter
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Bind Queue to Exchange with Routing Key
     */
    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(eventExchange())
                .with(USER_REGISTERED_KEY);
    }

    /**
     * RabbitTemplate for publishing messages
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}