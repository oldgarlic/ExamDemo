package com.lll.online.exam.viewmodel.student.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 固定试卷/任务试卷返回体
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperInfo {
    private Integer id;
    private String name;
    // TODO：下面两个参数是给时段试卷服务的
    private Date limitStartTime;
    private Date limitEndTime;
}
