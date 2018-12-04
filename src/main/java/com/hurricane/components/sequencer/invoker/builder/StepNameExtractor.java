package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.annotations.Name;
import com.hurricane.components.sequencer.step.NamedStep;
import com.hurricane.components.sequencer.step.Step;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StepNameExtractor {
    private final Supplier<String> nameProvider;

    public String extract() {
        return nameProvider.get();
    }

//    private static final ExtractorStrategy stategy = new ByInheritanceExtractor()
//            .next(new ByAnnotationExtractor())
//            .next(new DefaultExtractor());

    private static final List<ExtractorStrategy> strategies = Arrays.asList(
            new ByInheritanceExtractor(), new ByAnnotationExtractor(), new DefaultExtractor()
    );

    static StepNameExtractor of(final Step step) {
        final Optional<ExtractorStrategy> foundStrategy = strategies.stream()
                .filter(strategy -> strategy.accept(step))
                .findFirst();
        final ExtractorStrategy extractorStrategy = foundStrategy
                .orElseThrow(() -> new IllegalArgumentException(""));

        return new StepNameExtractor(() -> extractorStrategy.extract(step));

    }


    private interface ExtractorStrategy {
        boolean accept(Step step);
//        ExtractorStrategy next(ExtractorStrategy strategy);
        String extract(Step step);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class ByInheritanceExtractor implements ExtractorStrategy {
//        private ExtractorStrategy nextStrategy;
//
//        @Override
//        public ExtractorStrategy next(final ExtractorStrategy strategy) {
//            if (this.nextStrategy != null) {
//                return this.nextStrategy.next(strategy);
//            } else {
//                return this.nextStrategy = strategy;
//            }
//        }

        @Override
        public boolean accept(final Step step) {
            return step instanceof NamedStep;
        }

        @Override
        public String extract(final Step step) {
            if (accept(step)) {
                return ((NamedStep) step).name();
            } else {
//                return nextStrategy.extract(step);
                return null;
            }
        }
    }


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class ByAnnotationExtractor implements ExtractorStrategy {
        @Override
        public boolean accept(final Step step) {
            return step.getClass().isAnnotationPresent(Name.class);
        }

        @Override
        public String extract(final Step step) {
            final Name name = step.getClass().getAnnotation(Name.class);
            return name.value();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class DefaultExtractor implements ExtractorStrategy {
        @Override
        public boolean accept(final Step step) {
            return true;
        }

        @Override
        public String extract(final Step step) {
            return step.getClass().getCanonicalName();
        }
    }
}
