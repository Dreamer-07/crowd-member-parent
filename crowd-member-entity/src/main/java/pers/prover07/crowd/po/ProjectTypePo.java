package pers.prover07.crowd.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Getter
@Setter
@TableName("project_type")
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTypePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectid;

    private Integer typeid;


}
