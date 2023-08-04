package org.fundacionjala.virtualassistant.util.either;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
@Component
public class Either<L, R> implements ProcessorEither<L, R> {
  private final L left;
  private final R right;

  public static <L, R> Either<L, R> Left(L value) {
    return new Either<>(value, null);
  }

  public static <L, R> Either<L, R> Right(R value) {
    return new Either<>(null, value);
  }

  public boolean isLeft() {
    return left != null;
  }

  public boolean isRight() {
    return right != null;
  }

  @Override
  public <T> Function<T, Either<L, R>> lift(Function<T, Either<L, R>> function) {
    return function;
  }
}
