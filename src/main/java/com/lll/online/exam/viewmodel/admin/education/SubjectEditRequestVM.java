package com.lll.online.exam.viewmodel.admin.education;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加/修改Subject接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEditRequestVM {
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer level;

    @NotBlank
    private String levelName;
}
