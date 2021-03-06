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

package org.sonar.server.group;

import org.sonar.api.ServerComponent;
import org.sonar.core.user.*;
import org.sonar.server.exceptions.NotFoundException;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class GroupMembershipFinder implements ServerComponent {

  private final UserDao userDao;
  private final GroupMembershipDao groupMembershipDao;

  public GroupMembershipFinder(UserDao userDao, GroupMembershipDao groupMembershipDao) {
    this.userDao = userDao;
    this.groupMembershipDao = groupMembershipDao;
  }

  public GroupMembershipQueryResult find(GroupMembershipQuery query) {
    Long userId = userId(query.login());
    int pageSize = query.pageSize();
    int pageIndex = query.pageIndex();

    int offset = (pageIndex - 1) * pageSize;
    // Add one to page size in order to be able to know if there's more results or not
    int limit = pageSize + 1;
    List<GroupMembershipDto> dtos = groupMembershipDao.selectGroups(query, userId, offset, limit);
    boolean hasMoreResults = false;
    if (dtos.size() == limit) {
      hasMoreResults = true;
      // Removed last entry as it's only need to know if there more results or not
      dtos.remove(dtos.size() - 1);
    }
    return new GroupMembershipQueryResult(toGroupMembership(dtos), hasMoreResults);
  }

  private Long userId(String login) {
    UserDto userDto = userDao.selectActiveUserByLogin(login);
    if (userDto == null) {
      throw new NotFoundException("User '"+ login +"' does not exists.");
    }
    return userDto.getId();
  }

  private List<GroupMembership> toGroupMembership(List<GroupMembershipDto> dtos) {
    List<GroupMembership> groups = newArrayList();
    for (GroupMembershipDto groupMembershipDto : dtos) {
      groups.add(groupMembershipDto.toGroupMembership());
    }
    return groups;
  }
}
