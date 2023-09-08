package org.fundacionjala.virtualassistant.taskhandler.intents;

import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;

public class EntityArgs {
    private IntentEntity primaryArg;
    private IntentEntity secondaryArg;

    private EntityArgs() {
    }

    public IntentEntity getPrimaryArg() {
        return primaryArg;
    }

    public IntentEntity getSecondaryArg() {
        return secondaryArg;
    }

    public static class Builder {
        private IntentEntity primaryArg;
        private IntentEntity secondaryArg;

        public Builder setPrimaryArg(IntentEntity primaryArg) {
            this.primaryArg = primaryArg;
            return this;
        }

        public Builder setSecondaryArg(IntentEntity secondaryArg) {
            this.secondaryArg = secondaryArg;
            return this;
        }

        public EntityArgs build() {
            EntityArgs entityArgs = new EntityArgs();
            entityArgs.primaryArg = this.primaryArg;
            entityArgs.secondaryArg = this.secondaryArg;
            return entityArgs;
        }
    }
}
