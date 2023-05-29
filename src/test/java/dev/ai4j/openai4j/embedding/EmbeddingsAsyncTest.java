package dev.ai4j.openai4j.embedding;

import dev.ai4j.openai4j.OpenAiService;
import dev.ai4j.openai4j.RateLimitAwareTest;
import dev.ai4j.openai4j.ResponseHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class EmbeddingsAsyncTest extends RateLimitAwareTest {

    private static final String INPUT = "hello";

    private final OpenAiService openAiService = new OpenAiService(System.getenv("OPENAI_API_KEY"));

    static Stream<Arguments> testWithBuilder() {
        return Stream.of(
                Arguments.of(
                        EmbeddingRequest.builder()
                                .input(singletonList(INPUT))
                                .build()
                ),
                Arguments.of(
                        EmbeddingRequest.builder()
                                .input(INPUT)
                                .build()
                )
        );
    }

    @MethodSource
    @ParameterizedTest
    void testWithBuilder(EmbeddingRequest request) throws ExecutionException, InterruptedException, TimeoutException {

        CompletableFuture<EmbeddingResponse> future = new CompletableFuture<>();

        openAiService.getEmbeddingsAsync(request, new ResponseHandler<EmbeddingResponse>() {

            @Override
            public void onResponse(EmbeddingResponse response) {
                future.complete(response);
            }

            @Override
            public void onFailure(Throwable t) {
                future.completeExceptionally(t);
            }
        });


        EmbeddingResponse response = future.get(30, SECONDS);


        assertThat(response.data()).hasSize(1);
        assertThat(response.data().get(0).embedding()).hasSize(1536);

        assertThat(response.embedding()).hasSize(1536);
    }

    @Test
    void testWithInput() throws ExecutionException, InterruptedException, TimeoutException {

        CompletableFuture<List<Float>> future = new CompletableFuture<>();

        openAiService.getEmbeddingAsync(INPUT, new ResponseHandler<List<Float>>() {

            @Override
            public void onResponse(List<Float> embedding) {
                future.complete(embedding);
            }

            @Override
            public void onFailure(Throwable t) {
                future.completeExceptionally(t);
            }
        });


        List<Float> response = future.get(30, SECONDS);


        assertThat(response).hasSize(1536);
    }
}
