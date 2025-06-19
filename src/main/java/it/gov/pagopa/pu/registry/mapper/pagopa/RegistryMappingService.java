package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.utils.RegistryUtils;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class RegistryMappingService {

  public <T, R> List<R> mapRegistries(
    T dto,
    Function<T, String> iuvGetter,
    Function<T, String> navGetter,
    TriFunction<T, String, String, R> builder) {

    String[] iuvs = RegistryUtils.split(iuvGetter.apply(dto));
    String[] navs = RegistryUtils.split(navGetter.apply(dto));

    int maxSize = Math.max(iuvs.length, navs.length);
    maxSize = Math.max(1, maxSize);

    Stream<String> iuvStream = RegistryUtils.streamAndExtend(iuvs, maxSize);
    Stream<String> navStream = RegistryUtils.streamAndExtend(navs, maxSize);

    return StreamUtils.zip(iuvStream, navStream,
        (iuv, nav) -> builder.apply(dto, iuv, nav))
      .toList();
  }
}

