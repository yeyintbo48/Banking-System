CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE Table IF NOT EXISTS system_config(
    config_key VARCHAR(50) PRIMARY KEY,
    config_value VARCHAR(255) NOT NULL,
    config_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO system_config(config_key,config_value)
VALUES ('APP_VERSION','1.0.0-MILESTONE-1')
ON CONFLICT (config_key) DO NOTHING;