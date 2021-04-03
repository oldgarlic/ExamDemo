package com.lll.online.exam.viewmodel.student.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 主页试卷信息返回类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexVM {
    private List<PaperInfo> fixedPaper;
    private List<TimeLimitPaper> timeLimitPaper;
    // TODO：任务试卷前端没有要求，暂时不写
}
