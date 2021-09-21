package me.berg.home.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件表 - 包含所有文件及其文件类型和所属项目
 * @TableName my_file
 */
@TableName(value ="my_file")
@Data
@AllArgsConstructor
public class MyFile implements Serializable {
    /**
     * 文件UUID
     */
    @TableId(value = "fid")
    private String fid;

    /**
     * 文件名
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 文件类型
     */
    @TableField(value = "typename")
    private String typename;

    /**
     * 所属项目
     */
    @TableField(value = "pid")
    private Short pid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
