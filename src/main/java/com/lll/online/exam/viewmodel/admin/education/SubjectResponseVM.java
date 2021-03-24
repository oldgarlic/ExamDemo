
package com.lll.online.exam.viewmodel.admin.education;

import com.lll.online.exam.viewmodel.BaseVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Subject查询返回类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponseVM extends BaseVM {
    private Integer id;
    /**
     * 语文 数学 英语 等
     */
    private String name;
    /**
     * 年级 (1-12) 小学 初中 高中  大学
     */
    private Integer level;
    /**
     * 一年级、二年级等
     */
    private String levelName;
}
