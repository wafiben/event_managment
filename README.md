┌─────────────────────────────────────────────────────────────┐
│                    EVENT SERVICE                            │
│  ┌──────────────┐                                           │
│  │ REST API     │  ← Other services call this via HTTP      │
│  └──────┬───────┘                                           │
│         │                                                    │
│         ▼                                                    │
│  ┌──────────────────────────────────────────┐              │
│  │  RabbitMQ Integration (ONLY HERE!)       │              │
│  │  • RabbitTemplate                        │              │
│  │  • Exchange/Queue/Binding Configuration  │              │
│  │  • Message Publishing Logic              │              │
│  └──────────────────┬───────────────────────┘              │
└─────────────────────┼───────────────────────────────────────┘
│
▼
┌──────────────────┐
│   RabbitMQ Broker │  ← Separate container/server
│   (Docker)        │
└─────────┬─────────┘
│
┌─────────────┼─────────────┐
▼             ▼             ▼
┌──────────┐  ┌──────────┐  ┌──────────┐
│Notification│  │Other   │  │  Other   │
│  Service   │  │ Service  │  │ Service  │
│            │  │          │  │          │
│ RabbitMQ   │  │ RabbitMQ │  │ RabbitMQ │
│ Consumer   │  │ Consumer │  │ Consumer │
└──────────┘  └──────────┘  └──────────┘