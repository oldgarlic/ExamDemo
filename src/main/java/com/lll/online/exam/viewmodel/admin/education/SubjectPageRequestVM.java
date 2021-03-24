package com.lll.online.exam.viewmodel.admin.education;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Subject分页查询接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectPageRequestVM extends BasePage {
    private Integer id;
    private Integer level;
}
