package uk.ac.bbsrc.tgac.miso.persistence;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import uk.ac.bbsrc.tgac.miso.core.data.ScientificName;

public interface ScientificNameDao extends SaveDao<ScientificName> {

  public ScientificName getByAlias(String alias) throws IOException;

  public long getUsageBySamples(ScientificName scientificName) throws IOException;

  public long getUsageByReferenceGenomes(ScientificName scientificName) throws IOException;

  List<ScientificName> listByIdList(Collection<Long> ids) throws IOException;

}
