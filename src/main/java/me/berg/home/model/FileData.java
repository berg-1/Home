package me.berg.home.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据表 - 包含文件id和数据
 * @TableName file_data
 */
@TableName(value ="file_data")
@Data
@AllArgsConstructor
public class FileData implements Serializable {
    /**
     * 文件ID - UUID
     */
    @TableId(value = "fid")
    private String fid;

    /**
     * 数据
     */
    @TableField(value = "data")
    private byte[] data;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
