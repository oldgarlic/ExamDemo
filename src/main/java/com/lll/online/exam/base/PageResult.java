package com.lll.online.exam.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页查询结果
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    // TODO：为了迎合前端需求，正常应该是pageIndex，pageSize，total，data

    // 结果集 List<UserResponseVM>
    private List<T> list;
    // 总记录数
    private Long total;
    // 当前页数
    private Long pageNum;
}
