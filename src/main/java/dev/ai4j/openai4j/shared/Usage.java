package dev.ai4j.openai4j.shared;

public final class Usage {

    private final Integer promptTokens;
    private final Integer completionTokens;
    private final Integer totalTokens;

    private Usage(Builder builder) {
        this.promptTokens = builder.promptTokens;
        this.completionTokens = builder.completionTokens;
        this.totalTokens = builder.totalTokens;
    }

    public Integer promptTokens() {
        return promptTokens;
    }

    public Integer completionTokens() {
        return completionTokens;
    }

    public Integer totalTokens() {
        return totalTokens;
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        return another instanceof Usage
                && equalTo((Usage) another);
    }

    private boolean equalTo(Usage another) {
        return promptTokens.equals(another.promptTokens)
                && completionTokens.equals(another.completionTokens)
                && totalTokens.equals(another.totalTokens);
    }

    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + promptTokens.hashCode();
        h += (h << 5) + completionTokens.hashCode();
        h += (h << 5) + totalTokens.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return "Usage{"
                + "promptTokens=" + promptTokens
                + ", completionTokens=" + completionTokens
                + ", totalTokens=" + totalTokens
                + "}";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Integer promptTokens;
        private Integer completionTokens;
        private Integer totalTokens;

        private Builder() {
        }

        public Builder promptTokens(Integer promptTokens) {
            this.promptTokens = promptTokens;
            return this;
        }

        public Builder completionTokens(Integer completionTokens) {
            this.completionTokens = completionTokens;
            return this;
        }

        public Builder totalTokens(Integer totalTokens) {
            this.totalTokens = totalTokens;
            return this;
        }

        public Usage build() {
            return new Usage(this);
        }
    }
}
