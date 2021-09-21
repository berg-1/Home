package me.berg.home.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目表 - 项目id和项目名称
 * @TableName project
 */
@TableName(value ="project")
@Data
public class Project implements Serializable {
    /**
     * 项目ID
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Short pid;

    /**
     * 醒目名称
     */
    @TableField(value = "project_name")
    private String projectName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
