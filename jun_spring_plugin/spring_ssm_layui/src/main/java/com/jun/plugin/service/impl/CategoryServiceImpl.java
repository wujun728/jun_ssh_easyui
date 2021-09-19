package com.jun.plugin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.common.utils.IDUtil;
import com.jun.plugin.entity.Category;
import com.jun.plugin.mapper.CategoryMapper;
import com.jun.plugin.service.CategoryService;

import java.util.Date;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询所有种类
     * @return
     */
    @Override
    public List<Category> queryAllCategory() {
        return categoryMapper.selectByExample(null);
    }

    /**
     * 添加种类
     * @param category
     * @return
     */
    @Override
    public Integer insertGagegory(Category category) {
        // 设置默认值
        category.setId(IDUtil.getRandomIdByUUID());
        category.setCreateDate(new Date());
        category.setUpdateDate(new Date());
        return categoryMapper.insert(category);
    }
}
