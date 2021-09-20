package com.feri.fyw.dao;

import com.feri.fyw.entity.Project;
import com.feri.fyw.entity.Subject;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-17 10:53
 */
public interface ProjectDao {
    int insert(Project project);
    List<Project> selectAll();
    int update(Project project);
}
