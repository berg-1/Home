package me.berg.home.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件类型 - 文件类型及对应的后缀名
 * @TableName file_type
 */
@TableName(value ="file_type")
@Data
public class FileType implements Serializable {
    /**
     * 文件类型
     */
    @TableId(value = "typename")
    private String typename;

    /**
     * 文件后缀名
     */
    @TableField(value = "suffix")
    private String suffix;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
