package me.berg.home.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件表 - 包含所有文件及其文件类型和所属项目
 *
 * @TableName my_file
 */
@TableName(value = "my_file")
@Data
@AllArgsConstructor
public class MyFile implements Serializable {
    /**
     * File's UUID
     */
    @TableId(value = "fid")
    private String fid;

    /**
     * Filename
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * File type
     */
    @TableField(value = "typename")
    private String typename;

    /**
     * File's Project
     */
    @TableField(value = "pid")
    private Short pid;

    /**
     * File's last modify time
     */
    @TableField(value = "modify_time")
    private Date modify_time;

    /**
     * File 前n个字符
     */
    @TableField(value = "description")
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
