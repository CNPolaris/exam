package com.polaris.exam.dto.dashboard;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class TeacherDashboard {
    private Integer classCount;
    private Integer studentCount;
    private Integer examPaperCount;
    private Integer questionCount;
    private List<Map<String,Object>> classUserPie;
    private List<Map<String,Object>> classPaperPie;
}
