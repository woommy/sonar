/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.startup;

import org.junit.Test;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.config.PropertyDefs;
import org.sonar.core.properties.PropertiesDao;

import static org.mockito.Mockito.*;

public class RenameDeprecatedPropertyKeysTest {
  @Test
  public void should_rename_deprecated_keys() {
    PropertiesDao dao = mock(PropertiesDao.class);
    PropertyDefs definitions = new PropertyDefs(FakeExtension.class);
    RenameDeprecatedPropertyKeys task = new RenameDeprecatedPropertyKeys(dao, definitions);
    task.start();

    verify(dao).renamePropertyKey("old_key", "new_key");
    verifyNoMoreInteractions(dao);
  }

  @Properties({
    @Property(key = "new_key", deprecatedKey = "old_key", name = "Name"),
    @Property(key = "other", name = "Other")
  })
  public static class FakeExtension {

  }
}
