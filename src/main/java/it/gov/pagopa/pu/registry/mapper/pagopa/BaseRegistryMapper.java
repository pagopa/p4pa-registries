package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.utils.Utilities;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public abstract class BaseRegistryMapper<T, R> {

  public List<R> map(T dto) {
    String[] iuvs = Utilities.splitCommaString(getIuv(dto));
    String[] navs = Utilities.splitCommaString(getNav(dto));

    int maxSize = Math.max(iuvs.length, navs.length);
    maxSize = Math.max(1, maxSize);

    Stream<String> iuvStream = Utilities.streamAndExtend(iuvs, maxSize, null);
    Stream<String> navStream = Utilities.streamAndExtend(navs, maxSize, null);

    return StreamUtils.zip(iuvStream, navStream, (iuv, nav) -> build(dto, iuv, nav))
      .toList();
  }

  protected abstract String getIuv(T dto);

  protected abstract String getNav(T dto);

  protected abstract R build(T dto, String iuv, String nav);

}

