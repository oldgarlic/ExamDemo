package com.lll.online.exam.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页基本信息
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePage {
    private Integer pageIndex;
    private Integer pageSize;
}
