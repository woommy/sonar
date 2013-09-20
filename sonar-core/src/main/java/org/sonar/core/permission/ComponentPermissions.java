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

package org.sonar.core.permission;

import com.google.common.collect.ImmutableList;
import org.sonar.api.web.UserRole;

import java.util.List;

/**
 * Holds the constants representing the various component permissions that can be assigned to users & groups
 *
 */
public final class ComponentPermissions {

  /**
   * All the component permissions values, ordered from {@link UserRole#USER} to {@link UserRole#CODEVIEWER}.
   */
  public static final List<String> ALL = ImmutableList.of(UserRole.USER, UserRole.ADMIN, UserRole.CODEVIEWER);


}
