package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.annotations.Name;
import com.hurricane.components.sequencer.step.NamedStep;
import com.hurricane.components.sequencer.step.Step;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StepNameExtractor {
    private final Supplier<String> nameProvider;

    public String extract() {
        return nameProvider.get();
    }

    private static final ExtractorStrategy strategy = new ByInheritanceExtractor()
            .next(new ByAnnotationExtractor())
            .next(new DefaultExtractor());

    public static StepNameExtractor of(final Step step) {
        return new StepNameExtractor(() -> strategy.extract(step));
    }


    private interface ExtractorStrategy {
        ExtractorStrategy next(ExtractorStrategy strategy);
        String extract(Step step);
    }

    private static abstract class ExtractorWithSuccessorStrategy implements ExtractorStrategy {
        protected ExtractorStrategy nextStrategy;

        @Override
        public ExtractorStrategy next(final ExtractorStrategy strategy) {
            if (this.nextStrategy != null) {
                this.nextStrategy.next(strategy);
            } else {
                this.nextStrategy = strategy;
            }
            return this;
        }

        @Override
        public String extract(final Step step) {
            if (accept(step)) {
                return doExtract(step);
            } else {
                return nextStrategy.extract(step);
            }
        }

        protected abstract boolean accept(Step step);

        public abstract String doExtract(Step step);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class ByInheritanceExtractor extends ExtractorWithSuccessorStrategy {
        @Override
        protected boolean accept(final Step step) {
            return step instanceof NamedStep;
        }

        @Override
        public String doExtract(final Step step) {
            return ((NamedStep) step).name();
        }
    }


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class ByAnnotationExtractor extends ExtractorWithSuccessorStrategy {
        @Override
        public boolean accept(final Step step) {
            return step.getClass().isAnnotationPresent(Name.class);
        }

        @Override
        public String doExtract(final Step step) {
            return step.getClass().getAnnotation(Name.class).value();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class DefaultExtractor implements ExtractorStrategy {
        @Override
        public ExtractorStrategy next(ExtractorStrategy strategy) {
            throw new UnsupportedOperationException("It's a default extractor. It should be used as last");
        }

        @Override
        public String extract(final Step step) {
            return step.getClass().getCanonicalName();
        }
    }
}
